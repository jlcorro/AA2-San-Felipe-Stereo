import { CalendarClock, Play } from 'lucide-react'
import { site } from '../config/site'
import Logo from './Logo'

export default function HeroSection({ onListen }: { onListen: () => void }) {
  return (
    <section id="inicio" className="relative overflow-hidden bg-navy text-white" aria-labelledby="titulo-inicio">
      <div aria-hidden className="absolute inset-0 bg-[radial-gradient(circle_at_80%_20%,rgba(217,173,82,0.28),transparent_45%),radial-gradient(circle_at_10%_90%,rgba(20,39,71,1),transparent_60%)]" />
      <div aria-hidden className="absolute -right-24 top-1/2 h-[28rem] w-[28rem] -translate-y-1/2 rounded-full border border-gold/30 shadow-[0_0_0_3rem_rgba(217,173,82,0.05),0_0_0_6rem_rgba(217,173,82,0.04)]" />
      <div className="relative mx-auto max-w-6xl px-4 py-20 sm:py-28">
        <Logo className="mb-8 h-20" />
        <h1 id="titulo-inicio" className="font-display text-5xl font-bold sm:text-7xl">{site.name}</h1>
        <p className="mt-3 font-display text-2xl italic text-gold-light sm:text-3xl">{site.slogan}</p>
        <p className="mt-6 max-w-xl text-lg leading-relaxed text-cream/90">
          Conectamos a San Bernardo del Viento con la música, la información, la cultura y el servicio social.
        </p>
        <div className="mt-10 flex flex-wrap gap-4">
          <a href="#en-vivo" onClick={onListen} className="inline-flex items-center gap-2 rounded-full bg-gold px-7 py-3.5 font-bold text-navy shadow-lg hover:bg-gold-light">
            <Play size={18} aria-hidden /> Escuchar en vivo
          </a>
          <a href="#programacion" className="inline-flex items-center gap-2 rounded-full border border-gold/60 px-7 py-3.5 font-semibold text-gold-light hover:bg-white/5">
            <CalendarClock size={18} aria-hidden /> Conocer la programación
          </a>
        </div>
      </div>
    </section>
  )
}
