package com.example.projectSophos.repositories;

import com.example.projectSophos.entities.Appointments;
import com.example.projectSophos.serializers.AppointmentsCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {

    List<Appointments> findByAffiliate_Id(Integer affiliateId);

    @Query(
        "SELECT new com.example.projectSophos.serializers.AppointmentsCount(app.affiliate, COUNT(app.affiliate)) " +
        "FROM Appointments AS app WHERE app.date = :date GROUP BY app.affiliate"
    )
    List<AppointmentsCount> countTotalAffiliatesByDate(@Param("date") Date date);

}