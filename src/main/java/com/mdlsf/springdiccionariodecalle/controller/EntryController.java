package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.NoEntryMatchingIdFound;
import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.repos.EntryDefRepository;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EntryController {

    private EntryRepository entryRepository;
    private EntryDefRepository entryDefRepository;

    @Autowired
    public EntryController(EntryRepository entryRepository, EntryDefRepository entryDefRepository) {
        this.entryRepository = entryRepository;
        this.entryDefRepository = entryDefRepository;
    }

    @GetMapping("/")
    public List<Entry> getLastTenEntries(){
        List<Entry> list = entryRepository.findAll();
        List<Entry> smallerList = new ArrayList<>();

        int listSize = list.size() -1;
        for(int i = 0; i < 10; i++){

            smallerList.add(list.get(listSize--));

        }

        return smallerList;
    }

    @GetMapping("/search/{initial}")
    public List<Entry> getEntryByInitial(@PathVariable String initial){
        List<Entry> list = new ArrayList<>();
        list.add(entryRepository.getDictionariesByEntryNameIsStartingWith(initial));

        return list;
    }

    @DeleteMapping("/remove/{id}")
    public void deleteEntryById(@PathVariable int id){
        entryRepository.deleteById(id);
    }

    @PostMapping("/new")
    public void addNewEntry(@RequestBody Entry entry){
        entryRepository.save(entry);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateEntry(@RequestBody Entry entry, @PathVariable int id){
        if(entry == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Entry entryToUpdate;
        boolean hasBeenUpdated = false;
        try {
            entryToUpdate = entryRepository.findById(id).
                    orElseThrow(NoEntryMatchingIdFound::new);

            if(entry.getEntryName() != null && !entry.getEntryName().equals(entryToUpdate.getEntryName()) && !entry.getEntryName().isEmpty()){
                entryToUpdate.setEntryName(entry.getEntryName());
                hasBeenUpdated = true;
            }

            if(entry.getDefinitions() != null && !entry.getDefinitions().isEmpty()){
                entryToUpdate.setDefinitions(entry.getDefinitions());
                hasBeenUpdated = true;
            }

            if(!hasBeenUpdated){
                return new ResponseEntity<>("The entry provided matches the original entry.",HttpStatus.BAD_REQUEST);
            }

            entryToUpdate.setLastUpdated(Instant.now());
            entryRepository.save(entryToUpdate);
        } catch (NoEntryMatchingIdFound e) {
            return new ResponseEntity<>(e.getMessage(id), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(entryToUpdate, HttpStatus.ACCEPTED);
    }
}
