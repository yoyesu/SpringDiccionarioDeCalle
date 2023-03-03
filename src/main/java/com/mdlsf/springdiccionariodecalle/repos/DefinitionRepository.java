package com.mdlsf.springdiccionariodecalle.repos;

import com.mdlsf.springdiccionariodecalle.entities.Definition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefinitionRepository extends JpaRepository<Definition, Integer> {
    List<Definition> findDefinitionsByDefinitionContainsIgnoreCase(String searchParam);
    List<Definition> findDefinitionsByExampleContainingIgnoreCase(String searchParam);
}
