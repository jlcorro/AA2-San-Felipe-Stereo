
package co.edu.aa2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocutoresTest {

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

        driver.manage().window().setSize(
            new Dimension(1365, 900)
        );

        wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(10)
        );

        driver.get(urlAplicacion + "#locutores");

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#locutores h2")
            )
        );

        System.out.println(
            "Sección de locutores abierta correctamente."
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
                Path.of(programFiles,
                    "BraveSoftware",
                    "Brave-Browser",
                    "Application",
                    "brave.exe")
            );
        }

        if (programFilesX86 != null) {
            posiblesRutas.add(
                Path.of(programFilesX86,
                    "BraveSoftware",
                    "Brave-Browser",
                    "Application",
                    "brave.exe")
            );
        }

        if (localAppData != null) {
            posiblesRutas.add(
                Path.of(localAppData,
                    "BraveSoftware",
                    "Brave-Browser",
                    "Application",
                    "brave.exe")
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

    // Guardar evidencia de cada prueba.
    private void guardarCaptura(String nombre)
            throws IOException {

        File captura = ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.FILE);

        Path carpeta = Path.of(
            "..",
            "evidencias-aa2",
            "locutores"
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

    // CP07: verificar los perfiles y nombres de los locutores.
    @Test
    public void verificarPerfilesDeLocutores()
            throws IOException {

        WebElement titulo = driver.findElement(
            By.cssSelector("#locutores h2")
        );

        assertEquals(
            "Nuestros locutores",
            titulo.getText()
        );

        List<WebElement> tarjetas = driver.findElements(
            By.cssSelector("#locutores article")
        );

        assertEquals(
            5,
            tarjetas.size(),
            "La sección debe mostrar cinco perfiles."
        );

        String contenido = driver.findElement(
            By.id("locutores")
        ).getText();

        assertTrue(
            contenido.contains("Luis Enrique Corro Sarza"),
            "No aparece Luis Enrique Corro Sarza."
        );

        assertTrue(
            contenido.contains("Arnulfo Herrera Mosquera"),
            "No aparece Arnulfo Herrera Mosquera."
        );

        assertTrue(
            contenido.contains("Everaldo Giménez Sierra"),
            "No aparece Everaldo Giménez Sierra."
        );

        assertTrue(
            contenido.contains("Eduin Conde Angulo"),
            "No aparece Eduin Conde Angulo."
        );

        assertTrue(
            contenido.contains("Edilberto Corro Sarza"),
            "No aparece Edilberto Corro Sarza."
        );

        guardarCaptura(
            "07_perfiles_locutores.png"
        );

        System.out.println(
            "CP07: perfiles y nombres de locutores aprobados."
        );
    }

    // CP08: verificar la carga de las fotografías.
    @Test
    public void verificarFotografiasDeLocutores()
            throws IOException {

        List<WebElement> fotografias = driver.findElements(
            By.cssSelector("#locutores article img")
        );

        assertEquals(
            5,
            fotografias.size(),
            "Deben existir cinco fotografías."
        );

        JavascriptExecutor js =
            (JavascriptExecutor) driver;

        for (WebElement fotografia : fotografias) {

            js.executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                fotografia
            );

            wait.until(d -> Boolean.TRUE.equals(
                js.executeScript(
                    "return arguments[0].complete && " +
                    "arguments[0].naturalWidth > 0;",
                    fotografia
                )
            ));

            String textoAlternativo =
                fotografia.getAttribute("alt");

            String origen =
                fotografia.getAttribute("src");

            assertTrue(
                textoAlternativo.startsWith("Fotografía de "),
                "La fotografía debe tener texto alternativo."
            );

            assertTrue(
                origen.contains("/images/locutores/"),
                "La fotografía debe provenir de la carpeta de locutores."
            );
        }

        guardarCaptura(
            "08_fotografias_locutores.png"
        );

        System.out.println(
            "CP08: fotografías de locutores aprobadas."
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
