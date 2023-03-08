package com.mdlsf.springdiccionariodecalle.repos;

import com.mdlsf.springdiccionariodecalle.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String username);
}