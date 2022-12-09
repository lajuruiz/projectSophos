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

    @RequestMapping(value="/tests", method=RequestMethod.GET)
    public List<Tests> getList(HttpServletResponse response) {
        List<Tests> listTests = testsService.getTests();
        if(listTests.size() == 0){
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
        return listTests;
    }

    @RequestMapping(value="/tests/{testsId}", method=RequestMethod.GET)
    public ResponseEntity<Tests> getById(@PathVariable(value = "testsId") Integer id) {
        Optional<Tests> tests = testsService.getById(id);
        return ResponseEntity.of(tests);
    }

    @RequestMapping(value="/tests", method=RequestMethod.POST)
    public Tests createTests(@Valid @RequestBody Tests tests) {
        return testsService.createTests(tests);
    }


    @RequestMapping(value="/tests/{testsId}", method=RequestMethod.PUT)
    public ResponseEntity<Tests> updateTests(@Valid @PathVariable(value = "testsId") Integer id, @RequestBody Tests testDetails) {
        return ResponseEntity.of(testsService.updateTests(id, testDetails));
    }


    @RequestMapping(value="/tests/{testsId}", method=RequestMethod.DELETE)
    public void deleteTests(@PathVariable(value = "testsId") Integer id) {
        testsService.deleteTests(id);
    }

}