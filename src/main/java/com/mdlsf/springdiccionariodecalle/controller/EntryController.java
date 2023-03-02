package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.entities.*;
import com.mdlsf.springdiccionariodecalle.exceptions.NoMatchingIdFound;
import com.mdlsf.springdiccionariodecalle.repos.DefinitionRepository;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import com.mdlsf.springdiccionariodecalle.repos.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/all")
    public ResponseEntity<List<Entry>> getAllEntries(){
        return new ResponseEntity<>(entryRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/search/{initial}")
    public ResponseEntity<List<List<Entry>>> getEntriesByInitial(@PathVariable String initial){
        if(isStringNullOrLongerThanOne(initial)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<List<Entry>> list = new ArrayList<>();

        for(Term term : getAllTermsStartingWithCharacter(initial)){

            list.add(entryRepository.findEntriesByTerm(term));
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/browse/{character}")
    public ResponseEntity<List<String>> getAllEntryNamesStartingWith(@PathVariable String character){
        if(isStringNullOrLongerThanOne(character)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<String> names = new ArrayList<>();

        for(Term term : getAllTermsStartingWithCharacter(character)){

            names.add(term.getEntryName());
        }
        return new ResponseEntity<>(names, HttpStatus.OK);
    }

    @GetMapping("/search-name/{word}")
    public ResponseEntity<List<Entry>> getEntriesContainingXInTermName(@PathVariable String word){
        if(word == null || word.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Entry> entries = new ArrayList<>();
        List<Term> terms = termRepository.findTermsByEntryNameContainsIgnoreCase(word);

        if(terms.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        for(Term term : terms){
            entries.addAll(entryRepository.findEntriesByTerm(term));
        }
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/search-definition/{word}")
    public ResponseEntity<List<Entry>> getEntriesContainingXInDefinition(@PathVariable String word){
        if(word == null || word.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Entry> entries = new ArrayList<>();
        entries = getEntriesByDefinitionFieldContaining(entries, "definition", word);

        if(entries.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/search-example/{word}")
    public ResponseEntity<List<Entry>> getEntriesContainingXInExample(@PathVariable String word){
        if(word == null || word.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Entry> entries = new ArrayList<>();
        entries = getEntriesByDefinitionFieldContaining(entries, "example", word);

        if(entries.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    //TODO GET entries added from X date (should addedDate be on the entry rather than definition?)
    //TODO GET entries updated from X date

    @GetMapping("/contributor/all")
    public ResponseEntity<Set<User>> getAllContributors(){
        Set<User> contributors = new HashSet<>();
        for (Entry entry : entryRepository.findAll()){
            contributors.add(entry.getUserAdded());
        }
        return new ResponseEntity<>(contributors, HttpStatus.OK);
    }

    @GetMapping("/contributor/{contributor}")
    public ResponseEntity<?> getAllEntriesAddedByContributor(@PathVariable String contributor){
        List<Entry> entries = new ArrayList<>();
        entries = getEntriesByDefinitionFieldContaining(entries, "contributor", contributor);

        if(entries.isEmpty()){
            return new ResponseEntity<>("No entries found for that contributor.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<?> getAllEntriesByCountry(@PathVariable String country){
        List<Entry> entries = new ArrayList<>();
        entries = getEntriesByDefinitionFieldContaining(entries, "country", country);

        if(entries.isEmpty()){
            return new ResponseEntity<>("No entries found for that country.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(entries, HttpStatus.OK);
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

        return new ResponseEntity<>("New entry added successfully", HttpStatus.OK);
    }
    @PatchMapping("/update/{defId}")
    public ResponseEntity<?> updateEntry(@RequestBody Entry entry, @PathVariable int defId){
        if(entry == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Entry entryToUpdate;
        Term newTerm = entry.getTerm();
        Definition newDefinition = entry.getDef();
        boolean hasBeenUpdated = false;

        try {
            entryToUpdate = entryRepository.findEntryByDef(definitionRepository.findById(defId).orElseThrow(NoMatchingIdFound::new));
            boolean termIsValidForUpdate = isTermValidForUpdate(entryToUpdate, newTerm, false);
            hasBeenUpdated =  termIsValidForUpdate || isDefValidForUpdate(entry, entryToUpdate, newDefinition, false);

            if(!hasBeenUpdated){
                return new ResponseEntity<>("The entry provided matches the original entry.",HttpStatus.BAD_REQUEST);
            }

            entryToUpdate.setLastUpdated(Instant.now());

            //updating the date for all the other entries with the same term id if the term was updated above
            if(termIsValidForUpdate){
                for(Entry otherEntry : entryRepository.findEntriesByTerm(termRepository.findById(entryToUpdate.getTerm().getId()).orElseThrow())){
                    otherEntry.setLastUpdated(Instant.now());
                }
            }
            entryRepository.save(entryToUpdate);

        } catch (NoMatchingIdFound e) {
            return new ResponseEntity<>(e.getMessage(defId), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(entryToUpdate, HttpStatus.OK);
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

    //TODO test delete methods

    //TODO GET entries added before X date
    //TODO GET entries added between X-Y dates

    //TODO create a table for countries in DB

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
            case "contributor" -> definitions = definitionRepository.findDefinitionsByUserAdded(searchWord); //NOT case-sensitive
            case "country" -> definitions = definitionRepository.findDefinitionsByCountryUse(searchWord);
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
