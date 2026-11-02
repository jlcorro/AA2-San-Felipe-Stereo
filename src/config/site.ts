export type Social = { label: string; url: string; platform: 'facebook' | 'instagram' | 'youtube' | 'whatsapp' | 'other' }

export const site = {
  name: 'San Felipe Stereo',
  legalName: 'Corporación Radial San Felipe Stereo',
  slogan: 'La voz de nuestra comunidad',
  tagline: 'Información, música, cultura y servicio social al servicio de nuestra comunidad',
  location: 'San Bernardo del Viento, Córdoba, Colombia',
  address: 'Calle 8 # 8-33, barrio San Felipe',
  phone: '314 504 1369',
  phoneHref: 'tel:+573145041369',
  email: 'sanfelipestereo@hotmail.com',
  streamUrl: (import.meta.env.VITE_RADIO_STREAM_URL ?? '').trim(),
  formEndpoint: (import.meta.env.VITE_FORM_ENDPOINT ?? '').trim(),
  logoSrc: '/images/Logo.svg',
socials: [
  {
    label: 'Facebook',
    url: 'https://www.facebook.com/share/1cVjBgVXXH/',
    platform: 'facebook',
  },
  {
    label: 'Instagram',
    url: 'https://www.instagram.com/sanfelipestereo/',
    platform: 'instagram',
  },
  {
    label: 'WhatsApp',
    url: 'https://wa.me/573145041369',
    platform: 'whatsapp',
  },
] as Social[],
  nav: [
    { label: 'Inicio', href: '#inicio' },
    { label: 'Escuchar en vivo', href: '#en-vivo' },
    { label: 'Programación', href: '#programacion' },
    { label: 'Locutores', href: '#locutores' },
    { label: 'Nosotros', href: '#nosotros' },
    { label: 'Comunidad', href: '#comunidad' },
    { label: 'Contacto', href: '#contacto' },
  ],
  hosts: [
  {
    name: 'Luis Enrique Corro Sarza',
    image: '/images/locutores/Luis_Corro.jpeg',
  },
  {
    name: 'Arnulfo Herrera Mosquera',
    image: '/images/locutores/Arnulfo_Herrera.jpeg',
  },
  {
    name: 'Everaldo Giménez Sierra',
    image: '/images/locutores/Everaldo_Gimenez_Sierra.jpeg',
  },
  {
    name: 'Eduin Conde Angulo',
    image: '/images/locutores/Eduin_Conde.jpeg',
  },
  {
    name: 'Edilberto Corro Sarza',
    image: '/images/locutores/Edilberto_Corro.jpeg',
  },
],
}
