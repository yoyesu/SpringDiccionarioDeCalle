package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.entities.Definition;
import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.entities.EntryId;
import com.mdlsf.springdiccionariodecalle.entities.Term;
import com.mdlsf.springdiccionariodecalle.repos.DefinitionRepository;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import com.mdlsf.springdiccionariodecalle.repos.TermRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EntryControllerTests {

    @MockBean(EntryRepository.class)
    private EntryRepository entryRepository;

    @MockBean(DefinitionRepository.class)
    private DefinitionRepository definitionRepository;

    @MockBean(TermRepository.class)
    private TermRepository termRepository;

    @Autowired
    private EntryController entryController;


    @Test
    public void testAllEntriesAreReturned() {

        List<Entry> entries = buildRepositoryWithEmptyEntries(2);

        given(this.entryRepository.findAll()).willReturn(entries);

        assertEquals(2, entryController.getAllEntries(null).getBody().size());
    }

    @ParameterizedTest
    @MethodSource("getEntryLimitAndExpectations")
    public void testALimitedNumberOfEntriesIsReturned(int limit, int expectedEntries) {
        List<Entry> entries = buildRepositoryWithEmptyEntries(20);

        given(this.entryRepository.findAll()).willReturn(entries);

        assertEquals(expectedEntries, entryController.getAllEntries(limit).getBody().size());
    }

    private static Stream<Arguments> getEntryLimitAndExpectations(){
        return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(1, 1),
                Arguments.of(2, 2),
                Arguments.of(10, 10),
                Arguments.of(19, 19),
                Arguments.of(20, 20),
                Arguments.of(21, 20)
        );
    }
    @Test
    public void testAnEntryIsDeleted() {
        List<Entry> entries = new ArrayList<>();
        Term term = Term.builder().id(1).build();
        Definition def = Definition.builder().id(1).build();
        Entry entry = Entry.builder().term(term).def(def).build();
        entries.add(entry);

        when(termRepository.findById(1)).thenReturn(Optional.of(term));
        when(entryRepository.findEntriesByTerm(term)).thenReturn(entries);

        ResponseEntity<String> responseEntity = entryController.deleteAllEntriesMatchingTermId(1);
        verify(definitionRepository, times(entries.size())).deleteById(any());
        verify(termRepository, times(1)).deleteById(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Entry with id 1 has been removed successfully.", responseEntity.getBody());
    }

    @Test
    public void testANewEntryIsAdded() {
        List<Entry> entries = new ArrayList<>();
        Entry entry = Entry.builder().id(EntryId.builder().entryId(1).defId(1).build()).build();
        given(this.entryRepository.findAll()).willReturn(entries);
        entryController.addNewEntry(entry);
        assertEquals(1, entries.size());
    }

    private List<Entry> buildRepositoryWithEmptyEntries(int numberOfEntries) {
        List<Entry> entries = new ArrayList<>();
        for (int i=0; i <numberOfEntries; i++) {
            entries.add(new Entry());
        }

        return entries;
    }
}
