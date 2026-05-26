package com.example.biskit.controller;

import com.example.biskit.entities.Admin;
import com.example.biskit.entities.DTOs.KPIs.DrogaTratamientoCountDTO;
import com.example.biskit.entities.DTOs.KPIs.StockDrogaDTO;
import com.example.biskit.entities.DTOs.KPIs.TopDTO;
import com.example.biskit.entities.DTOs.Tratamientos.TratamientosMesDTO;
import com.example.biskit.service.Admin.AdminsService;
import com.example.biskit.service.Admin.ReporteExcelService;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Tratamientos.DrogasService;
import com.example.biskit.service.Tratamientos.TratamientosService;
import com.example.biskit.service.Vets.VetService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private AdminsService adminsService;

  @Autowired
  private TratamientosService tratamientosService;

  @Autowired
  private VetService vetsService;

  @Autowired
  private PetsService petsService;

  @Autowired
  private DrogasService drogasService;

  @Autowired
  private ReporteExcelService reporteExcelService;

  // ----- Mostrar Admin (READ) -----
  // http://localhost:8080/admin/{id}
  @GetMapping("/{id}")
  public ResponseEntity<Admin> getAdmin(@PathVariable Long id) {
    return new ResponseEntity<>(adminsService.findById(id), HttpStatus.OK);
  }

  // http://localhost:8080/admin/{id}/exists
  @GetMapping("/{id}/exists")
  public ResponseEntity<Void> checkAdmin(@PathVariable Long id) {
    adminsService.findById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  // http://localhost:8080/admin/ultimos-tratamientos-count
  @GetMapping("/ultimos-tratamientos-count")
  public ResponseEntity<List<TratamientosMesDTO>> getUltimosTratamientos() {
    return new ResponseEntity<>(tratamientosService.getNumTratamientos6Meses(), HttpStatus.OK);
  }

  // http://localhost:8080/admin/droga-tratamientos-mes-count
  @GetMapping("/droga-tratamientos-mes-count")
  public ResponseEntity<List<DrogaTratamientoCountDTO>> getTratamientosMedicamentoCount() {
    return new ResponseEntity<>(tratamientosService.getDrogaTratamientosMesCount(), HttpStatus.OK);
  }

  // http://localhost:8080/admin/vets-count
  @GetMapping("/vets-count")
  public ResponseEntity<Long> getVetsCount() {
    return new ResponseEntity<>(vetsService.getVetsCount(), HttpStatus.OK);
  }

  // http://localhost:8080/admin/vets-inactivos-count
  @GetMapping("/vets-inactivos-count")
  public ResponseEntity<Long> getVetsInactivosCount() {
    return new ResponseEntity<>(vetsService.getVetsInactivosCount(), HttpStatus.OK);
  }

  // http://localhost:8080/admin/vets-activos-count
  @GetMapping("/vets-activos-count")
  public ResponseEntity<Long> getVetsActivosCount() {
    return new ResponseEntity<>(vetsService.getVetsActivosCount(), HttpStatus.OK);
  }

  // http://localhost:8080/admin/ventas-totales
  @GetMapping("/ventas-totales")
  public ResponseEntity<Long> getVentasTotales() {
    return new ResponseEntity<>(drogasService.getVentasTotales(), HttpStatus.OK);
  }

  // http://localhost:8080/admin/ganancias-totales
  @GetMapping("/ganancias-totales")
  public ResponseEntity<Long> getGananciasTotales() {
    return new ResponseEntity<>(drogasService.getGananciasTotales(), HttpStatus.OK);
  }

  // http://localhost:8080/admin/top5-drogas
  @GetMapping("/top5-drogas")
  public ResponseEntity<List<TopDTO>> getTop5Drogas() {
    return new ResponseEntity<>(drogasService.getTop5Drogas(), HttpStatus.OK);
  }

  // http://localhost:8080/admin/top5-enfermedades
  @GetMapping("/top5-enfermedades")
  public ResponseEntity<List<TopDTO>> getTop5Enfermedades() {
    return new ResponseEntity<>(petsService.getTop5Enfermedades(), HttpStatus.OK);
  }

  // http://localhost:8080/admin/drogas-bajas-stock
  @GetMapping("/drogas-bajas-stock")
  public ResponseEntity<List<StockDrogaDTO>> getDrogasBajasStock() {
    return new ResponseEntity<>(drogasService.getDrogasBajasStock(), HttpStatus.OK);
  }

  // http://localhost:8080/admin/reporte-ultimo-mes-excel
  @GetMapping("/reporte-ultimo-mes-excel")
  public ResponseEntity<byte[]> reporteUltimoMesExcel() throws IOException {
    byte[] archivo = reporteExcelService.generarReporteUltimoMes();

    String nombreArchivo =
      "reporte-biskit-" +
      LocalDate.now()
        .minusMonths(1)
        .getMonth()
        .getDisplayName(TextStyle.FULL, new Locale("es", "CO"))
        .toLowerCase() +
      "-" +
      LocalDate.now().minusMonths(1).getYear() +
      ".xlsx";

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
      .contentType(
        MediaType.parseMediaType(
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        )
      )
      .body(archivo);
  }

  @GetMapping("/details")
  public Admin buscarAdmin() {
    Admin admin = adminsService.findByUsuario(
      SecurityContextHolder.getContext().getAuthentication().getName()
    );
    return admin;
  }
}
