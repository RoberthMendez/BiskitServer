package com.example.biskit.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.biskit.entities.Droga;

@Repository
public interface DrogasRepo extends JpaRepository<Droga, Long> {

}