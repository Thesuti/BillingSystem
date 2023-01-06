package com.project.billingsystem.repositories;

import com.project.billingsystem.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    boolean existsAppUserByUsername(String username);
    boolean existsAppUserByEmail(String email);

    List<AppUser> findAll();

    Optional<AppUser> findAppUserByUsername(String username);
}
