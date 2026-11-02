import { site } from '../config/site'
import Logo from './Logo'
import SocialLinks from './SocialLinks'

export default function Footer() {
  return (
    <footer className="bg-navy text-cream">
      <div className="mx-auto grid max-w-6xl gap-8 px-4 py-12 md:grid-cols-3">
        <div><Logo /><p className="mt-3 font-display italic text-gold-light">{site.slogan}</p><SocialLinks className="mt-4" /></div>
        <nav aria-label="Pie de página"><ul className="space-y-2 text-sm">{site.nav.map((n) => <li key={n.href}><a href={n.href} className="hover:text-gold-light">{n.label}</a></li>)}</ul></nav>
        <address className="space-y-2 text-sm not-italic">
          <p>{site.location}</p>
          <p><a href={site.phoneHref} className="hover:text-gold-light">{site.phone}</a></p>
          <p><a href={`mailto:${site.email}`} className="break-all hover:text-gold-light">{site.email}</a></p>
        </address>
      </div>
      <p className="border-t border-white/10 py-4 text-center text-xs text-cream/70">© {new Date().getFullYear()} {site.legalName}. Todos los derechos reservados.</p>
    </footer>
  )
}
