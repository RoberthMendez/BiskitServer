package com.example.biskit.e2e;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

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

        // 1. Entrar como administrador y revisar ganancias iniciales
        driver.get(BASE_URL + "/");

        esperarTexto("Iniciar Sesión");
        clickPorTexto("Iniciar Sesión");

        wait.until(ExpectedConditions.urlContains("/login"));

        login(ADMIN_USUARIO, ADMIN_PASSWORD);

        wait.until(ExpectedConditions.urlContains("/admin/"));
        esperarTexto("GANANCIAS TOTALES");

        long gananciasIniciales = obtenerGananciasTotales();

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

        // 5. Entrar al detalle de la mascota dando click en la foto
        WebElement cardMascota = driver.findElement(By.xpath(
                "//article[.//h3[contains(normalize-space(.), '" + NOMBRE_MASCOTA + "')]]"
        ));

        scrollAlElemento(cardMascota);

        List<WebElement> imagenes = cardMascota.findElements(By.tagName("img"));

        if (!imagenes.isEmpty()) {
            try {
                imagenes.get(0).click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", imagenes.get(0));
            }
        } else {
            cardMascota.click();
        }

        pausaVisual();

        esperarTexto("Tratamientos de " + NOMBRE_MASCOTA);
        esperarTexto("Agregar Tratamiento");

        // 6. Agregar tratamiento
        clickBotonPorTexto("Agregar Tratamiento");

        esperarTexto("Nuevo Tratamiento");
        esperarTexto("Registrar Tratamiento");

        // 7. Buscar parte del nombre y seleccionar Cepritect
        seleccionarDrogaDisponible(BUSQUEDA_DROGA, NOMBRE_DROGA);

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

        long gananciasFinales = obtenerGananciasTotales();

        Assertions.assertThat(gananciasFinales).isGreaterThan(gananciasIniciales);
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
        inputDroga.clear();
        inputDroga.sendKeys(textoBusqueda);

        pausaVisual();

        // Esperar a que aparezca la droga en el desplegable
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), nombreDroga));

        WebElement opcionDroga = buscarOpcionDroga(nombreDroga);

        scrollAlElemento(opcionDroga);
        pausaVisual();

        try {
            opcionDroga.click();
        } catch (Exception e1) {
            try {
                new Actions(driver)
                        .moveToElement(opcionDroga)
                        .pause(Duration.ofMillis(300))
                        .click()
                        .perform();
            } catch (Exception e2) {
                try {
                    ((JavascriptExecutor) driver).executeScript(
                            "const el = arguments[0];" +
                                    "const rect = el.getBoundingClientRect();" +
                                    "const x = rect.left + rect.width / 2;" +
                                    "const y = rect.top + rect.height / 2;" +
                                    "document.elementFromPoint(x, y).click();",
                            opcionDroga
                    );
                } catch (Exception e3) {
                    inputDroga.sendKeys(Keys.ARROW_DOWN);
                    inputDroga.sendKeys(Keys.ENTER);
                }
            }
        }

        pausaVisual();

        // Validar que realmente quedó seleccionada
        wait.until(driver -> {
            String valor = inputDroga.getAttribute("value");

            return valor != null
                    && valor.toLowerCase().contains(nombreDroga.toLowerCase());
        });

        pausaVisual();
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

            // Evita escoger contenedores gigantes como toda la página o todo el formulario
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

    private void escribir(By locator, String texto) {
        WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollAlElemento(elemento);

        try {
            elemento.click();
            elemento.clear();
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

        pausaVisual();
    }

    private void esperarTexto(String texto) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), texto));
        pausaVisual();
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

            scrollAlElemento(ultimoBoton);

            try {
                ultimoBoton.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ultimoBoton);
            }

            pausaVisual();
            return;
        }

        clickPorTexto("Cerrar sesión");
    }

    private long obtenerGananciasTotales() {
        String texto = driver.findElement(By.tagName("body")).getText();

        Pattern patron = Pattern.compile(
                "GANANCIAS\\s+TOTALES\\s*\\$\\s*([0-9.,]+)",
                Pattern.CASE_INSENSITIVE
        );

        Matcher matcher = patron.matcher(texto);

        if (matcher.find()) {
            return convertirNumero(matcher.group(1));
        }

        throw new RuntimeException("No se encontró el valor de GANANCIAS TOTALES");
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