
package co.edu.aa2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramacionTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlAplicacion = System.getProperty(
        "baseUrl",
        "http://localhost:5173/"
    );

    // Preparar Brave antes de cada prueba.
    @BeforeEach
    public void configurarNavegador() {

        Path rutaBrave = localizarBrave();

        ChromeOptions options = new ChromeOptions();
        options.setBinary(rutaBrave.toString());

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(10)
        );

        driver.get(urlAplicacion);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("titulo-prog")
            )
        );

        System.out.println(
            "San Felipe Stereo abierta en Brave."
        );
    }

    // Localizar automáticamente Brave en Windows.
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

    // Guardar evidencia de cada escenario.
    private void guardarCaptura(String nombre)
            throws IOException {

        File captura = ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.FILE);

        Path carpeta = Path.of(
            "..",
            "evidencias-aa2",
            "programacion"
        ).toAbsolutePath().normalize();

        Files.createDirectories(carpeta);

        Path destino = carpeta.resolve(nombre);

        Files.copy(
            captura.toPath(),
            destino,
            StandardCopyOption.REPLACE_EXISTING
        );

        System.out.println(
            "Captura guardada: " + destino
        );
    }

    // CP04: verificar programación de lunes a viernes.
    @Test
    public void verificarProgramacionEntreSemana()
            throws IOException {

        WebElement pestana = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("tab-weekdays")
            )
        );

        pestana.click();

        wait.until(
            ExpectedConditions.attributeToBe(
                By.id("tab-weekdays"),
                "aria-selected",
                "true"
            )
        );

        WebElement panel = driver.findElement(
            By.id("panel-prog")
        );

        String contenido = panel.getText();

        assertTrue(
            contenido.contains("Nuestro Folklor"),
            "No se encontró el programa Nuestro Folklor."
        );

        assertTrue(
            contenido.contains("Notideportes"),
            "No se encontró el programa Notideportes."
        );

        guardarCaptura(
            "04_programacion_lunes_viernes.png"
        );

        System.out.println(
            "CP04: programación de lunes a viernes aprobada."
        );
    }

    // CP05: verificar programación del sábado.
    @Test
    public void verificarProgramacionSabado()
            throws IOException {

        WebElement pestana = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("tab-saturday")
            )
        );

        pestana.click();

        wait.until(
            ExpectedConditions.attributeToBe(
                By.id("tab-saturday"),
                "aria-selected",
                "true"
            )
        );

        WebElement panel = driver.findElement(
            By.id("panel-prog")
        );

        String contenido = panel.getText();

        assertTrue(
            contenido.contains(
                "El Artista de la Semana de la Región"
            ),
            "No se encontró el programa del artista de la semana."
        );

        assertTrue(
            contenido.contains("El Show Afrocaribe"),
            "No se encontró el programa El Show Afrocaribe."
        );

        guardarCaptura(
            "05_programacion_sabado.png"
        );

        System.out.println(
            "CP05: programación del sábado aprobada."
        );
    }

    // CP06: verificar programación del domingo.
    @Test
    public void verificarProgramacionDomingo()
            throws IOException {

        WebElement pestana = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("tab-sunday")
            )
        );

        pestana.click();

        wait.until(
            ExpectedConditions.attributeToBe(
                By.id("tab-sunday"),
                "aria-selected",
                "true"
            )
        );

        WebElement panel = driver.findElement(
            By.id("panel-prog")
        );

        String contenido = panel.getText();

        assertTrue(
            contenido.contains("Juglares de la Región"),
            "No se encontró el programa Juglares de la Región."
        );

        assertTrue(
            contenido.contains("Vallenatos del Ayer"),
            "No se encontró el programa Vallenatos del Ayer."
        );

        guardarCaptura(
            "06_programacion_domingo.png"
        );

        System.out.println(
            "CP06: programación del domingo aprobada."
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
