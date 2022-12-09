package com.example.projectSophos.controllers;

import com.example.projectSophos.entities.Appointments;
import com.example.projectSophos.exceptions.WrongForeignIdException;
import com.example.projectSophos.services.AffiliatesService;
import com.example.projectSophos.services.AppointmentsService;
import com.example.projectSophos.services.TestsService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class AppointmentsController {

    @Autowired
    AppointmentsService appointmentsService;
    @Autowired
    AffiliatesService affiliatesService;
    @Autowired
    TestsService testsService;

    @GetMapping(value="/appointments")
    public List<Appointments> getList(HttpServletResponse response) {
        List<Appointments> listAppointments = appointmentsService.getAppointments();
        if(listAppointments.size() == 0){
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
        return listAppointments;
    }

    @GetMapping(value="/appointments/{appointmentsId}")
    public ResponseEntity<Appointments> getById(@PathVariable(value = "appointmentsId") Integer id) {
        Optional<Appointments> appointments = appointmentsService.getById(id);
        return ResponseEntity.of(appointments);
    }


    @PostMapping(value="/appointments")
    public Appointments createAppointments(@Valid @RequestBody Appointments appointments) throws WrongForeignIdException {
        appointments = appointmentsService.checkAppointments(appointments);
        return appointmentsService.createAppointments(appointments);
    }

    @PutMapping(value="/appointments/{appointmentsId}")
    public ResponseEntity<Appointments> updateTests(
        @Valid @PathVariable(value = "appointmentsId") Integer id, @RequestBody Appointments appointmentDetails
    ) throws WrongForeignIdException {
        appointmentDetails = appointmentsService.checkAppointments(appointmentDetails);
        return ResponseEntity.of(appointmentsService.updateAppointments(id, appointmentDetails));
    }


    @RequestMapping(value="/appointments/{appointmentsId}", method=RequestMethod.DELETE)
    public void deleteAppointments(@PathVariable(value = "appointmentsId") Integer id) {
        appointmentsService.deleteAppointments(id);
    }


}
