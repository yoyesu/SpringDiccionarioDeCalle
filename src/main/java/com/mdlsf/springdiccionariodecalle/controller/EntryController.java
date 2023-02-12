package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.entities.EntryDef;
import com.mdlsf.springdiccionariodecalle.repos.EntryDefRepository;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public List<EntryDef> getTenFirstEntries(){
        List<EntryDef> list = entryDefRepository.findAll();
        List<EntryDef> smallerList = new ArrayList<>();
        int count = 0;
        for(int i = 0; i < list.size(); i++){

            smallerList.add(list.get(i));
            if(count == 1){
                break;
            }
            count++;
        }

        return smallerList;
    }
}
