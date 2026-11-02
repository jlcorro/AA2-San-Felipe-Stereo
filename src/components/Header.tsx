import { useState } from 'react'
import { Menu, Radio, X } from 'lucide-react'
import { site } from '../config/site'
import Logo from './Logo'

export default function Header({ onListen }: { onListen: () => void }) {
  const [open, setOpen] = useState(false)
  return (
    <header className="sticky top-0 z-40 border-b border-white/10 bg-navy/95 backdrop-blur">
      <div className="mx-auto flex h-16 max-w-6xl items-center justify-between gap-4 px-4">
        <a href="#inicio" aria-label={`${site.name}, ir al inicio`}><Logo /></a>
        <nav aria-label="Principal" className="hidden items-center gap-6 lg:flex">
          {site.nav.map((n) => (
            <a key={n.href} href={n.href} className="text-sm text-cream/85 transition-colors hover:text-gold-light">{n.label}</a>
          ))}
        </nav>
        <div className="flex items-center gap-2">
          <a href="#en-vivo" onClick={onListen} className="inline-flex items-center gap-2 rounded-full bg-gold px-4 py-2 text-xs font-bold tracking-wide text-navy hover:bg-gold-light sm:text-sm">
            <Radio size={16} aria-hidden /> ESCUCHAR EN VIVO
          </a>
          <button type="button" className="rounded-lg p-2 text-white lg:hidden" aria-expanded={open} aria-controls="menu-movil" aria-label={open ? 'Cerrar menú' : 'Abrir menú'} onClick={() => setOpen(!open)}>
            {open ? <X /> : <Menu />}
          </button>
        </div>
      </div>
      {open && (
        <nav id="menu-movil" aria-label="Menú móvil" className="border-t border-white/10 bg-navy-2 px-4 py-2 lg:hidden">
          {site.nav.map((n) => (
            <a key={n.href} href={n.href} onClick={() => setOpen(false)} className="block rounded-lg px-3 py-3 text-cream hover:bg-white/5">{n.label}</a>
          ))}
        </nav>
      )}
    </header>
  )
}
