package com.example.projectSophos.controllers;


import com.example.projectSophos.entities.Affiliates;
import com.example.projectSophos.services.AffiliatesService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AffiliatesController {

    @Autowired
    AffiliatesService affiliatesService;

    @GetMapping(value="/affiliates")
    public ResponseEntity<List<Affiliates>> getList(HttpServletResponse response){
        List<Affiliates> listAffiliates = affiliatesService.getAffiliates();
        if(listAffiliates.size() == 0){
            return new ResponseEntity<>(listAffiliates, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listAffiliates, HttpStatus.OK);
    }

    @GetMapping(value="/affiliates/{affiliateId}")
    public ResponseEntity<Affiliates> getById(@PathVariable(value = "affiliateId") Integer id) {
        return ResponseEntity.of(affiliatesService.getById(id));
    }

    @PostMapping(value="/affiliates")
    public ResponseEntity<Affiliates> createAffiliates(@Valid @RequestBody Affiliates affiliates) {
        return new ResponseEntity<>(affiliatesService.createAffiliates(affiliates), HttpStatus.CREATED);
    }

    @PutMapping(value="/affiliates/{affiliatesId}")
    public ResponseEntity<Affiliates> updateAffiliates(@Valid @PathVariable(value = "affiliatesId") Integer id, @RequestBody Affiliates affiliateDetails) {
        return affiliatesService.updateAffiliates(id, affiliateDetails)
                .map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value="/affiliates/{affiliateId}")
    public void deleteAffiliates(@PathVariable(value = "affiliateId") Integer id, HttpServletResponse response) {
        if(affiliatesService.deleteAffiliates(id)){
            response.setStatus(HttpStatus.OK.value());
        } else {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        };
    }

}
