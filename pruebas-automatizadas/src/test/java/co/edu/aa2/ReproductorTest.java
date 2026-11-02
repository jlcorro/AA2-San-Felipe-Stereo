
package co.edu.aa2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReproductorTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlAplicacion = System.getProperty(
        "baseUrl",
        "http://localhost:5173/"
    );

    // Configuración del navegador Brave.
    @BeforeEach
    public void configurarNavegador() {

        Path rutaBrave = localizarBrave();

        ChromeOptions options = new ChromeOptions();
        options.setBinary(rutaBrave.toString());

        driver = new ChromeDriver(options);

        driver.manage().window().setSize(
            new Dimension(1365, 900)
        );

        wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(10)
        );

        driver.get(urlAplicacion + "#en-vivo");

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("titulo-vivo")
            )
        );

        System.out.println(
            "Reproductor de San Felipe Stereo abierto."
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
            "No se encontró Brave en Windows."
        );
    }

    // Guardar capturas de pantalla.
    private void guardarCaptura(String nombre)
            throws IOException {

        File captura = ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.FILE);

        Path carpeta = Path.of(
            "..",
            "evidencias-aa2",
            "reproductor"
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

    // CP14: comprobar los controles y el estado inicial.
    @Test
    public void verificarControlesDelReproductor()
            throws IOException {

        WebElement titulo = driver.findElement(
            By.id("titulo-vivo")
        );

        assertEquals(
            "Escucha San Felipe Stereo en vivo",
            titulo.getText()
        );

        WebElement boton = driver.findElement(
            By.cssSelector(
                "#en-vivo button[aria-label='Reproducir la emisora']"
            )
        );

        assertTrue(
            boton.isDisplayed(),
            "El botón de reproducción debe estar visible."
        );

        WebElement estado = driver.findElement(
            By.cssSelector("#en-vivo [role='status']")
        );

        assertFalse(
            estado.getText().isBlank(),
            "El reproductor debe mostrar su estado."
        );

        WebElement volumen = driver.findElement(
            By.id("volumen")
        );

        assertTrue(volumen.isDisplayed());

        assertEquals("0", volumen.getAttribute("min"));
        assertEquals("1", volumen.getAttribute("max"));
        assertEquals("0.05", volumen.getAttribute("step"));

        guardarCaptura(
            "14_controles_reproductor.png"
        );

        System.out.println(
            "CP14: controles del reproductor aprobados."
        );
    }

    // CP15: comprobar el control de volumen.
    @Test
    public void verificarControlDeVolumen()
            throws IOException {

        WebElement volumen = driver.findElement(
            By.id("volumen")
        );

        String valorInicial =
            volumen.getDomProperty("value");

        assertEquals(
            "0.8",
            valorInicial,
            "El volumen inicial debe ser 0.8."
        );

        // Aumentar el volumen sin iniciar la transmisión.
        volumen.sendKeys(Keys.ARROW_RIGHT);

        wait.until(d -> {
            String valorActual = volumen.getDomProperty("value");

            return Double.parseDouble(valorActual)
                > Double.parseDouble(valorInicial);
        });

        String valorFinal =
            volumen.getDomProperty("value");

        assertTrue(
            Double.parseDouble(valorFinal) <= 1.0,
            "El volumen no debe superar el máximo permitido."
        );

        assertTrue(
            Double.parseDouble(valorFinal) > 0.8,
            "El volumen debe aumentar al utilizar el control."
        );

        guardarCaptura(
            "15_control_volumen.png"
        );

        System.out.println(
            "CP15: control de volumen aprobado."
        );
    }

    // Cerrar Brave después de cada prueba.
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
