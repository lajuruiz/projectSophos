package com.example.projectSophos.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "appointments")
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(name="date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm")
    @Column(name="hour")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date hour;

    @ManyToOne
    @JoinColumn(name="id_test", referencedColumnName="id")
    @NotNull
    private Tests test;

    @ManyToOne
    @JoinColumn(name="id_affiliate", referencedColumnName="id")
    @NotNull
    private Affiliates affiliate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHour() {
        return hour;
    }

    public void setHour(Date hour) {
        this.hour = hour;
    }

    public Tests getTest() {
        return test;
    }

    public void setTest(Tests test) {
        this.test = test;
    }

    public Affiliates getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affiliates affiliate) {
        this.affiliate = affiliate;
    }

}