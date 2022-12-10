package com.example.projectSophos.controllers;

import com.example.projectSophos.entities.Appointments;
import com.example.projectSophos.exceptions.WrongForeignIdException;
import com.example.projectSophos.serializers.AppointmentsCount;
import com.example.projectSophos.services.AppointmentsService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class AppointmentsController {

    @Autowired
    AppointmentsService appointmentsService;

    @GetMapping(value="/appointments")
    public ResponseEntity<List<Appointments>> getList(HttpServletResponse response) {
        List<Appointments> listAppointments = appointmentsService.getAppointments();
        if(listAppointments.size() == 0){
            return new ResponseEntity<>(listAppointments, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listAppointments, HttpStatus.OK);
    }

    @GetMapping(value="/appointments/{appointmentsId}")
    public ResponseEntity<Appointments> getById(@PathVariable(value = "appointmentsId") Integer id) {
        Optional<Appointments> appointments = appointmentsService.getById(id);
        return ResponseEntity.of(appointments);
    }

    @GetMapping(value="/appointments/getByDate/{date}")
    public ResponseEntity<List<AppointmentsCount>> getByDate(
            @PathVariable(value = "date") @DateTimeFormat(pattern = "dd-MM-yyyy")Date date, HttpServletResponse response
    ) {
        List<AppointmentsCount> listAppointments = appointmentsService.getByDate(date);

        if(listAppointments.size() == 0){
            return new ResponseEntity<>(listAppointments, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listAppointments, HttpStatus.OK);
    }

    @GetMapping(value="/appointments/getByAffiliate/{affiliateId}")
    public ResponseEntity<List<Appointments>> getByAffiliate(@PathVariable(value = "affiliateId") Integer affiliateId, HttpServletResponse response) {
        List<Appointments> listAppointments = this.appointmentsService.getByAffiliateId(affiliateId);

        if(listAppointments.size() == 0){
            return new ResponseEntity<>(listAppointments, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listAppointments, HttpStatus.OK);
    }

    @PostMapping(value="/appointments")
    public ResponseEntity<Appointments> createAppointments(@Valid @RequestBody Appointments appointments) throws WrongForeignIdException {
        appointments = appointmentsService.checkAppointments(appointments);
        return new ResponseEntity<>(appointmentsService.createAppointments(appointments), HttpStatus.CREATED);
    }

    @PutMapping(value="/appointments/{appointmentsId}")
    public ResponseEntity<Appointments> updateTests(
        @Valid @PathVariable(value = "appointmentsId") Integer id, @RequestBody Appointments appointmentDetails
    ) throws WrongForeignIdException {
        appointmentDetails = appointmentsService.checkAppointments(appointmentDetails);
        return appointmentsService.updateAppointments(id, appointmentDetails)
                .map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value="/appointments/{appointmentsId}")
    public void deleteAppointments(@PathVariable(value = "appointmentsId") Integer id, HttpServletResponse response) {
        if(appointmentsService.deleteAppointments(id)){
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
    }

}
