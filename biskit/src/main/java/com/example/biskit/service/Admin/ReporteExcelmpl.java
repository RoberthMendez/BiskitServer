package com.example.biskit.service.Admin;

import com.example.biskit.entities.DTOs.KPIs.DrogaTratamientoCountDTO;
import com.example.biskit.entities.DTOs.KPIs.TopDTO;
import com.example.biskit.service.Pets.PetsService;
import com.example.biskit.service.Tratamientos.TratamientosService;
import com.example.biskit.service.Vets.VetService;
import jakarta.transaction.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReporteExcelmpl implements ReporteExcelService {

  @Autowired
  private VetService vetService;

  @Autowired
  private PetsService petsService;

  @Autowired
  private TratamientosService tratamientosService;

  public byte[] generarReporteUltimoMes() throws IOException {
    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
      // ── Estilos ──────────────────────────────────────────────
      XSSFCellStyle estiloPrincipal = crearEstiloPrincipal(workbook);
      XSSFCellStyle estiloEncabezado = crearEstiloEncabezado(workbook);
      XSSFCellStyle estiloSubtitulo = crearEstiloSubtitulo(workbook);
      XSSFCellStyle estiloDato = crearEstiloDato(workbook);
      XSSFCellStyle estiloDatoAlt = crearEstiloDatoAlt(workbook);
      XSSFCellStyle estiloNumero = crearEstiloNumero(workbook);

      // ── Mes del reporte ──────────────────────────────────────
      LocalDate ultimoMes = LocalDate.now().minusMonths(1);
      String nombreMes = ultimoMes
        .getMonth()
        .getDisplayName(TextStyle.FULL, new Locale("es", "CO"));
      String periodoTexto = nombreMes.toUpperCase() + " " + ultimoMes.getYear();

      // ════════════════════════════════════════════════════════
      // HOJA 1 — Resumen General
      // ════════════════════════════════════════════════════════
      XSSFSheet hoja1 = workbook.createSheet("Resumen General");
      hoja1.setColumnWidth(0, 9000);
      hoja1.setColumnWidth(1, 7000);
      int fila = 0;

      // Título principal
      fila = crearTitulo(
        hoja1,
        estiloPrincipal,
        workbook,
        "REPORTE MENSUAL — VETERINARIA BISKIT",
        fila,
        1
      );
      fila = crearTitulo(hoja1, estiloSubtitulo, workbook, "Período: " + periodoTexto, fila, 1);
      fila++; // fila vacía

      // Sección: Veterinarios
      fila = crearEncabezadoSeccion(hoja1, estiloEncabezado, "VETERINARIOS", fila, 1);
      fila = crearFila(
        hoja1,
        estiloDato,
        "Total de veterinarios",
        vetService.getVetsCount(),
        estiloNumero,
        fila
      );
      fila = crearFila(
        hoja1,
        estiloDatoAlt,
        "Veterinarios activos",
        vetService.getVetsActivosCount(),
        estiloNumero,
        fila
      );
      fila = crearFila(
        hoja1,
        estiloDato,
        "Veterinarios inactivos",
        vetService.getVetsInactivosCount(),
        estiloNumero,
        fila
      );
      fila++;

      // Sección: Mascotas
      fila = crearEncabezadoSeccion(hoja1, estiloEncabezado, "MASCOTAS", fila, 1);
      fila = crearFila(
        hoja1,
        estiloDato,
        "Total de mascotas",
        petsService.getPetsCount(),
        estiloNumero,
        fila
      );
      fila = crearFila(
        hoja1,
        estiloDatoAlt,
        "Mascotas activas",
        petsService.getMascotasActivasCount(),
        estiloNumero,
        fila
      );
      fila = crearFila(
        hoja1,
        estiloDato,
        "Mascotas inactivas",
        petsService.getPetsInactivosCount(),
        estiloNumero,
        fila
      );
      fila++;

      // Sección: Tratamientos y ventas
      fila = crearEncabezadoSeccion(
        hoja1,
        estiloEncabezado,
        "TRATAMIENTOS Y VENTAS — " + periodoTexto,
        fila,
        1
      );
      fila = crearFila(
        hoja1,
        estiloDato,
        "Total de tratamientos",
        tratamientosService.countTratamientosUltimoMes(),
        estiloNumero,
        fila
      );
      fila = crearFila(
        hoja1,
        estiloDatoAlt,
        "Unidades de drogas vendidas",
        tratamientosService.getVentasTotalesMes(),
        estiloNumero,
        fila
      );
      fila = crearFila(
        hoja1,
        estiloDato,
        "Ganancias por drogas (COP)",
        tratamientosService.getGananciasTotalesMes(),
        estiloNumero,
        fila
      );

      // ════════════════════════════════════════════════════════
      // HOJA 2 — Top 5 Drogas
      // ════════════════════════════════════════════════════════
      XSSFSheet hoja2 = workbook.createSheet("Top 5 Drogas");
      hoja2.setColumnWidth(0, 3000);
      hoja2.setColumnWidth(1, 9000);
      hoja2.setColumnWidth(2, 5000);
      int fila2 = 0;

      fila2 = crearTitulo(hoja2, estiloPrincipal, workbook, "TOP 5 DROGAS MÁS VENDIDAS", fila2, 2);
      fila2 = crearTitulo(hoja2, estiloSubtitulo, workbook, "Período: " + periodoTexto, fila2, 2);
      fila2++;

      // Encabezados de tabla
      Row encTop = hoja2.createRow(fila2++);
      crearCeldaEstilo(encTop, 0, "PUESTO", estiloEncabezado);
      crearCeldaEstilo(encTop, 1, "MEDICAMENTO", estiloEncabezado);
      crearCeldaEstilo(encTop, 2, "VECES USADO", estiloEncabezado);

      List<TopDTO> top5Drogas = tratamientosService.getTop5DrogasUltimoMes();
      for (int i = 0; i < top5Drogas.size(); i++) {
        TopDTO dto = top5Drogas.get(i);
        XSSFCellStyle estilo = (i % 2 == 0) ? estiloDato : estiloDatoAlt;
        Row row = hoja2.createRow(fila2++);
        crearCeldaEstilo(row, 0, "#" + dto.getTop(), estilo);
        crearCeldaEstilo(row, 1, dto.getNombre(), estilo);
        crearCeldaNumero(row, 2, dto.getCount(), estiloNumero);
      }

      // ════════════════════════════════════════════════════════
      // HOJA 3 — Enfermedades Comunes
      // ════════════════════════════════════════════════════════
      XSSFSheet hoja3 = workbook.createSheet("Enfermedades Comunes");
      hoja3.setColumnWidth(0, 3000);
      hoja3.setColumnWidth(1, 9000);
      hoja3.setColumnWidth(2, 5000);
      int fila3 = 0;

      fila3 = crearTitulo(
        hoja3,
        estiloPrincipal,
        workbook,
        "TOP 5 ENFERMEDADES MÁS COMUNES",
        fila3,
        2
      );
      fila3++;

      Row encEnf = hoja3.createRow(fila3++);
      crearCeldaEstilo(encEnf, 0, "PUESTO", estiloEncabezado);
      crearCeldaEstilo(encEnf, 1, "ENFERMEDAD", estiloEncabezado);
      crearCeldaEstilo(encEnf, 2, "CASOS", estiloEncabezado);

      List<TopDTO> top5Enf = petsService.getTop5Enfermedades();
      for (int i = 0; i < top5Enf.size(); i++) {
        TopDTO dto = top5Enf.get(i);
        XSSFCellStyle estilo = (i % 2 == 0) ? estiloDato : estiloDatoAlt;
        Row row = hoja3.createRow(fila3++);
        crearCeldaEstilo(row, 0, "#" + dto.getTop(), estilo);
        crearCeldaEstilo(row, 1, dto.getNombre(), estilo);
        crearCeldaNumero(row, 2, dto.getCount(), estiloNumero);
      }

      // ════════════════════════════════════════════════════════
      // HOJA 4 — Tratamientos por Droga
      // ════════════════════════════════════════════════════════
      XSSFSheet hoja4 = workbook.createSheet("Tratamientos por Droga");
      hoja4.setColumnWidth(0, 9000);
      hoja4.setColumnWidth(1, 5000);
      int fila4 = 0;

      fila4 = crearTitulo(hoja4, estiloPrincipal, workbook, "TRATAMIENTOS POR DROGA", fila4, 1);
      fila4 = crearTitulo(hoja4, estiloSubtitulo, workbook, "Período: " + periodoTexto, fila4, 1);
      fila4++;

      Row encDroga = hoja4.createRow(fila4++);
      crearCeldaEstilo(encDroga, 0, "MEDICAMENTO", estiloEncabezado);
      crearCeldaEstilo(encDroga, 1, "N° TRATAMIENTOS", estiloEncabezado);

      List<DrogaTratamientoCountDTO> drogaCounts =
        tratamientosService.getDrogaTratamientosMesCount();
      for (int i = 0; i < drogaCounts.size(); i++) {
        DrogaTratamientoCountDTO dto = drogaCounts.get(i);
        XSSFCellStyle estilo = (i % 2 == 0) ? estiloDato : estiloDatoAlt;
        Row row = hoja4.createRow(fila4++);
        crearCeldaEstilo(row, 0, dto.getDrogaNombre(), estilo);
        crearCeldaNumero(row, 1, dto.getCount(), estiloNumero);
      }

      // ── Serializar a bytes ───────────────────────────────────
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      workbook.write(out);
      return out.toByteArray();
    }
  }

  // ════════════════════════════════════════════════════════════════
  // HELPERS — filas y celdas
  // ════════════════════════════════════════════════════════════════

  private int crearTitulo(
    XSSFSheet hoja,
    XSSFCellStyle estilo,
    XSSFWorkbook wb,
    String texto,
    int fila,
    int lastCol
  ) {
    Row row = hoja.createRow(fila);
    row.setHeightInPoints(28);
    Cell cell = row.createCell(0);
    cell.setCellValue(texto);
    cell.setCellStyle(estilo);
    hoja.addMergedRegion(new CellRangeAddress(fila, fila, 0, lastCol));
    return fila + 1;
  }

  private int crearEncabezadoSeccion(
    XSSFSheet hoja,
    XSSFCellStyle estilo,
    String texto,
    int fila,
    int lastCol
  ) {
    Row row = hoja.createRow(fila);
    row.setHeightInPoints(20);
    Cell cell = row.createCell(0);
    cell.setCellValue(texto);
    cell.setCellStyle(estilo);
    hoja.addMergedRegion(new CellRangeAddress(fila, fila, 0, lastCol));
    return fila + 1;
  }

  private int crearFila(
    XSSFSheet hoja,
    XSSFCellStyle estiloTexto,
    String etiqueta,
    Long valor,
    XSSFCellStyle estiloNum,
    int fila
  ) {
    Row row = hoja.createRow(fila);
    row.setHeightInPoints(18);
    Cell cEtiqueta = row.createCell(0);
    cEtiqueta.setCellValue(etiqueta);
    cEtiqueta.setCellStyle(estiloTexto);
    Cell cValor = row.createCell(1);
    cValor.setCellValue(valor != null ? valor : 0L);
    cValor.setCellStyle(estiloNum);
    return fila + 1;
  }

  private void crearCeldaEstilo(Row row, int col, String valor, XSSFCellStyle estilo) {
    Cell cell = row.createCell(col);
    cell.setCellValue(valor);
    cell.setCellStyle(estilo);
  }

  private void crearCeldaNumero(Row row, int col, Long valor, XSSFCellStyle estilo) {
    Cell cell = row.createCell(col);
    cell.setCellValue(valor != null ? valor : 0L);
    cell.setCellStyle(estilo);
  }

  // ════════════════════════════════════════════════════════════════
  // ESTILOS
  // ════════════════════════════════════════════════════════════════

  private XSSFCellStyle crearEstiloPrincipal(XSSFWorkbook wb) {
    XSSFCellStyle s = wb.createCellStyle();
    s.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 43, (byte) 83, (byte) 146 }, null)); // #2b5392
    s.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    s.setAlignment(HorizontalAlignment.CENTER);
    s.setVerticalAlignment(VerticalAlignment.CENTER);
    XSSFFont f = wb.createFont();
    f.setColor(new XSSFColor(new byte[] { (byte) 255, (byte) 255, (byte) 255 }, null));
    f.setBold(true);
    f.setFontHeightInPoints((short) 14);
    s.setFont(f);
    return s;
  }

  private XSSFCellStyle crearEstiloSubtitulo(XSSFWorkbook wb) {
    XSSFCellStyle s = wb.createCellStyle();
    s.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 74, (byte) 111, (byte) 165 }, null));
    s.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    s.setAlignment(HorizontalAlignment.CENTER);
    s.setVerticalAlignment(VerticalAlignment.CENTER);
    XSSFFont f = wb.createFont();
    f.setColor(new XSSFColor(new byte[] { (byte) 255, (byte) 255, (byte) 255 }, null));
    f.setFontHeightInPoints((short) 11);
    s.setFont(f);
    return s;
  }

  private XSSFCellStyle crearEstiloEncabezado(XSSFWorkbook wb) {
    XSSFCellStyle s = wb.createCellStyle();
    s.setFillForegroundColor(new XSSFColor(new byte[] { (byte) 26, (byte) 58, (byte) 107 }, null)); // #1a3a6b
    s.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    s.setAlignment(HorizontalAlignment.LEFT);
    s.setVerticalAlignment(VerticalAlignment.CENTER);
    setBorder(s);
    XSSFFont f = wb.createFont();
    f.setColor(new XSSFColor(new byte[] { (byte) 255, (byte) 255, (byte) 255 }, null));
    f.setBold(true);
    f.setFontHeightInPoints((short) 11);
    s.setFont(f);
    return s;
  }

  private XSSFCellStyle crearEstiloDato(XSSFWorkbook wb) {
    XSSFCellStyle s = wb.createCellStyle();
    s.setFillForegroundColor(
      new XSSFColor(new byte[] { (byte) 255, (byte) 255, (byte) 255 }, null)
    );
    s.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    s.setVerticalAlignment(VerticalAlignment.CENTER);
    setBorder(s);
    XSSFFont f = wb.createFont();
    f.setColor(new XSSFColor(new byte[] { (byte) 74, (byte) 74, (byte) 74 }, null));
    f.setFontHeightInPoints((short) 11);
    s.setFont(f);
    return s;
  }

  private XSSFCellStyle crearEstiloDatoAlt(XSSFWorkbook wb) {
    XSSFCellStyle s = wb.createCellStyle();
    s.setFillForegroundColor(
      new XSSFColor(new byte[] { (byte) 235, (byte) 241, (byte) 251 }, null)
    );
    s.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    s.setVerticalAlignment(VerticalAlignment.CENTER);
    setBorder(s);
    XSSFFont f = wb.createFont();
    f.setColor(new XSSFColor(new byte[] { (byte) 74, (byte) 74, (byte) 74 }, null));
    f.setFontHeightInPoints((short) 11);
    s.setFont(f);
    return s;
  }

  private XSSFCellStyle crearEstiloNumero(XSSFWorkbook wb) {
    XSSFCellStyle s = wb.createCellStyle();
    s.setFillForegroundColor(
      new XSSFColor(new byte[] { (byte) 255, (byte) 255, (byte) 255 }, null)
    );
    s.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    s.setAlignment(HorizontalAlignment.CENTER);
    s.setVerticalAlignment(VerticalAlignment.CENTER);
    setBorder(s);
    XSSFFont f = wb.createFont();
    f.setBold(true);
    f.setColor(new XSSFColor(new byte[] { (byte) 43, (byte) 83, (byte) 146 }, null));
    f.setFontHeightInPoints((short) 11);
    s.setFont(f);
    return s;
  }

  private void setBorder(XSSFCellStyle s) {
    s.setBorderBottom(BorderStyle.THIN);
    s.setBorderTop(BorderStyle.THIN);
    s.setBorderLeft(BorderStyle.THIN);
    s.setBorderRight(BorderStyle.THIN);
    XSSFColor gris = new XSSFColor(new byte[] { (byte) 200, (byte) 200, (byte) 200 }, null);
    s.setBottomBorderColor(gris);
    s.setTopBorderColor(gris);
    s.setLeftBorderColor(gris);
    s.setRightBorderColor(gris);
  }
}
