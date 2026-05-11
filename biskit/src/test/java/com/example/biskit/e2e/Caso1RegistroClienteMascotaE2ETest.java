package com.example.biskit.e2e;

import java.time.Duration;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import io.github.bonigarcia.wdm.WebDriverManager;

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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void SystemTest_caso1_registroClienteMascota() {
        long timestamp = System.currentTimeMillis();

        String nombreCliente = "andyprueba";
        String cedulaCliente = "1027400439";
        String correoCliente = "andyprueba" + timestamp + "@correo.com";
        String celularCliente = "3001234567";

        String nombreMascota = "tomymascotaprueba";
        String fotoMascota = "https://cdn.wamiz.fr/cdn-cgi/image/format=auto,quality=80,width=1200,height=675,fit=cover/article/main-picture/5c5328bde77a3957455947.jpg";

        // Entrar desde la landing
        driver.get(BASE_URL + "/");
        esperarTexto("Iniciar Sesión");
        clickPrimerDisponible(
                By.xpath("//a[contains(normalize-space(.), 'Iniciar Sesión')]"),
                By.xpath("//button[contains(normalize-space(.), 'Iniciar Sesión')]")
        );

        wait.until(ExpectedConditions.urlContains("/login"));
        esperarVisible(By.id("usuario"));

        // Login incorrecto
        escribirPrimerDisponible("usuario.mal@biskit.com", By.id("usuario"), By.name("usuario"));
        escribirPrimerDisponible("clave-mal", By.id("contrasena"), By.name("contrasena"), By.cssSelector("input[type='password']"));
        clickPrimerDisponible(By.xpath("//button[contains(normalize-space(.), 'Ingresar')]"), By.cssSelector("button[type='submit']"));

        esperarVisible(By.tagName("body"));

        Assertions.assertThat(
                driver.getCurrentUrl().contains("/login")
                        || textoPagina().contains("incorrect")
                        || textoPagina().contains("error")
        ).isTrue();

        // Login correcto
        escribirPrimerDisponible(VET_USUARIO, By.id("usuario"), By.name("usuario"));
        escribirPrimerDisponible(VET_PASSWORD, By.id("contrasena"), By.name("contrasena"), By.cssSelector("input[type='password']"));
        clickPrimerDisponible(By.xpath("//button[contains(normalize-space(.), 'Ingresar')]"), By.cssSelector("button[type='submit']"));

        wait.until(ExpectedConditions.urlContains("/vet/"));
        pausaVisual();

        // Agregar cliente desde acceso rápido
        clickAccesoRapido("Cliente");
        esperarTexto("Nuevo Cliente");

        escribir(By.name("nombre"), nombreCliente);
        escribir(By.name("cedula"), cedulaCliente);
        escribir(By.name("correo"), "");
        escribir(By.name("celular"), celularCliente);

        clickBotonPorTexto("Registrar Cliente");

        Assertions.assertThat(
                driver.getCurrentUrl().contains("/clients/add")
                        || textoPagina().contains("correo")
                        || textoPagina().contains("error")
                        || textoPagina().contains("inválido")
                        || textoPagina().contains("invalido")
        ).isTrue();

        escribir(By.name("correo"), correoCliente);
        clickBotonPorTexto("Registrar Cliente");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/clients"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Clientes Registrados"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Cliente guardado correctamente")
        ));

        pausaVisual();

        // Volver al panel veterinario
        clickBackToPanel("Panel de Veterinario");

        // Agregar mascota desde acceso rápido
        clickAccesoRapido("Mascota");
        esperarTexto("Nuevo Paciente");

        escribir(By.name("nombre"), nombreMascota);

        seleccionarPrimeraOpcionDropdown(By.name("especieSearch"));
        seleccionarPrimeraOpcionDropdown(By.name("razaSearch"));

        escribirFecha("2020-01-01");

        escribir(By.name("peso"), "10.5");

        seleccionarDropdownPorTexto(By.name("clienteSearch"), cedulaCliente);

        seleccionarPrimeraOpcionDropdown(By.name("enfermedadSearch"));

        if (existe(By.name("urlFoto"))) {
            escribir(By.name("urlFoto"), fotoMascota);
        }

        clickBotonPorTexto("Registrar Mascota");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/pets"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Mascotas Registradas"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Mascota")
        ));

        pausaVisual();

        // Volver al panel y buscar el cliente
        clickBackToPanel("Panel Veterinario");

        esperarTexto("Clientes de Biskit");

        click(By.xpath("//article[.//h3[contains(normalize-space(.), 'Clientes de Biskit')]]"));

        esperarTexto("Clientes Registrados");

        escribir(By.cssSelector("input[placeholder='Buscar por cédula...']"), cedulaCliente);

        esperarTexto(cedulaCliente);

        WebElement filaCliente = driver.findElement(By.xpath("//tr[.//td[contains(normalize-space(.), '" + cedulaCliente + "')]]"));
        filaCliente.click();

        wait.until(ExpectedConditions.urlMatches(".*/clients?/\\d+.*"));
        pausaVisual();

        WebElement seccionMascotas = driver.findElement(By.xpath(
                "//section[.//h1[contains(normalize-space(.), 'Mascotas Registradas')]]"
        ));

        scrollAlElemento(seccionMascotas);

        String textoPagina = normalizarTexto(driver.findElement(By.tagName("body")).getText());
        String nombreEsperado = normalizarTexto(nombreMascota);
        String timestampEsperado = String.valueOf(timestamp);

        Assertions.assertThat(
                textoPagina.contains(nombreEsperado) || textoPagina.contains(timestampEsperado)
        ).isTrue();
    }

    private void escribir(By locator, String texto) {
        WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollAlElemento(elemento);

        try {
            elemento.click();
            elemento.clear();
            elemento.sendKeys(texto);
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].value = arguments[1];" +
                            "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                            "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                    elemento,
                    texto
            );
        }

        pausaVisual();
    }

    private void click(By locator) {
        WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollAlElemento(elemento);

        try {
            elemento.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
        }

        pausaVisual();
    }

    private void esperarVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void esperarTexto(String texto) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), texto));
        pausaVisual();
    }

    private void clickBotonPorTexto(String texto) {
        click(By.xpath("//button[contains(normalize-space(.), '" + texto + "')]"));
    }

    private void clickAccesoRapido(String tipo) {
        click(By.xpath(
                "//article[.//h2[contains(normalize-space(.), 'Crear Nuevo Registro')]]" +
                        "//button[contains(normalize-space(.), 'Agregar " + tipo + "')]"
        ));
    }

    private void clickBackToPanel(String textoPanel) {
        click(By.xpath("//a[contains(@class, 'back-link')][.//h2[contains(normalize-space(.), '" + textoPanel + "')]]"));
    }

    private void seleccionarPrimeraOpcionDropdown(By inputLocator) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputLocator));
        scrollAlElemento(input);
        input.click();

        List<WebElement> opciones = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(".custom-scroll .cursor-pointer")
        ));

        for (WebElement opcion : opciones) {
            String texto = opcion.getText().trim().toLowerCase();

            if (!texto.isBlank()
                    && !texto.contains("agregar raza")
                    && !texto.contains("agregar enfermedad")) {
                scrollAlElemento(opcion);
                opcion.click();
                pausaVisual();
                return;
            }
        }

        if (!opciones.isEmpty()) {
            opciones.get(0).click();
        }

        pausaVisual();
    }

    private void seleccionarDropdownPorTexto(By inputLocator, String textoBusqueda) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputLocator));
        scrollAlElemento(input);
        input.clear();
        input.sendKeys(textoBusqueda);

        List<WebElement> opciones = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(".custom-scroll .cursor-pointer")
        ));

        for (WebElement opcion : opciones) {
            if (opcion.getText().contains(textoBusqueda)) {
                scrollAlElemento(opcion);
                opcion.click();
                pausaVisual();
                return;
            }
        }

        if (!opciones.isEmpty()) {
            opciones.get(0).click();
        }

        pausaVisual();
    }

    private void escribirFecha(String fecha) {
        List<WebElement> inputsFecha = driver.findElements(By.cssSelector("app-date-picker input"));

        if (!inputsFecha.isEmpty()) {
            WebElement inputFecha = inputsFecha.get(0);
            scrollAlElemento(inputFecha);
            inputFecha.clear();
            inputFecha.sendKeys(fecha);
            pausaVisual();
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

        pausaVisual();
    }

    private void escribirPrimerDisponible(String texto, By... locators) {
        for (By locator : locators) {
            try {
                escribir(locator, texto);
                return;
            } catch (Exception e) {
                // intenta el siguiente selector
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
                // intenta el siguiente selector
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

        return texto.toLowerCase().replaceAll("\\s+", " ").trim();
    }

    private void scrollAlElemento(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
    }

    private void pausaVisual() {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}