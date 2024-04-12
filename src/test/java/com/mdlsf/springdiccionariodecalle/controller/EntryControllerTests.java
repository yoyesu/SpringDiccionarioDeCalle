package com.mdlsf.springdiccionariodecalle.controller;

import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.entities.NewEntryRequest;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
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
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
        ResponseEntity<String> responseEntity = entryController.deleteAllEntriesMatchingTermId(1);
        verify(entryRepository, times(1)).deleteById(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Entry with id 1 has been removed successfully.", responseEntity.getBody());
    }

    @Test
    public void testANewEntryIsAdded() {
        List<Entry> entries = new ArrayList<>();
        given(this.entryRepository.findAll()).willReturn(entries);
        NewEntryRequest newEntryRequest = NewEntryRequest.builder()
                .term("some term")
                .def("some def")
                .countryUse(Set.of("country1","country2")).build();
        ResponseEntity<String> responseEntity = entryController.addNewEntry(newEntryRequest);

        verify(entryRepository, times(1)).save(any(Entry.class));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("New entry added successfully", responseEntity.getBody());

    }

    private List<Entry> buildRepositoryWithEmptyEntries(int numberOfEntries) {
        List<Entry> entries = new ArrayList<>();
        for (int i=0; i <numberOfEntries; i++) {
            entries.add(new Entry());
        }

        return entries;
    }
}
