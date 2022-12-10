package com.example.projectSophos.services;

import com.example.projectSophos.entities.Affiliates;
import com.example.projectSophos.entities.Appointments;
import com.example.projectSophos.entities.Tests;
import com.example.projectSophos.exceptions.WrongForeignIdException;
import com.example.projectSophos.repositories.AffiliatesRepository;
import com.example.projectSophos.serializers.AppointmentsCount;
import com.example.projectSophos.repositories.AppointmentsRepository;
import com.example.projectSophos.repositories.TestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class AppointmentsService {

    @Autowired
    AppointmentsRepository appointmentsRepository;
    @Autowired
    AffiliatesRepository affiliatesRepository;
    @Autowired
    TestsRepository testsRepository;

    // CREATE
    public Appointments createAppointments(Appointments appointments) {
        return appointmentsRepository.save(appointments);
    }

    // CHECK
    public Appointments checkAppointments(Appointments appointments) throws WrongForeignIdException {
        int testId = appointments.getTest().getId();
        Optional<Tests> test = testsRepository.findById(testId);

        if(! test.isPresent()){
            throw new WrongForeignIdException("test_id = '" + testId + "' does not exist");
        }

        int affiliateId = appointments.getAffiliate().getId();
        Optional<Affiliates> affiliate = affiliatesRepository.findById(affiliateId);

        if(! affiliate.isPresent()){
            throw new WrongForeignIdException("affiliate_id = '" + affiliateId + "' does not exist");
        }

        appointments.setTest(test.get());
        appointments.setAffiliate(affiliate.get());
        return appointmentsRepository.save(appointments);
    }

    // READ
    public List<Appointments> getAppointments() {
        return appointmentsRepository.findAll();
    }

    public Optional<Appointments> getById(Integer id) {
        return appointmentsRepository.findById(id);
    }

    // UPDATE
    public Optional<Appointments> updateAppointments(Integer appointmentId, Appointments appointmentsDetails) {
        Optional<Appointments> opAppointments = appointmentsRepository.findById(appointmentId);

        if(!opAppointments.isPresent()) {
            return opAppointments;
        }

        Appointments appointments = opAppointments.get();

        appointments.setDate(appointmentsDetails.getDate());
        appointments.setHour(appointmentsDetails.getHour());
        appointments.setTest(appointmentsDetails.getTest());
        appointments.setAffiliate(appointmentsDetails.getAffiliate());

        return Optional.of(appointmentsRepository.save(appointments));
    }

    // DELETE
    public void deleteAppointments(Integer appointmentsId) {
        appointmentsRepository.deleteById(appointmentsId);
    }

    public List<AppointmentsCount> getByDate(Date date) {
        return appointmentsRepository.countTotalAffiliatesByDate(date);
    }

    public List<Appointments> getByAffiliateId(Integer affiliateId) {
        return appointmentsRepository.findByAffiliate_Id(affiliateId);
    }

}