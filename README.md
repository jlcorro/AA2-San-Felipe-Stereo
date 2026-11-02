# AA2 — Automatización de pruebas funcionales de San Felipe Stereo

**Estudiante:** Jairo Luis Corro Noriega  
**Asignatura:** Pruebas y Calidad del Software — Unidad 2  
**Actividad:** AA2 — Automatización de pruebas con JUnit y Selenium WebDriver  
**Modalidad:** Individual, con autorización del docente.

## Descripción

Este repositorio contiene la aplicación web San Felipe Stereo y un proyecto independiente de pruebas automatizadas escrito en Java. Las pruebas utilizan Selenium WebDriver, JUnit 5 y Maven, y se ejecutan en el navegador Brave (basado en Chromium).

El conjunto incluye 17 casos distribuidos en seis clases: navegación, programación semanal, perfiles de locutores, validaciones del formulario de contacto, controles del reproductor y comportamiento responsive.

## Requisitos

- Node.js y npm compatibles con las dependencias del proyecto.
- JDK 17 o superior (el desarrollo original se ejecutó con JDK 25.0.4.1).
- Apache Maven 3.10.0 o compatible.
- Brave Browser instalado.
- Conexión a Internet la primera vez que Selenium Manager necesite obtener el controlador del navegador y Maven deba descargar dependencias.

> Selenium utiliza `ChromeOptions` y `ChromeDriver` para automatizar Brave, porque Brave está basado en Chromium. No es necesario instalar Google Chrome cuando la ruta de Brave está configurada en los scripts.

## 1. Preparar la aplicación

Desde la raíz del repositorio (`san-felipe-stereo/`):

```powershell
npm ci
```

Si se requiere configurar el reproductor o el servicio externo del formulario, copia `.env.example` a `.env` y completa únicamente los valores necesarios para tu entorno. No publiques `.env` en GitHub. Las variables `VITE_*` se incorporan al cliente y no deben contener contraseñas ni claves privadas.

Inicia la aplicación: 

```powershell
npm run dev
```

Por defecto, Vite sirve la aplicación en `http://localhost:5173/`. Mantén esta terminal abierta durante la ejecución de pruebas.

Para comprobar la compilación de producción: 

```powershell
npm run build
```

## 2. Ejecutar las pruebas automatizadas

Abre una segunda terminal PowerShell y entra a la carpeta de automatización: 

```powershell
cd .\pruebas-automatizadas
mvn clean test
```

La URL predeterminada de las pruebas es `http://localhost:5173/`. Si ejecutas la aplicación en otra dirección, puedes indicarla como propiedad de Maven: 

```powershell
mvn clean test "-DbaseUrl=http://localhost:5173/"
```

## 3. Clases y casos de prueba

| Clase | Casos | Alcance |
|---|---:|---|
| `NavegacionTest` | 3 | Página principal, navegación a programación y contacto. |
| `ProgramacionTest` | 3 | Pestañas de lunes a viernes, sábado y domingo. |
| `LocutoresTest` | 2 | Perfiles de locutores y carga de fotografías. |
| `FormularioContactoTest` | 5 | Campos vacíos, nombre, contacto, asunto y mensaje inválidos. |
| `ReproductorTest` | 2 | Controles/estado del reproductor y ajuste del volumen. |
| `ResponsiveTest` | 2 | Navegación de escritorio y apertura/cierre del menú móvil. |
| **Total** | **17** | **Pruebas funcionales de interfaz.** |

## 4. Resultados de la ejecución documentada

En la ejecución reportada durante el desarrollo se obtuvo:

- Pruebas ejecutadas: **17**
- Aprobadas: **17**
- Fallidas: **0**
- Errores: **0**
- Omitidas: **0**
- Resultado Maven: **BUILD SUCCESS**

Los informes de JUnit/Surefire se generan en `pruebas-automatizadas/target/surefire-reports/`.

## 5. Evidencias

Los scripts guardan capturas PNG en estas carpetas:

```text
evidencias-aa2/
├── navegacion/
├── programacion/
├── locutores/
├── contacto/
├── reproductor/
└── responsive/
```

Se esperan 18 archivos de captura: tres de navegación, tres de programación, dos de locutores, cinco de contacto, dos del reproductor y tres de responsive.

## 6. Alcance y limitaciones

- La automatización se realizó en Brave, navegador basado en Chromium; no representa una certificación en todos los navegadores.
- La prueba responsive cambia el tamaño del viewport en un navegador de escritorio; no equivale a una prueba física en un teléfono.
- El reproductor se evalúa en cuanto a presencia de controles, estado inicial y control de volumen. No se certifica la disponibilidad ni continuidad de la señal de streaming.
- El formulario se prueba con datos inválidos para validar la respuesta del cliente. No se envía un mensaje válido al endpoint externo, para evitar comunicaciones reales durante la prueba. Por tanto, no se valida la recepción final en el servicio externo.
- Los 17 casos aprobados se limitan a los escenarios descritos y no demuestran ausencia total de defectos.

## 7. Seguridad y publicación en GitHub

No subir al repositorio:

- `.env`, `.env.local` ni archivos con credenciales o valores privados.
- `node_modules/`, `dist/` ni carpetas `target/` generadas por Maven.
- Archivos temporales o logs locales.

Conservar `package-lock.json` y `.env.example`. El `.gitignore` debe excluir los archivos y directorios anteriores, pero permitir `.env.example`.

## 8. Documentación

El informe técnico de AA2 se entrega en PDF, con máximo 15 páginas sin contar la portada. La versión editable en Word incluye espacios identificados para insertar las 18 capturas antes de exportar el documento a PDF.
