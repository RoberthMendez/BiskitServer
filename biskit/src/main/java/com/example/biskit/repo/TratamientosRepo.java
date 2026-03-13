package com.example.biskit.repo;

import org.springframework.stereotype.Repository;

import com.example.biskit.entities.Tratamiento;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TratamientosRepo extends JpaRepository<Tratamiento, Long> {

}