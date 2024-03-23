package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class EntryControllerTests {

    @MockBean(EntryRepository.class)
    private EntryRepository entryRepository;

    @Autowired
    private EntryController entryController;


    @Test
    public void testAllEntriesAreReturned() {

        List<Entry> entries = buildRepositoryWithEmptyEntries(2);

        given(this.entryRepository.findAll()).willReturn(entries);

        assertEquals(2, entryController.getAllEntries(null).getBody().size());
    }

    @Test
    public void testALimitedNumberOfEntriesIsReturned() {
        List<Entry> entries = buildRepositoryWithEmptyEntries(20);

        given(this.entryRepository.findAll()).willReturn(entries);

        assertEquals(10, entryController.getAllEntries(10).getBody().size());
    }

    private List<Entry> buildRepositoryWithEmptyEntries(int numberOfEntries) {
        List<Entry> entries = new ArrayList<>();
        for (int i=0; i <numberOfEntries; i++) {
            entries.add(new Entry());
        }

        return entries;
    }
}
