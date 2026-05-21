package com.example.biskit.e2e;

import java.text.Normalizer;
import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.biskit.service.Tratamientos.DrogasService;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("default")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class Caso2TratamientoDashboardE2ETest {

    private final String BASE_URL = "http://localhost:4200";

    private final String VET_USUARIO = "ana.gonzalez@biskit.com";
    private final String VET_PASSWORD = "101";

    private final String ADMIN_USUARIO = "santiago@biskit.com";
    private final String ADMIN_PASSWORD = "40";

    private final String NOMBRE_MASCOTA = "Oso";

    // Se escribe solo una parte para que salga el desplegable
    private final String BUSQUEDA_DROGA = "Cepri";
    private final String NOMBRE_DROGA = "Cepritect";

    @Autowired
    private DrogasService drogasService;

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
    public void SystemTest_caso2_tratamientoDashboard() {

        // 1. Entrar como administrador y revisar ventas y ganancias iniciales
        driver.get(BASE_URL + "/");

        esperarTexto("Iniciar Sesión");
        clickPorTexto("Iniciar Sesión");

        wait.until(ExpectedConditions.urlContains("/login"));

        login(ADMIN_USUARIO, ADMIN_PASSWORD);

        wait.until(ExpectedConditions.urlContains("/admin/"));
        esperarTexto("GANANCIAS TOTALES");

        long ventasIniciales = obtenerVentasTotales();
        long gananciasIniciales = obtenerGananciasTotales();

        Assertions.assertThat(ventasIniciales).isGreaterThanOrEqualTo(0);
        Assertions.assertThat(gananciasIniciales).isGreaterThanOrEqualTo(0);

        clickCerrarSesion();

        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/"));
        esperarTexto("Iniciar Sesión");

        // 2. Entrar como veterinario
        clickPorTexto("Iniciar Sesión");

        wait.until(ExpectedConditions.urlContains("/login"));

        login(VET_USUARIO, VET_PASSWORD);

        wait.until(ExpectedConditions.urlContains("/vet/"));
        esperarTexto("Hola de nuevo");

        // 3. Entrar a Mascotas de Biskit
        click(By.xpath("//*[contains(normalize-space(.), 'Mascotas de Biskit')]/ancestor::article[1]"));

        esperarTexto("Mascotas Registradas");

        // 4. Buscar la mascota
        escribirPrimerDisponible(
                NOMBRE_MASCOTA,
                By.cssSelector("input[placeholder='Buscar mascota...']"),
                By.xpath("//input[contains(@placeholder, 'Buscar mascota')]")
        );

        esperarTexto(NOMBRE_MASCOTA);

        // 5. Entrar al detalle de la mascota
        WebElement cardMascota = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//article[.//h3[contains(normalize-space(.), '" + NOMBRE_MASCOTA + "')]]")
        ));

        scrollAlElemento(cardMascota);

        List<WebElement> imagenes = cardMascota.findElements(By.tagName("img"));

        if (!imagenes.isEmpty()) {
            clickElemento(imagenes.get(0));
        } else {
            clickElemento(cardMascota);
        }

        esperarTexto("Tratamientos de " + NOMBRE_MASCOTA);
        esperarTexto("Agregar Tratamiento");

        // 6. Agregar tratamiento
        clickBotonPorTexto("Agregar Tratamiento");

        esperarTexto("Nuevo Tratamiento");
        esperarTexto("Registrar Tratamiento");

        // 7. Buscar la droga y obtener su precio de venta desde los datos del backend
        seleccionarDrogaDisponible(BUSQUEDA_DROGA, NOMBRE_DROGA);

        /*
         * El dropdown solo muestra el nombre de la droga y las unidades disponibles.
         * Por eso el precio de venta se toma desde los datos de prueba del backend.
         */
        long precioVentaDroga = obtenerPrecioVentaDrogaDesdeBackend(NOMBRE_DROGA);

        Assertions.assertThat(precioVentaDroga).isGreaterThan(0);

        // 8. Registrar tratamiento
        clickBotonPorTexto("Registrar Tratamiento");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/pets/"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Tratamientos de " + NOMBRE_MASCOTA)
        ));

        esperarTexto("Tratamientos de " + NOMBRE_MASCOTA);
        esperarTexto(NOMBRE_DROGA);

        Assertions.assertThat(textoPagina().contains(NOMBRE_DROGA.toLowerCase())).isTrue();

        // 9. Cerrar sesión del veterinario
        clickCerrarSesion();

        wait.until(ExpectedConditions.urlToBe(BASE_URL + "/"));
        esperarTexto("Iniciar Sesión");

        // 10. Entrar otra vez como administrador
        clickPorTexto("Iniciar Sesión");

        wait.until(ExpectedConditions.urlContains("/login"));

        login(ADMIN_USUARIO, ADMIN_PASSWORD);

        wait.until(ExpectedConditions.urlContains("/admin/"));
        esperarTexto("GANANCIAS TOTALES");

        long ventasFinales = obtenerVentasTotales();
        long gananciasFinales = obtenerGananciasTotales();

        long ventasEsperadas = ventasIniciales + 1;
        long gananciasEsperadas = gananciasIniciales + precioVentaDroga;

        // 11. Validar que ventas y ganancias sean exactamente las esperadas
        Assertions.assertThat(ventasFinales).isEqualTo(ventasEsperadas);
        Assertions.assertThat(gananciasFinales).isEqualTo(gananciasEsperadas);
    }

    private void login(String usuario, String contrasena) {
        escribirPrimerDisponible(
                usuario,
                By.id("usuario"),
                By.name("usuario")
        );

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

    private void seleccionarDrogaDisponible(String textoBusqueda, String nombreDroga) {
        WebElement inputDroga = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[contains(@placeholder, 'Buscar droga') or @name='drugSearch0']")
        ));

        scrollAlElemento(inputDroga);

        inputDroga.click();
        inputDroga.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        inputDroga.sendKeys(Keys.DELETE);
        inputDroga.sendKeys(textoBusqueda);

        esperarTexto(nombreDroga);

        WebElement opcionDroga = buscarOpcionDroga(nombreDroga);

        clickElemento(opcionDroga);

        wait.until(driver -> {
            String valor = inputDroga.getAttribute("value");

            return valor != null
                    && normalizarTexto(valor).contains(normalizarTexto(nombreDroga));
        });
    }

    private WebElement buscarOpcionDroga(String nombreDroga) {
        By opcionesDroga = By.xpath(
                "//*[self::div or self::li or self::button]" +
                        "[contains(normalize-space(.), '" + nombreDroga + "') " +
                        "and contains(normalize-space(.), 'Unidades disponibles') " +
                        "and not(contains(normalize-space(.), 'Sin unidades'))]"
        );

        List<WebElement> opciones = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(opcionesDroga));

        WebElement mejorOpcion = null;
        int menorArea = Integer.MAX_VALUE;

        for (WebElement opcion : opciones) {
            if (!opcion.isDisplayed()) {
                continue;
            }

            String texto = opcion.getText();

            if (!texto.contains(nombreDroga)
                    || !texto.contains("Unidades disponibles")
                    || texto.contains("Sin unidades")) {
                continue;
            }

            Rectangle rect = opcion.getRect();
            int area = rect.getWidth() * rect.getHeight();

            // Evita seleccionar contenedores grandes como toda la página
            if (rect.getHeight() > 15 && rect.getHeight() < 180 && area < menorArea) {
                menorArea = area;
                mejorOpcion = opcion;
            }
        }

        if (mejorOpcion != null) {
            return mejorOpcion;
        }

        return opciones.get(opciones.size() - 1);
    }

    private long obtenerPrecioVentaDrogaDesdeBackend(String nombreDroga) {
        return drogasService.getDrogas()
                .stream()
                .filter(droga -> droga.getNombre().equalsIgnoreCase(nombreDroga))
                .findFirst()
                .map(droga -> Math.round(droga.getPrecioVenta()))
                .orElseThrow(() -> new RuntimeException("No se encontró la droga: " + nombreDroga));
    }

    private long obtenerVentasTotales() {
        return obtenerNumeroDashboard(
                "VENTAS TOTALES",
                "MEDICAMENTOS SUMINISTRADOS",
                "MEDICAMENTOS VENDIDOS",
                "DROGAS SUMINISTRADAS",
                "TRATAMIENTOS TOTALES"
        );
    }

    private long obtenerGananciasTotales() {
        return obtenerNumeroDashboard(
                "GANANCIAS TOTALES"
        );
    }

    private long obtenerNumeroDashboard(String... posiblesTitulos) {
        String textoDashboard = normalizarTexto(driver.findElement(By.tagName("body")).getText());

        for (String titulo : posiblesTitulos) {
            String tituloNormalizado = normalizarTexto(titulo);

            Pattern patron = Pattern.compile(
                    Pattern.quote(tituloNormalizado) +
                            "[^0-9$]{0,80}" +
                            "(COP\\s*)?" +
                            "\\$?\\s*" +
                            "([0-9][0-9.,]*)",
                    Pattern.CASE_INSENSITIVE
            );

            Matcher matcher = patron.matcher(textoDashboard);

            if (matcher.find()) {
                return convertirNumero(matcher.group(2));
            }
        }

        throw new RuntimeException(
                "No se encontró en el dashboard ninguno de estos valores: " + String.join(", ", posiblesTitulos)
        );
    }

    private void click(By locator) {
        WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
        clickElemento(elemento);
    }

    private void clickElemento(WebElement elemento) {
        scrollAlElemento(elemento);

        try {
            elemento.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
        } catch (Exception e) {
            try {
                new Actions(driver)
                        .moveToElement(elemento)
                        .pause(Duration.ofMillis(200))
                        .click()
                        .perform();
            } catch (Exception e2) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
            }
        }
    }

    private void escribir(By locator, String texto) {
        WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollAlElemento(elemento);

        try {
            elemento.click();
            elemento.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            elemento.sendKeys(Keys.DELETE);
            elemento.sendKeys(texto);
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

    private void esperarTexto(String texto) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), texto));
    }

    private void clickPorTexto(String texto) {
        click(By.xpath("//*[self::a or self::button or self::article][contains(normalize-space(.), '" + texto + "')]"));
    }

    private void clickBotonPorTexto(String texto) {
        click(By.xpath("//button[contains(normalize-space(.), '" + texto + "')]"));
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
            List<WebElement> botones = driver.findElements(By.xpath("//button[.//*[name()='svg'] and ancestor::header]"));
            WebElement ultimoBoton = botones.get(botones.size() - 1);

            clickElemento(ultimoBoton);
            return;
        }

        clickPorTexto("Cerrar sesión");
    }

    private long convertirNumero(String texto) {
        String limpio = texto.replaceAll("[^0-9]", "");

        if (limpio.isBlank()) {
            return 0;
        }

        return Long.parseLong(limpio);
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

        String textoSinTildes = Normalizer
                .normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return textoSinTildes
                .toLowerCase()
                .replaceAll("\\s+", " ")
                .trim();
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