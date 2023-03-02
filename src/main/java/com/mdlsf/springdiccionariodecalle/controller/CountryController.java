package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.entities.Country;
import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.repos.CountryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/countries")
@RestController
public class CountryController {

    private CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Country>> getAllCountries(){
        return new ResponseEntity<>(countryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getNamesOfAllCountries(){
        List<String> names = new ArrayList<>();
        for(Country country : countryRepository.findAll()){
            names.add(country.getCountry());
        }

        return new ResponseEntity<>(names, HttpStatus.OK);
    }
}

