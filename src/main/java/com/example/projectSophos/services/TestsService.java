package com.example.projectSophos.services;

import com.example.projectSophos.entities.Tests;
import com.example.projectSophos.repositories.TestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestsService {

    @Autowired
    TestsRepository testsRepository;

    // CREATE
    public Tests createTests(Tests tests) {
        return testsRepository.save(tests);
    }

    // READ
    public List<Tests> getTests() {
        return testsRepository.findAll();
    }
    public Optional<Tests> getById(Integer id) {
        return testsRepository.findById(id);
    }

    // UPDATE
    public Optional<Tests> updateTests(Integer testId, Tests testsDetails) {
        Optional<Tests> opTests = testsRepository.findById(testId);

        if(!opTests.isPresent()) {
            return opTests;
        }

        Tests tests = opTests.get();

        tests.setName(testsDetails.getName());
        tests.setDescription(testsDetails.getDescription());

        return Optional.of(testsRepository.save(tests));
    }

    // DELETE
    public void deleteTests(Integer testsId) {
        testsRepository.deleteById(testsId);
    }
}