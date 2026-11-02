
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormularioContactoTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String urlAplicacion = System.getProperty(
        "baseUrl",
        "http://localhost:5173/"
    );

    // Preparar Brave y abrir la sección de contacto.
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

        driver.get(urlAplicacion + "#contacto");

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("titulo-con")
            )
        );

        System.out.println(
            "Formulario de contacto abierto correctamente."
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

    // Completar los campos con datos válidos de prueba.
    private void completarFormularioValido() {

        driver.findElement(By.id("f-name"))
            .sendKeys("Jairo Corro");

        driver.findElement(By.id("f-contact"))
            .sendKeys("jairo@example.com");

        driver.findElement(By.id("f-subject"))
            .sendKeys("Consulta general");

        driver.findElement(By.id("f-message"))
            .sendKeys(
                "Este es un mensaje de prueba para validar el formulario."
            );
    }

    // Guardar captura de pantalla.
    private void guardarCaptura(String nombre)
            throws IOException {

        File captura = ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.FILE);

        Path carpeta = Path.of(
            "..",
            "evidencias-aa2",
            "contacto"
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

    // Comprobar que un campo presenta el error esperado.
    private void verificarError(
            String campo,
            String mensajeEsperado) {

        WebElement mensaje = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("e-" + campo)
            )
        );

        assertEquals(
            mensajeEsperado,
            mensaje.getText()
        );

        assertEquals(
            "true",
            driver.findElement(By.id("f-" + campo))
                .getAttribute("aria-invalid")
        );
    }

    // CP09: comprobar que todos los campos sean obligatorios.
    @Test
    public void verificarFormularioVacio()
            throws IOException {

        driver.findElement(
            By.cssSelector("#contacto button[type='submit']")
        ).click();

        verificarError(
            "name",
            "Escribe tu nombre."
        );

        verificarError(
            "contact",
            "Escribe un correo o un teléfono válido."
        );

        verificarError(
            "subject",
            "Escribe un asunto."
        );

        verificarError(
            "message",
            "El mensaje debe tener al menos 10 caracteres."
        );

        guardarCaptura(
            "09_formulario_campos_vacios.png"
        );

        System.out.println(
            "CP09: validación de campos vacíos aprobada."
        );
    }

    // CP10: comprobar longitud mínima del nombre.
    @Test
    public void verificarNombreInvalido()
            throws IOException {

        completarFormularioValido();

        WebElement nombre = driver.findElement(
            By.id("f-name")
        );

        nombre.clear();
        nombre.sendKeys("A");

        driver.findElement(
            By.cssSelector("#contacto button[type='submit']")
        ).click();

        verificarError(
            "name",
            "Escribe tu nombre."
        );

        guardarCaptura(
            "10_nombre_invalido.png"
        );

        System.out.println(
            "CP10: validación del nombre aprobada."
        );
    }

    // CP11: comprobar formato del correo o teléfono.
    @Test
    public void verificarContactoInvalido()
            throws IOException {

        completarFormularioValido();

        WebElement contacto = driver.findElement(
            By.id("f-contact")
        );

        contacto.clear();
        contacto.sendKeys("dato-invalido");

        driver.findElement(
            By.cssSelector("#contacto button[type='submit']")
        ).click();

        verificarError(
            "contact",
            "Escribe un correo o un teléfono válido."
        );

        guardarCaptura(
            "11_contacto_invalido.png"
        );

        System.out.println(
            "CP11: validación del contacto aprobada."
        );
    }

    // CP12: comprobar longitud mínima del asunto.
    @Test
    public void verificarAsuntoInvalido()
            throws IOException {

        completarFormularioValido();

        WebElement asunto = driver.findElement(
            By.id("f-subject")
        );

        asunto.clear();
        asunto.sendKeys("ab");

        driver.findElement(
            By.cssSelector("#contacto button[type='submit']")
        ).click();

        verificarError(
            "subject",
            "Escribe un asunto."
        );

        guardarCaptura(
            "12_asunto_invalido.png"
        );

        System.out.println(
            "CP12: validación del asunto aprobada."
        );
    }

    // CP13: comprobar longitud mínima del mensaje.
    @Test
    public void verificarMensajeInvalido()
            throws IOException {

        completarFormularioValido();

        WebElement mensaje = driver.findElement(
            By.id("f-message")
        );

        mensaje.clear();
        mensaje.sendKeys("corto");

        driver.findElement(
            By.cssSelector("#contacto button[type='submit']")
        ).click();

        verificarError(
            "message",
            "El mensaje debe tener al menos 10 caracteres."
        );

        guardarCaptura(
            "13_mensaje_invalido.png"
        );

        System.out.println(
            "CP13: validación del mensaje aprobada."
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
