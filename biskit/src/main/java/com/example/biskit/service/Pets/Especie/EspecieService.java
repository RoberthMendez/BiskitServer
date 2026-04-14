package com.example.biskit.service.Pets.Especie;
import java.util.List;
import com.example.biskit.entities.pets.Especie;

public interface EspecieService {

    public List<Especie> getAllEspecies();

    public Especie getEspecieById(Long id);

    public Especie getEspecieByNombre(String nombre);
}
