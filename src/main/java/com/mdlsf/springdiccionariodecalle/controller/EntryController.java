package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.entities.*;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/entries")
@RestController
public class EntryController {

    private EntryRepository entryRepository;

    @Autowired
    public EntryController(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Entry>> getAllEntries(@RequestParam @Nullable Integer entryLimit){
        List<Entry> allEntries = entryRepository.findAll();

        if (entryLimit != null) {
            int entriesSize = allEntries.size();
            int startingIndex = entryLimit > entriesSize ? 0 : entriesSize - entryLimit;
            return new ResponseEntity<>(allEntries.subList(startingIndex,entriesSize), HttpStatus.OK);
        }
        return new ResponseEntity<>(allEntries, HttpStatus.OK);
    }



    @PostMapping("/")
    public ResponseEntity<String> addNewEntry(@RequestBody NewEntryRequest entryRequest){
        Entry entry = Entry.builder()
                .def(entryRequest.getDef())
                .term(entryRequest.getTerm())
                .countryUse(entryRequest.getCountryUse())
                .build();

        entryRepository.save(entry);

        return new ResponseEntity<>("New entry added successfully", HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAllEntriesMatchingTermId(@PathVariable int id) {
        entryRepository.deleteById(id);

        return new ResponseEntity<>("Entry with id " + id + " has been removed successfully.", HttpStatus.OK);
    }

}
