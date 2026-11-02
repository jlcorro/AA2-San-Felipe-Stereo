
package co.edu.aa2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponsiveTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlAplicacion = System.getProperty(
        "baseUrl",
        "http://localhost:5173/"
    );

    // Configurar Brave.
    @BeforeEach
    public void configurarNavegador() {

        Path rutaBrave = localizarBrave();

        ChromeOptions options = new ChromeOptions();
        options.setBinary(rutaBrave.toString());

        driver = new ChromeDriver(options);

        wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(10)
        );

        System.out.println(
            "Prueba responsive iniciada en Brave."
        );
    }

    // Localizar Brave en Windows.
    private Path localizarBrave() {

        List<Path> posiblesRutas = new ArrayList<>();

        String programFiles = System.getenv("ProgramFiles");
        String programFilesX86 = System.getenv("ProgramFiles(x86)");
        String localAppData = System.getenv("LOCALAPPDATA");

        if (programFiles != null) {
            posiblesRutas.add(
                Path.of(
                    programFiles,
                    "BraveSoftware",
                    "Brave-Browser",
                    "Application",
                    "brave.exe"
                )
            );
        }

        if (programFilesX86 != null) {
            posiblesRutas.add(
                Path.of(
                    programFilesX86,
                    "BraveSoftware",
                    "Brave-Browser",
                    "Application",
                    "brave.exe"
                )
            );
        }

        if (localAppData != null) {
            posiblesRutas.add(
                Path.of(
                    localAppData,
                    "BraveSoftware",
                    "Brave-Browser",
                    "Application",
                    "brave.exe"
                )
            );
        }

        for (Path ruta : posiblesRutas) {
            if (Files.isRegularFile(ruta)) {
                return ruta;
            }
        }

        throw new IllegalStateException(
            "No se encontró el navegador Brave."
        );
    }

    // Guardar capturas.
    private void guardarCaptura(String nombre)
            throws IOException {

        File captura = ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.FILE);

        Path carpeta = Path.of(
            "..",
            "evidencias-aa2",
            "responsive"
        ).toAbsolutePath().normalize();

        Files.createDirectories(carpeta);

        Path destino = carpeta.resolve(nombre);

        Files.copy(
            captura.toPath(),
            destino,
            StandardCopyOption.REPLACE_EXISTING
        );

        System.out.println(
            "Evidencia guardada: " + destino
        );
    }

    // CP16: comprobar navegación en escritorio.
    @Test
    public void verificarNavegacionEscritorio()
            throws IOException {

        driver.manage().window().setSize(
            new Dimension(1365, 900)
        );

        driver.get(urlAplicacion);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("titulo-inicio")
            )
        );

        WebElement navegacion = driver.findElement(
            By.cssSelector("nav[aria-label='Principal']")
        );

        WebElement botonMovil = driver.findElement(
            By.cssSelector("button[aria-controls='menu-movil']")
        );

        assertTrue(
            navegacion.isDisplayed(),
            "La navegación horizontal debe estar visible en escritorio."
        );

        assertFalse(
            botonMovil.isDisplayed(),
            "El botón del menú móvil no debe mostrarse en escritorio."
        );

        guardarCaptura(
            "16_navegacion_escritorio.png"
        );

        System.out.println(
            "CP16: navegación de escritorio aprobada."
        );
    }

    // CP17: comprobar menú móvil y navegación.
    @Test
    public void verificarMenuMovil()
            throws IOException {

        driver.manage().window().setSize(
            new Dimension(390, 844)
        );

        driver.get(urlAplicacion);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("titulo-inicio")
            )
        );

        WebElement botonAbrir = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector(
                    "button[aria-label='Abrir menú']"
                )
            )
        );

        assertTrue(
            botonAbrir.isDisplayed(),
            "El botón para abrir el menú debe estar visible."
        );

        botonAbrir.click();

        WebElement menu = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("menu-movil")
            )
        );

        WebElement botonCerrar = driver.findElement(
            By.cssSelector(
                "button[aria-label='Cerrar menú']"
            )
        );

        assertTrue(menu.isDisplayed());

        assertTrue(
            "true".equals(
                botonCerrar.getAttribute("aria-expanded")
            ),
            "El menú debe quedar marcado como abierto."
        );

        guardarCaptura(
            "17_menu_movil_abierto.png"
        );

        // Seleccionar programación desde el menú móvil.
        WebElement enlaceProgramacion = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector(
                    "#menu-movil a[href='#programacion']"
                )
            )
        );

        enlaceProgramacion.click();

        wait.until(
            ExpectedConditions.urlContains("#programacion")
        );

        wait.until(
            ExpectedConditions.invisibilityOfElementLocated(
                By.id("menu-movil")
            )
        );

        WebElement botonAbrirNuevamente = driver.findElement(
            By.cssSelector(
                "button[aria-label='Abrir menú']"
            )
        );

        assertTrue(
            "false".equals(
                botonAbrirNuevamente.getAttribute("aria-expanded")
            ),
            "El menú debe cerrarse después de seleccionar una sección."
        );

        assertTrue(
            driver.getCurrentUrl().contains("#programacion"),
            "La navegación debe llegar a programación."
        );

        guardarCaptura(
            "18_menu_movil_cerrado.png"
        );

        System.out.println(
            "CP17: menú móvil y navegación aprobados."
        );
    }

    // Cerrar Brave.
    @AfterEach
    public void cerrarNavegador() {

        if (driver != null) {
            driver.quit();

            System.out.println(
                "Navegador Brave cerrado."
            );
        }
    }
}
