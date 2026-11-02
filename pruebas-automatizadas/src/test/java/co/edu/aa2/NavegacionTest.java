
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

import org.openqa.selenium.Dimension;

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

public class NavegacionTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlAplicacion = System.getProperty(
        "baseUrl",
        "http://localhost:5173/"
    );

    // Configuración inicial del navegador Brave.
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

        driver.get(urlAplicacion);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("titulo-inicio")
            )
        );

        System.out.println("San Felipe Stereo abierta en Brave.");
    }

    // Localizar automáticamente el navegador Brave.
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
                System.out.println(
                    "Brave localizado en: " + ruta
                );

                return ruta;
            }
        }

        throw new IllegalStateException(
            "No se encontró Brave. Verifica su ubicación."
        );
    }

    // Guardar capturas de los escenarios ejecutados.
    private void guardarCaptura(String nombre)
            throws IOException {

        File captura = ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.FILE);

        Path carpeta = Path.of(
            "..",
            "evidencias-aa2",
            "navegacion"
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

    // CP01: verificar la página principal.
    @Test
    public void verificarPaginaPrincipal() throws IOException {

        WebElement titulo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("titulo-inicio")
            )
        );

        assertEquals(
            "San Felipe Stereo",
            titulo.getText()
        );

        assertTrue(
            driver.getCurrentUrl().startsWith(urlAplicacion),
            "La página principal no se abrió correctamente."
        );

        guardarCaptura("01_pagina_principal.png");

        System.out.println(
            "CP01: página principal aprobada."
        );
    }

    // CP02: verificar navegación hacia programación.
    @Test
    public void verificarNavegacionProgramacion()
            throws IOException {

        WebElement enlace = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector(
                    "nav[aria-label='Principal'] a[href='#programacion']"
                )
            )
        );

        enlace.click();

        wait.until(
            ExpectedConditions.urlContains("#programacion")
        );

        WebElement titulo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("titulo-prog")
            )
        );

        assertEquals(
            "Programación semanal",
            titulo.getText()
        );

        assertTrue(
            driver.getCurrentUrl().contains("#programacion")
        );

        guardarCaptura(
            "02_navegacion_programacion.png"
        );

        System.out.println(
            "CP02: navegación a programación aprobada."
        );
    }

    // CP03: verificar navegación hacia contacto.
    @Test
    public void verificarNavegacionContacto()
            throws IOException {

        WebElement enlace = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.cssSelector(
                    "nav[aria-label='Principal'] a[href='#contacto']"
                )
            )
        );

        enlace.click();

        wait.until(
            ExpectedConditions.urlContains("#contacto")
        );

        WebElement titulo = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("titulo-con")
            )
        );

        assertEquals(
            "Contacto",
            titulo.getText()
        );

        assertTrue(
            driver.getCurrentUrl().contains("#contacto")
        );

        guardarCaptura(
            "03_navegacion_contacto.png"
        );

        System.out.println(
            "CP03: navegación a contacto aprobada."
        );
    }

    // Cerrar Brave después de cada ejecución.
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
