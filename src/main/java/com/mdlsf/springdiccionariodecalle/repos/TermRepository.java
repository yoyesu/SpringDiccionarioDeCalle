package com.mdlsf.springdiccionariodecalle.repos;

import com.mdlsf.springdiccionariodecalle.entities.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Integer> {

    Term getDictionariesByEntryNameIsStartingWith(String initial);
}
