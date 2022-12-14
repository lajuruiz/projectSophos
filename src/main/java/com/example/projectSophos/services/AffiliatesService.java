package com.example.projectSophos.services;

import com.example.projectSophos.entities.Affiliates;
import com.example.projectSophos.repositories.AffiliatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AffiliatesService {

    @Autowired
    AffiliatesRepository affiliatesRepository;


    // GET
    public List<Affiliates> getAffiliates() {
        return affiliatesRepository.findAll();
    }

    // DETAIL GET
    public Optional<Affiliates> getById(Integer id) {
        return affiliatesRepository.findById(id);
    }


    // CREATE
    public Affiliates createAffiliates(Affiliates affiliates) {
        return affiliatesRepository.save(affiliates);
    }

    // UPDATE
    public Optional<Affiliates> updateAffiliates(Integer affiliateId, Affiliates affiliatesDetails) {
        Optional<Affiliates> opAffiliates = affiliatesRepository.findById(affiliateId);

        if(opAffiliates.isEmpty()) {
            return opAffiliates;
        }

        Affiliates affiliates = opAffiliates.get();

        affiliates.setName(affiliatesDetails.getName());
        affiliates.setAge(affiliatesDetails.getAge());
        affiliates.setMail(affiliatesDetails.getMail());

        return Optional.of(affiliatesRepository.save(affiliates));
    }

    // DELETE
    public boolean deleteAffiliates(Integer id) {
        if (affiliatesRepository.existsById(id)) {
            affiliatesRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

}