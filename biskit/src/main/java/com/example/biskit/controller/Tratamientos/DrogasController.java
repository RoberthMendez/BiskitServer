package com.example.biskit.controller.Tratamientos;

import com.example.biskit.entities.DTOs.Droga.DrogaDTO;
import com.example.biskit.entities.DTOs.Droga.DrogaMapper;
import com.example.biskit.service.Tratamientos.DrogasService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drogas")
@CrossOrigin(origins = "http://localhost:4200")
public class DrogasController {

  @Autowired
  private DrogasService drogasService;

  // ----- Mostrar Drogas (READ) ------
  // http://localhost:8080/drogas
  @GetMapping("")
  public ResponseEntity<List<DrogaDTO>> mostrarDrogas() {
    return new ResponseEntity<>(
      DrogaMapper.INSTANCE.toDTOList(drogasService.getDrogas()),
      HttpStatus.OK
    );
  }
}
