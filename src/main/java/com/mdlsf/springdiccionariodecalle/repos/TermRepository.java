package com.mdlsf.springdiccionariodecalle.repos;

import com.mdlsf.springdiccionariodecalle.entities.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermRepository extends JpaRepository<Term, Integer> {

    List<Term> getDictionariesByEntryNameIsStartingWith(String initial);
    boolean existsByEntryName(String name);
    Term findByEntryName(String name);
}
