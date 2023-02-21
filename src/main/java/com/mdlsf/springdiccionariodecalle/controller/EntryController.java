package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.NoEntryMatchingIdFound;
import com.mdlsf.springdiccionariodecalle.entities.Term;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import com.mdlsf.springdiccionariodecalle.repos.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EntryController {

    private TermRepository termRepository;
    private EntryRepository entryDefRepository;

    @Autowired
    public EntryController(TermRepository termRepository, EntryRepository entryRepository) {
        this.termRepository = termRepository;
        this.entryDefRepository = entryRepository;
    }

    @GetMapping("/")
    public List<Term> getLastTenEntries(){
        List<Term> list = termRepository.findAll();
        List<Term> smallerList = new ArrayList<>();

        int listSize = list.size() -1;
        for(int i = 0; i < 10; i++){

            smallerList.add(list.get(listSize--));

        }

        return smallerList;
    }

    //TODO change return type to EntryDef
    @GetMapping("/search/{initial}")
    public List<Term> getEntryByInitial(@PathVariable String initial){
        List<Term> list = new ArrayList<>();
        list.add(termRepository.getDictionariesByEntryNameIsStartingWith(initial));

        return list;
    }

    @DeleteMapping("/remove/{id}")
    public void deleteEntryById(@PathVariable int id){
        termRepository.deleteById(id);
    }

    @PostMapping("/new")
    public void addNewEntry(@RequestBody Term term){
        termRepository.save(term);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateEntry(@RequestBody Term term, @PathVariable int id){
        if(term == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Term termToUpdate;
        boolean hasBeenUpdated = false;
        try {
            termToUpdate = termRepository.findById(id).
                    orElseThrow(NoEntryMatchingIdFound::new);

            if(term.getEntryName() != null && !term.getEntryName().equals(termToUpdate.getEntryName()) && !term.getEntryName().isEmpty()){
                termToUpdate.setEntryName(term.getEntryName());
                hasBeenUpdated = true;
            }

            if(term.getDefinitions() != null && !term.getDefinitions().isEmpty()){
                termToUpdate.setDefinitions(term.getDefinitions());
                hasBeenUpdated = true;
            }

            if(!hasBeenUpdated){
                return new ResponseEntity<>("The entry provided matches the original entry.",HttpStatus.BAD_REQUEST);
            }

            //TODO fix method to update the entryDef lastUpdated field
//            entryToUpdate.setLastUpdated(Instant.now());
            termRepository.save(termToUpdate);
        } catch (NoEntryMatchingIdFound e) {
            return new ResponseEntity<>(e.getMessage(id), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(termToUpdate, HttpStatus.ACCEPTED);
    }
}
