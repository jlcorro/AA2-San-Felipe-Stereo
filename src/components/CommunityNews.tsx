import { Megaphone } from 'lucide-react'
import SocialLinks from './SocialLinks'

export default function CommunityNews() {
  return (
    <section id="comunidad" aria-labelledby="titulo-com" className="bg-white py-16">
      <div className="mx-auto max-w-3xl px-4 text-center">
        <h2 id="titulo-com" className="font-display text-3xl font-bold">Comunidad y servicio social</h2>
        <div className="mt-8 rounded-3xl border border-dashed border-gold bg-cream p-10">
          <Megaphone className="mx-auto text-gold" size={36} aria-hidden />
          <p className="mt-4 text-lg font-semibold">Próximamente compartiremos noticias y novedades de nuestra comunidad</p>
          <p className="mt-2 text-navy/70">Aquí publicaremos actividades culturales, campañas sociales y mensajes de servicio público.</p>
          <a href="#contacto" className="mt-6 inline-block rounded-full bg-navy px-6 py-3 font-semibold text-gold-light hover:bg-navy-2">Proponer un anuncio comunitario</a>
          <SocialLinks className="mt-6 justify-center" />
        </div>
      </div>
    </section>
  )
}
