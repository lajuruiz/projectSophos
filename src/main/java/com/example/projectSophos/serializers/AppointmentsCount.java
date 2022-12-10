package com.example.projectSophos.serializers;

import com.example.projectSophos.entities.Affiliates;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class AppointmentsCount {
    private Affiliates affiliate;
    @JsonManagedReference(value = "total_appointments")
    private Long totalAppointments;

    public AppointmentsCount(Affiliates affiliate, Long totalAppointments) {
        this.affiliate = affiliate;
        this.totalAppointments = totalAppointments;
    }

    public Affiliates getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affiliates affiliate) {
        this.affiliate = affiliate;
    }

    public Long GetTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(Long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }
}
