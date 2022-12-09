package com.example.projectSophos.repositories;

import com.example.projectSophos.entities.Tests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestsRepository extends JpaRepository<Tests, Integer> {

}