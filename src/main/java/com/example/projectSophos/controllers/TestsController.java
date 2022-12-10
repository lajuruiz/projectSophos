package com.example.projectSophos.controllers;

import com.example.projectSophos.entities.Tests;
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
public class TestsController {
    @Autowired
    TestsService testsService;

    @GetMapping(value="/tests")
    public ResponseEntity<List<Tests>> getList(HttpServletResponse response) {
        List<Tests> listTests = testsService.getTests();
        if(listTests.size() == 0){
            return new ResponseEntity<>(listTests, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(listTests, HttpStatus.OK);
    }

    @GetMapping(value="/tests/{testsId}")
    public ResponseEntity<Tests> getById(@PathVariable(value = "testsId") Integer id) {
        return ResponseEntity.of(testsService.getById(id));
    }

    @PostMapping(value="/tests")
    public ResponseEntity<Tests> createTests(@Valid @RequestBody Tests tests) {
        return new ResponseEntity<>(testsService.createTests(tests), HttpStatus.CREATED);
    }

    @PutMapping(value="/tests/{testsId}")
    public ResponseEntity<Tests> updateTests(@Valid @PathVariable(value = "testsId") Integer id, @RequestBody Tests testDetails) {
        return testsService.updateTests(id, testDetails)
                .map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value="/tests/{testsId}")
    public void deleteTests(@PathVariable(value = "testsId") Integer id, HttpServletResponse response) {
        if(testsService.deleteTests(id)){
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
    }

}