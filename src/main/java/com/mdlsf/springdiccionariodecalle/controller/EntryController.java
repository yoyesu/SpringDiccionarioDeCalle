package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.repos.EntryDefRepository;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    //TODO post method to add new entry

    @PostMapping("/new")
    public void addNewEntry(@RequestBody Entry entry){
        entryRepository.save(entry);
    }
}
