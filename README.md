# San Felipe Stereo – Sitio web

React + TypeScript (estricto) + Vite + Tailwind CSS v4 + Lucide React.

## Requisitos
- Node.js 20 o superior (https://nodejs.org, versión LTS) y npm.

## Instalación y uso
```bash
npm install        # instala dependencias
cp .env.example .env   # en Windows: copy .env.example .env
npm run dev        # servidor local (http://localhost:5173)
npm run typecheck  # verificación de TypeScript
npm run build      # versión de producción en /dist
npm run preview    # probar la versión de producción
```

## Configuración
- **Streaming:** en `.env` escribe `VITE_RADIO_STREAM_URL=<URL oficial>` y reinicia `npm run dev`. Usa https y un formato MP3/AAC. Si el sitio está en https y el stream en http, el navegador lo bloqueará.
- **Logotipo:** copia el archivo a `public/images/` y en `src/config/site.ts` pon `logoSrc: '/images/logo.png'` (el nombre real de tu archivo).
- **Redes sociales:** en `src/config/site.ts`, agrega objetos a `socials` solo con URL reales: `{ label: 'Facebook', url: 'https://…', platform: 'facebook' }`.
- **Formulario:** opcionalmente define `VITE_FORM_ENDPOINT` (p. ej. un endpoint de Formspree). Sin él, el formulario no envía y ofrece el correo.
- **Programación:** edita `src/data/programs.ts`. Los horarios marcados `pending` se muestran como «por confirmar».
- **Dirección y WhatsApp:** no se muestran hasta verificarlos.

## Publicación
Ejecuta `npm run build` y sube la carpeta `dist/` a Netlify, Vercel, Cloudflare Pages o cualquier hosting estático (comando de build `npm run build`, directorio `dist`). Configura `VITE_RADIO_STREAM_URL` en las variables de entorno del servicio y vuelve a construir.

## Problemas frecuentes
- *«Streaming sin configurar»*: falta `VITE_RADIO_STREAM_URL`; reinicia el servidor tras editar `.env`.
- *Error de formato/CORS/contenido mixto*: revisa que la URL sea https, MP3/AAC y que el servidor permita su reproducción desde tu dominio.
- *Estilos ausentes*: confirma que `npm install` terminó sin errores.
