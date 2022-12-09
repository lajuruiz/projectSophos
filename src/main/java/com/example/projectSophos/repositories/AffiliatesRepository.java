package com.example.projectSophos.repositories;

import com.example.projectSophos.entities.Affiliates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffiliatesRepository extends JpaRepository<Affiliates, Integer> {

}