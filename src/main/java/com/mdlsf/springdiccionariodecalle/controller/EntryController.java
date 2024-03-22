package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.entities.*;
import com.mdlsf.springdiccionariodecalle.exceptions.NoMatchingIdFound;
import com.mdlsf.springdiccionariodecalle.repos.DefinitionRepository;
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

    @GetMapping("/entries")
    public ResponseEntity<List<Entry>> getAllEntries(){
        return new ResponseEntity<>(entryRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<String> addNewEntry(@RequestBody Entry entry){
        Term term = entry.getTerm();
        //TODO add more validation to the entry (check term name is not empty, etc)
        //if the term already exists we won't create a new term
        if(termRepository.existsByEntryName(term.getEntryName())){
            term = termRepository.findByEntryName(term.getEntryName());
        } else {
            termRepository.save(term);
        }
        Entry entryToSave = new Entry();
        EntryId entryId = new EntryId();
        Definition def = definitionRepository.save(entry.getDef());

        entryId.setEntryId(term.getId());
        entryId.setDefId(def.getId());
        entryToSave.setTerm(term);
        entryToSave.setDef(def);
        entryToSave.setId(entryId);
        entryRepository.save(entryToSave);

        return new ResponseEntity<>("New entry added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> deleteAllEntriesMatchingTermId(@PathVariable int id) {

        //TODO find out why this method is not working, but the other delete methods are
        if(id < 0){
            return new ResponseEntity<>(new NoMatchingIdFound().getMessage(id), HttpStatus.BAD_REQUEST);
        }
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

    private List<Term> getAllTermsStartingWithCharacter(String character){
        return termRepository.getDictionariesByEntryNameIsStartingWith(character); //IT'S NOT CASE SENSITIVE
    }

    private boolean isStringNullOrLongerThanOne(String phrase){
        return phrase == null || phrase.length() > 1;
    }
    private static boolean isDefValidForUpdate(Entry entry, Entry entryToUpdate, Definition newDefinition, boolean hasBeenUpdated) {

        if(entry.getDef() == null){
            return false;
        }
        if(!entry.getDef().getDefinition().isEmpty() && !newDefinition.getDefinition().equalsIgnoreCase(entryToUpdate.getDef().getDefinition())){
            entryToUpdate.getDef().setDefinition(newDefinition.getDefinition());
            hasBeenUpdated = true;
        }

        if(!entry.getDef().getExample().isEmpty() && !newDefinition.getExample().equalsIgnoreCase(entryToUpdate.getDef().getExample())){
            entryToUpdate.getDef().setExample(newDefinition.getExample());
            hasBeenUpdated = true;
        }

        return hasBeenUpdated;
    }

    private List<Entry> getEntriesByDefinitionFieldContaining(List<Entry> entries, String field, String searchWord){

        List<Definition> definitions = new ArrayList<>();
        switch (field){
            case "definition" -> definitions = definitionRepository.findDefinitionsByDefinitionContainsIgnoreCase(searchWord);
            case "example" -> definitions = definitionRepository.findDefinitionsByExampleContainingIgnoreCase(searchWord);
        }

        for(Definition definition : definitions){
            entries.add(entryRepository.findEntryByDef(definition));
        }

        return entries;
    }

    private static boolean isTermValidForUpdate(Entry entryToUpdate, Term newTerm, boolean hasBeenUpdated) {
        if(newTerm.getEntryName() != null && !newTerm.getEntryName().equalsIgnoreCase(entryToUpdate.getTerm().getEntryName()) && !newTerm.getEntryName().isEmpty()){
            entryToUpdate.getTerm().setEntryName(newTerm.getEntryName());
            hasBeenUpdated = true;
        }
        return hasBeenUpdated;
    }
}
