package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.entities.Country;
import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.repos.CountryRepository;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestMapping("/countries")
@RestController
public class CountryController {

    private CountryRepository countryRepository;
    private final EntryRepository entryRepository;

    @Autowired
    public CountryController(CountryRepository countryRepository,
                             EntryRepository entryRepository) {
        this.countryRepository = countryRepository;
        this.entryRepository = entryRepository;
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

    @GetMapping("/entries/count")
    public ResponseEntity<Map<String, Long>> getCountOfEntriesPerCountry(){

        Map<String, Long> entriesPerCountry = entryRepository.findAll().stream()
                .flatMap(entry -> entry.getCountryUse().stream())
                .collect(Collectors.groupingBy(Country::getCountry, Collectors.counting()));

        return new ResponseEntity<>(entriesPerCountry, HttpStatus.OK);
    }
}

