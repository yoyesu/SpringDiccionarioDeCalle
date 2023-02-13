package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.repos.EntryDefRepository;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/search/{initial}")
    public List<Entry> getEntryByInitial(@PathVariable String initial){
        List<Entry> list = new ArrayList<>();
        list.add(entryRepository.getDictionariesByEntryNameIsStartingWith(initial));

        return list;
    }

    @GetMapping("/")
    public List<Entry> getTenEntries(){
        List<Entry> list = entryRepository.findAll();
        List<Entry> smallerList = new ArrayList<>();

        for(int i = 0; i < 3; i++){

            smallerList.add(list.get(i));

        }

        return smallerList;
    }

    //TODO post method to add new entry
    //TODO delete method to delete entry
    //TODO GET 10 latest added entries
}
