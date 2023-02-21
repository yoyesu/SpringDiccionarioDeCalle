package com.mdlsf.springdiccionariodecalle.repos;

import com.mdlsf.springdiccionariodecalle.entities.Definition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefinitionRepository extends JpaRepository<Definition, Integer> {
}
