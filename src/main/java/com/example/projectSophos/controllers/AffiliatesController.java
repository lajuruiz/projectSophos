package com.example.projectSophos.controllers;


import com.example.projectSophos.entities.Affiliates;
import com.example.projectSophos.entities.Tests;
import com.example.projectSophos.services.AffiliatesService;
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
public class AffiliatesController {

    @Autowired
    AffiliatesService affiliatesService;

    @GetMapping(value="/affiliates")
    public List<Affiliates> getList(HttpServletResponse response){
        List<Affiliates> listAffiliates = affiliatesService.getAffiliates();
        if(listAffiliates.size() == 0){
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
        return listAffiliates;
    }

    @GetMapping(value="/affiliates/{affiliateId}")
    public ResponseEntity<Affiliates> getById(@PathVariable(value = "affiliateId") Integer id) {
        Optional<Affiliates> affiliates = affiliatesService.getById(id);
        return ResponseEntity.of(affiliates);
    }

    @PostMapping(value="/affiliates")
    public Affiliates createAffiliates(@Valid @RequestBody Affiliates affiliates) {
        return affiliatesService.createAffiliates(affiliates);
    }

    @PutMapping(value="/affiliates/{affiliatesId}")
    public ResponseEntity<Affiliates> updateAffiliates(@Valid @PathVariable(value = "affiliatesId") Integer id, @RequestBody Affiliates affiliateDetails) {
        return ResponseEntity.of(affiliatesService.updateAffiliates(id, affiliateDetails));
    }

    @DeleteMapping(value="/affiliates/{affiliateId}")
    public void deleteAffiliates(@PathVariable(value = "affiliateId") Integer id) {
        affiliatesService.deleteAffiliates(id);
    }

}
