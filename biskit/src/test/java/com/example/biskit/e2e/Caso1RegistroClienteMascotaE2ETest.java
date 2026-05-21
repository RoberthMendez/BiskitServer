package com.example.biskit.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("default")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class Caso1RegistroClienteMascotaE2ETest {

  private final String BASE_URL = "http://localhost:4200";

  private final String VET_USUARIO = "ana.gonzalez@biskit.com";
  private final String VET_PASSWORD = "101";

  private WebDriver driver;
  private WebDriverWait wait;

  @BeforeEach
  public void init() {
    WebDriverManager.chromedriver().setup();

    ChromeOptions chromeOptions = new ChromeOptions();

    chromeOptions.addArguments("--disable-notifications");
    chromeOptions.addArguments("--disable-extensions");
    chromeOptions.addArguments("--start-maximized");
    // chromeOptions.addArguments("--headless");

    this.driver = new ChromeDriver(chromeOptions);
    this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
  }

  @Test
  public void SystemTest_caso1_registroClienteMascota() {
    String nombreCliente = "andyprueba";
    String cedulaCliente = "1027400439";
    String correoCliente = "andyprueba@gmail.com";
    String celularCliente = "3001234567";

    String nombreMascota = "tomymascotaprueba";
    String especieMascota = "Gato";
    String razaMascota = "Abisinio";
    String enfermedadMascota = "Alergía";
    String fechaMascota = "2020-01-01";
    String pesoMascota = "10.5";
    String fotoMascota =
      "https://cdn.wamiz.fr/cdn-cgi/image/format=auto,quality=80,width=1200,height=675,fit=cover/article/main-picture/5c5328bde77a3957455947.jpg";

    // 1. Entrar desde la landing
    driver.get(BASE_URL + "/");

    esperarTexto("Iniciar Sesión");
    clickPorTexto("Iniciar Sesión");

    wait.until(ExpectedConditions.urlContains("/login"));
    esperarVisible(By.id("usuario"));

    // 2. Intentar iniciar sesión con datos incorrectos
    escribirPrimerDisponible("usuario.mal@biskit.com", By.id("usuario"), By.name("usuario"));

    escribirPrimerDisponible(
      "clave-mal",
      By.id("contrasena"),
      By.name("contrasena"),
      By.cssSelector("input[type='password']")
    );

    clickPrimerDisponible(
      By.xpath("//button[contains(normalize-space(.), 'Ingresar')]"),
      By.cssSelector("button[type='submit']")
    );

    esperarVisible(By.tagName("body"));

    Assertions.assertThat(
      driver.getCurrentUrl().contains("/login") ||
        textoPagina().contains("incorrect") ||
        textoPagina().contains("error")
    ).isTrue();

    // 3. Iniciar sesión correctamente como veterinario
    escribirPrimerDisponible(VET_USUARIO, By.id("usuario"), By.name("usuario"));

    escribirPrimerDisponible(
      VET_PASSWORD,
      By.id("contrasena"),
      By.name("contrasena"),
      By.cssSelector("input[type='password']")
    );

    clickPrimerDisponible(
      By.xpath("//button[contains(normalize-space(.), 'Ingresar')]"),
      By.cssSelector("button[type='submit']")
    );

    wait.until(ExpectedConditions.urlContains("/vet/"));
    esperarTexto("Hola de nuevo");

    // 4. Registrar cliente
    clickAccesoRapido("Cliente");

    esperarTexto("Nuevo Cliente");

    escribir(By.name("nombre"), nombreCliente);
    escribir(By.name("cedula"), cedulaCliente);
    escribir(By.name("correo"), "");
    escribir(By.name("celular"), celularCliente);

    clickBotonPorTexto("Registrar Cliente");

    Assertions.assertThat(
      driver.getCurrentUrl().contains("/clients/add") ||
        textoPagina().contains("correo") ||
        textoPagina().contains("error") ||
        textoPagina().contains("inválido") ||
        textoPagina().contains("invalido")
    ).isTrue();

    escribir(By.name("correo"), correoCliente);
    clickBotonPorTexto("Registrar Cliente");

    wait.until(
      ExpectedConditions.or(
        ExpectedConditions.urlContains("/clients"),
        ExpectedConditions.textToBePresentInElementLocated(
          By.tagName("body"),
          "Clientes Registrados"
        ),
        ExpectedConditions.textToBePresentInElementLocated(
          By.tagName("body"),
          "Cliente guardado correctamente"
        )
      )
    );

    // 5. Volver al panel veterinario
    clickBackToPanel("Panel de Veterinario");

    // 6. Registrar mascota
    clickAccesoRapido("Mascota");

    esperarTexto("Nuevo Paciente");

    escribir(By.name("nombre"), nombreMascota);

    seleccionarDropdown(By.name("especieSearch"), especieMascota, especieMascota);
    seleccionarDropdown(By.name("razaSearch"), razaMascota, razaMascota);

    escribirFecha(fechaMascota);

    escribir(By.name("peso"), pesoMascota);

    /*
     * Se escribe la cédula para buscar al dueño,
     * pero se selecciona la opción visible con el nombre del cliente.
     */
    seleccionarDropdown(By.name("clienteSearch"), cedulaCliente, nombreCliente);

    seleccionarDropdown(By.name("enfermedadSearch"), enfermedadMascota, enfermedadMascota);

    if (existe(By.name("urlFoto"))) {
      escribir(By.name("urlFoto"), fotoMascota);
    }

    clickBotonPorTexto("Registrar Mascota");

    wait.until(
      ExpectedConditions.or(
        ExpectedConditions.urlContains("/pets"),
        ExpectedConditions.textToBePresentInElementLocated(
          By.tagName("body"),
          "Mascotas Registradas"
        ),
        ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), nombreMascota)
      )
    );

    esperarTextoNormalizado(nombreMascota);

    // 7. Cerrar sesión para volver a la landing
    clickCerrarSesion();

    wait.until(ExpectedConditions.urlToBe(BASE_URL + "/"));
    esperarTexto("Iniciar Sesión");

    // 8. Iniciar sesión como cliente
    clickPorTexto("Iniciar Sesión");

    wait.until(ExpectedConditions.urlContains("/login"));
    esperarVisible(By.id("usuario"));

    loginCliente(correoCliente, cedulaCliente);

    wait.until(ExpectedConditions.urlContains("/client/"));
    esperarTexto("Tus Mascotas");

    // 9. Buscar la sección de mascotas del cliente
    WebElement seccionMascotas = buscarElementoPorTextoNormalizado("Tus Mascotas");
    scrollAlElemento(seccionMascotas);

    /*
     * Espera a que la mascota aparezca en la vista del cliente.
     * Esto evita fallar por mayúsculas, espacios o carga lenta.
     */
    esperarTextoNormalizado(nombreMascota);

    abrirMascotaCliente(nombreMascota);

    // 10. Validar los datos de la mascota en el detalle
    esperarTextoNormalizado("Tratamientos");
    esperarTextoNormalizado(nombreMascota);

    validarDatosMascota(
      nombreMascota,
      especieMascota,
      razaMascota,
      enfermedadMascota,
      fechaMascota,
      pesoMascota,
      fotoMascota
    );
  }

  private void loginCliente(String usuario, String contrasena) {
    escribirPrimerDisponible(usuario, By.id("usuario"), By.name("usuario"));

    escribirPrimerDisponible(
      contrasena,
      By.id("contrasena"),
      By.name("contrasena"),
      By.cssSelector("input[type='password']")
    );

    clickPrimerDisponible(
      By.xpath("//button[contains(normalize-space(.), 'Ingresar')]"),
      By.cssSelector("button[type='submit']")
    );
  }

  private void abrirMascotaCliente(String nombreMascota) {
    WebElement nombre = buscarElementoPorTextoNormalizado(nombreMascota);
    WebElement cardMascota = buscarCardMascota(nombre, nombreMascota);

    scrollAlElemento(cardMascota);

    String urlAntes = driver.getCurrentUrl();

    List<WebElement> botones = cardMascota.findElements(By.xpath(".//a | .//button"));

    for (WebElement boton : botones) {
      if (!boton.isDisplayed()) {
        continue;
      }

      String textoBoton = normalizarTexto(boton.getText());

      if (textoBoton.contains("activo")) {
        continue;
      }

      clickElemento(boton);

      if (esperarDetalleMascota(urlAntes)) {
        return;
      }
    }

    List<WebElement> imagenes = cardMascota.findElements(By.tagName("img"));

    if (!imagenes.isEmpty()) {
      clickElemento(imagenes.get(0));

      if (esperarDetalleMascota(urlAntes)) {
        return;
      }
    }

    clickElemento(cardMascota);

    boolean entroAlDetalle = esperarDetalleMascota(urlAntes);

    if (!entroAlDetalle) {
      throw new RuntimeException("No se pudo abrir la mascota del cliente: " + nombreMascota);
    }
  }

  private WebElement buscarCardMascota(WebElement elementoNombre, String nombreMascota) {
    WebElement actual = elementoNombre;

    for (int i = 0; i < 8; i++) {
      String texto = actual.getText();

      boolean contieneNombre =
        texto != null && normalizarTexto(texto).contains(normalizarTexto(nombreMascota));

      boolean tieneImagen = !actual.findElements(By.tagName("img")).isEmpty();

      boolean pareceCard = actual.getRect().getHeight() > 80 && actual.getRect().getWidth() > 120;

      if (contieneNombre && tieneImagen && pareceCard) {
        return actual;
      }

      try {
        actual = actual.findElement(By.xpath("./.."));
      } catch (Exception e) {
        return elementoNombre;
      }
    }

    return elementoNombre;
  }

  private boolean esperarDetalleMascota(String urlAntes) {
    WebDriverWait esperaCorta = new WebDriverWait(driver, Duration.ofSeconds(5));

    try {
      esperaCorta.until(driver -> {
        String urlActual = driver.getCurrentUrl();
        String texto = normalizarTexto(driver.findElement(By.tagName("body")).getText());

        return !urlActual.equals(urlAntes) || texto.contains("tratamientos");
      });

      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }

  private void validarDatosMascota(
    String nombreMascota,
    String especieMascota,
    String razaMascota,
    String enfermedadMascota,
    String fechaMascota,
    String pesoMascota,
    String fotoMascota
  ) {
    String texto = normalizarTexto(driver.findElement(By.tagName("body")).getText());

    int edadEsperada = Period.between(LocalDate.parse(fechaMascota), LocalDate.now()).getYears();

    Assertions.assertThat(texto.contains(normalizarTexto(nombreMascota))).isTrue();
    Assertions.assertThat(texto.contains(normalizarTexto(especieMascota))).isTrue();
    Assertions.assertThat(texto.contains(normalizarTexto(razaMascota))).isTrue();
    Assertions.assertThat(texto.contains(normalizarTexto(enfermedadMascota))).isTrue();

    Assertions.assertThat(
      texto.contains(normalizarTexto(pesoMascota)) ||
        texto.contains(normalizarTexto(pesoMascota.replace(".", ",")))
    ).isTrue();

    Assertions.assertThat(texto.contains(String.valueOf(edadEsperada))).isTrue();

    List<WebElement> imagenes = driver.findElements(By.tagName("img"));

    boolean fotoEncontrada = false;

    for (WebElement imagen : imagenes) {
      String src = imagen.getAttribute("src");

      if (src != null && (src.contains("wamiz") || src.contains("5c5328bde77a3957455947"))) {
        fotoEncontrada = true;
        break;
      }
    }

    Assertions.assertThat(fotoEncontrada).isTrue();
  }

  private void clickAccesoRapido(String tipo) {
    click(
      By.xpath(
        "//article[.//h2[contains(normalize-space(.), 'Crear Nuevo Registro')]]" +
          "//button[contains(normalize-space(.), 'Agregar " +
          tipo +
          "')]"
      )
    );
  }

  private void clickBackToPanel(String textoPanel) {
    click(
      By.xpath(
        "//a[contains(@class, 'back-link')][.//h2[contains(normalize-space(.), '" +
          textoPanel +
          "')]]"
      )
    );
  }

  private void clickCerrarSesion() {
    if (existe(By.xpath("//button[@title='Cerrar sesión']"))) {
      click(By.xpath("//button[@title='Cerrar sesión']"));
      return;
    }

    if (existe(By.xpath("//button[contains(@aria-label, 'Cerrar')]"))) {
      click(By.xpath("//button[contains(@aria-label, 'Cerrar')]"));
      return;
    }

    if (existe(By.xpath("//button[.//*[name()='svg'] and ancestor::header]"))) {
      List<WebElement> botones = driver.findElements(
        By.xpath("//button[.//*[name()='svg'] and ancestor::header]")
      );
      WebElement ultimoBoton = botones.get(botones.size() - 1);

      clickElemento(ultimoBoton);
      return;
    }

    clickPorTexto("Cerrar sesión");
  }

  private void seleccionarDropdown(By inputLocator, String textoBusqueda, String textoEsperado) {
    WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputLocator));
    scrollAlElemento(input);

    limpiarYEscribir(input, textoBusqueda);

    esperarTextoNormalizado(textoEsperado);

    WebElement opcion = wait.until(driver -> buscarOpcionDropdown(textoEsperado));

    clickElemento(opcion);

    boolean quedoSeleccionado = esperarValorInput(inputLocator, textoEsperado, 1);

    if (!quedoSeleccionado) {
      input = wait.until(ExpectedConditions.elementToBeClickable(inputLocator));
      scrollAlElemento(input);

      limpiarYEscribir(input, textoBusqueda);

      esperarTextoNormalizado(textoEsperado);

      input.sendKeys(Keys.ARROW_DOWN);
      input.sendKeys(Keys.ENTER);
    }

    input = wait.until(ExpectedConditions.elementToBeClickable(inputLocator));
    input.sendKeys(Keys.TAB);

    boolean valorFinalCorrecto = esperarValorInput(inputLocator, textoEsperado, 2);

    if (!valorFinalCorrecto) {
      String valorActual = driver.findElement(inputLocator).getAttribute("value");

      throw new RuntimeException(
        "No se seleccionó correctamente la opción del dropdown. " +
          "Buscado: " +
          textoBusqueda +
          ", esperado: " +
          textoEsperado +
          ", valor actual: " +
          valorActual
      );
    }
  }

  private WebElement buscarOpcionDropdown(String textoEsperado) {
    List<WebElement> candidatos = driver.findElements(
      By.xpath(
        "//*[not(self::input) and not(self::textarea) and not(self::script) and not(self::style)]"
      )
    );

    WebElement mejorOpcion = null;
    int menorArea = Integer.MAX_VALUE;

    for (WebElement candidato : candidatos) {
      if (!candidato.isDisplayed()) {
        continue;
      }

      String texto = candidato.getText();

      if (texto == null || texto.trim().isEmpty()) {
        continue;
      }

      if (!normalizarTexto(texto).contains(normalizarTexto(textoEsperado))) {
        continue;
      }

      String textoNormalizado = normalizarTexto(texto);

      if (
        textoNormalizado.contains("nuevo paciente") ||
        textoNormalizado.contains("registrar mascota") ||
        textoNormalizado.contains("crear nuevo registro") ||
        textoNormalizado.contains("agregar mascota") ||
        textoNormalizado.contains("agregar cliente") ||
        textoNormalizado.contains("panel de veterinario")
      ) {
        continue;
      }

      if (texto.length() > 180) {
        continue;
      }

      Rectangle rect = candidato.getRect();
      int alto = rect.getHeight();
      int ancho = rect.getWidth();

      if (alto <= 0 || ancho <= 0) {
        continue;
      }

      int area = alto * ancho;

      if (area < menorArea) {
        menorArea = area;
        mejorOpcion = candidato;
      }
    }

    return mejorOpcion;
  }

  private boolean esperarValorInput(By inputLocator, String textoEsperado, int segundos) {
    WebDriverWait esperaCorta = new WebDriverWait(driver, Duration.ofSeconds(segundos));

    try {
      esperaCorta.until(driver -> {
        List<WebElement> inputs = driver.findElements(inputLocator);

        if (inputs.isEmpty()) {
          return false;
        }

        return valorInputContiene(inputs.get(0), textoEsperado);
      });

      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }

  private boolean valorInputContiene(WebElement input, String textoEsperado) {
    String valor = input.getAttribute("value");

    if (valor == null) {
      return false;
    }

    return normalizarTexto(valor).contains(normalizarTexto(textoEsperado));
  }

  private void escribirFecha(String fecha) {
    List<WebElement> inputsFecha = driver.findElements(By.cssSelector("app-date-picker input"));

    if (!inputsFecha.isEmpty()) {
      WebElement inputFecha = inputsFecha.get(0);
      scrollAlElemento(inputFecha);

      limpiarYEscribir(inputFecha, fecha);
      inputFecha.sendKeys(Keys.TAB);

      return;
    }

    List<WebElement> datePickers = driver.findElements(By.cssSelector("app-date-picker#fecha"));

    if (!datePickers.isEmpty()) {
      WebElement datePicker = datePickers.get(0);
      scrollAlElemento(datePicker);

      ((JavascriptExecutor) driver).executeScript(
        "arguments[0].setAttribute('value', arguments[1]);" +
          "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
          "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
        datePicker,
        fecha
      );
    }
  }

  private void click(By locator) {
    WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
    clickElemento(elemento);
  }

  private void clickElemento(WebElement elemento) {
    scrollAlElemento(elemento);

    try {
      elemento.click();
      return;
    } catch (ElementClickInterceptedException e) {
      // intenta con Actions
    } catch (Exception e) {
      // intenta con Actions
    }

    try {
      new Actions(driver).moveToElement(elemento).pause(Duration.ofMillis(200)).click().perform();
      return;
    } catch (Exception e) {
      // último intento con JavaScript
    }

    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
  }

  private void escribir(By locator, String texto) {
    WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
    scrollAlElemento(elemento);

    try {
      limpiarYEscribir(elemento, texto);
    } catch (Exception e) {
      ((JavascriptExecutor) driver).executeScript(
        "arguments[0].focus();" +
          "arguments[0].value = '';" +
          "arguments[0].value = arguments[1];" +
          "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
          "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
        elemento,
        texto
      );
    }
  }

  private void limpiarYEscribir(WebElement elemento, String texto) {
    elemento.click();
    elemento.sendKeys(Keys.chord(Keys.CONTROL, "a"));
    elemento.sendKeys(Keys.DELETE);
    elemento.sendKeys(texto);
  }

  private WebElement buscarElementoPorTextoNormalizado(String textoBuscado) {
    return wait.until(driver -> {
      List<WebElement> elementos = driver.findElements(
        By.xpath("//*[not(self::script) and not(self::style)]")
      );

      WebElement mejorElemento = null;
      int menorTexto = Integer.MAX_VALUE;

      for (WebElement elemento : elementos) {
        try {
          if (!elemento.isDisplayed()) {
            continue;
          }

          String texto = elemento.getText();

          if (texto == null || texto.trim().isEmpty()) {
            continue;
          }

          if (!normalizarTexto(texto).contains(normalizarTexto(textoBuscado))) {
            continue;
          }

          if (texto.length() < menorTexto) {
            menorTexto = texto.length();
            mejorElemento = elemento;
          }
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
          // El elemento fue removido del DOM, continúa con el siguiente
          continue;
        }
      }

      return mejorElemento;
    });
  }

  private void esperarVisible(By locator) {
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  private void esperarTexto(String texto) {
    wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), texto));
  }

  private void esperarTextoNormalizado(String texto) {
    wait.until(driver -> {
      String textoBody = driver.findElement(By.tagName("body")).getText();

      return normalizarTexto(textoBody).contains(normalizarTexto(texto));
    });
  }

  private void clickPorTexto(String texto) {
    click(
      By.xpath(
        "//*[self::a or self::button or self::article][contains(normalize-space(.), '" +
          texto +
          "')]"
      )
    );
  }

  private void clickBotonPorTexto(String texto) {
    click(By.xpath("//button[contains(normalize-space(.), '" + texto + "')]"));
  }

  private void escribirPrimerDisponible(String texto, By... locators) {
    for (By locator : locators) {
      try {
        escribir(locator, texto);
        return;
      } catch (Exception e) {
        // intenta con el siguiente selector
      }
    }

    throw new RuntimeException("No se pudo escribir en ningún campo disponible");
  }

  private void clickPrimerDisponible(By... locators) {
    for (By locator : locators) {
      try {
        click(locator);
        return;
      } catch (Exception e) {
        // intenta con el siguiente selector
      }
    }

    throw new RuntimeException("No se pudo hacer click en ningún botón disponible");
  }

  private boolean existe(By locator) {
    return !driver.findElements(locator).isEmpty();
  }

  private String textoPagina() {
    return driver.findElement(By.tagName("body")).getText().toLowerCase();
  }

  private String normalizarTexto(String texto) {
    if (texto == null) {
      return "";
    }

    String textoSinTildes = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll(
      "\\p{M}",
      ""
    );

    return textoSinTildes.toLowerCase().replaceAll("\\s+", " ").trim();
  }

  private void scrollAlElemento(WebElement elemento) {
    ((JavascriptExecutor) driver).executeScript(
      "arguments[0].scrollIntoView({block: 'center'});",
      elemento
    );
  }

  @AfterEach
  void tearDown() {
    driver.quit();
  }
}
