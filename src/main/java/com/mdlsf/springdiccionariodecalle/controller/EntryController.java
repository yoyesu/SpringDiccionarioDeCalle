package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.NoMatchingIdFound;
import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.entities.Term;
import com.mdlsf.springdiccionariodecalle.repos.DefinitionRepository;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import com.mdlsf.springdiccionariodecalle.repos.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EntryController {

    private TermRepository termRepository;
    private EntryRepository entryRepository;
    private DefinitionRepository definitionRepository;

    @Autowired
    public EntryController(TermRepository termRepository, EntryRepository entryRepository, DefinitionRepository definitionRepository) {
        this.termRepository = termRepository;
        this.entryRepository = entryRepository;
        this.definitionRepository = definitionRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<Entry>>  getLastTenEntries(){
        List<Entry> list = entryRepository.findAll();
        List<Entry> smallerList = new ArrayList<>();

        int listSize = list.size() -1;
        for(int i = 0; i < 10; i++){

            smallerList.add(list.get(listSize--));

        }

        return new ResponseEntity<>(smallerList, HttpStatus.OK) ;
    }

    @GetMapping("/search/{initial}")
    public ResponseEntity<List<List<Entry>>> getEntriesByInitial(@PathVariable String initial){
        if(initial == null || initial.length() > 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<List<Entry>> list = new ArrayList<>();
        List<Term> termsMatchingInitial = termRepository.getDictionariesByEntryNameIsStartingWith(initial);

        for(Term term : termsMatchingInitial){

            list.add(entryRepository.findEntriesByTerm(term));
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteAllEntriesMatchingTermId(@PathVariable int id) {
        try {
            List<Entry> entries = entryRepository.findEntriesByTerm(termRepository.findById(id).orElseThrow(NoMatchingIdFound::new));

            //removing all definitions from the Definitions table
            for(Entry entry : entries){
                definitionRepository.deleteById(entry.getDef().getId());
            }
            //finally, removing the term from Terms table
            termRepository.deleteById(id);
        } catch (NoMatchingIdFound e) {
            return new ResponseEntity<>(e.getMessage(id), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Entry with id " + id + " has been removed successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/remove/def/{id}")
    public ResponseEntity<String> deleteDefById(@PathVariable int id) {
        try {
            definitionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(new NoMatchingIdFound().getMessage(id), HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>("Definition with id " + id + " has been removed successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/remove/term/{id}")
    public ResponseEntity<String> deleteTermById(@PathVariable int id) {
        try {
            termRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(new NoMatchingIdFound().getMessage(id), HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>("Term with id " + id + " has been removed successfully.", HttpStatus.OK);
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
                    orElseThrow(NoMatchingIdFound::new);

            if(term.getEntryName() != null && !term.getEntryName().equals(termToUpdate.getEntryName()) && !term.getEntryName().isEmpty()){
                termToUpdate.setEntryName(term.getEntryName());
                hasBeenUpdated = true;
            }

//            if(term.getDefinitions() != null && !term.getDefinitions().isEmpty()){
//                termToUpdate.setDefinitions(term.getDefinitions());
//                hasBeenUpdated = true;
//            }

            if(!hasBeenUpdated){
                return new ResponseEntity<>("The entry provided matches the original entry.",HttpStatus.BAD_REQUEST);
            }

            //TODO fix method to update the entryDef lastUpdated field
//            entryToUpdate.setLastUpdated(Instant.now());
            termRepository.save(termToUpdate);
        } catch (NoMatchingIdFound e) {
            return new ResponseEntity<>(e.getMessage(id), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(termToUpdate, HttpStatus.ACCEPTED);
    }
}
