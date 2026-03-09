package com.example.biskit.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.biskit.entities.Client;

@Repository
public interface ClientsRepo extends JpaRepository<Client, Long> {

}
