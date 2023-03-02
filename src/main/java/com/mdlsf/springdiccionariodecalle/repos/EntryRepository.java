package com.mdlsf.springdiccionariodecalle.repos;

import com.mdlsf.springdiccionariodecalle.entities.Definition;
import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.entities.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Integer> {
    List<Entry> findEntriesByTerm(Term term);
    Entry findEntryByDef(Definition def);
    List<Entry> findEntriesByLastUpdatedAfter(Instant date);
    List<Entry> findEntriesByLastUpdatedBefore(Instant date);
    List<Entry> findEntriesByLastUpdatedBetween(Instant startingDate, Instant endingDate);
}
