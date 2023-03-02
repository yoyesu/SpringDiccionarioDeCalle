package com.mdlsf.springdiccionariodecalle.repos;

import com.mdlsf.springdiccionariodecalle.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
