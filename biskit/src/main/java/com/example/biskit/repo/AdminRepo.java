package com.example.biskit.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.biskit.entities.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {

    

}
