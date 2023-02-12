package com.mdlsf.springdiccionariodecalle.repos;

import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.entities.EntryDef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryDefRepository extends JpaRepository<EntryDef, Integer> {

}
