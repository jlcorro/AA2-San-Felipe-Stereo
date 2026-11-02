
import {
  Facebook,
  Globe,
  Instagram,
  Youtube,
  MessageCircle,
} from 'lucide-react'

import { site, type Social } from '../config/site'

const ICONS: Record<Social['platform'], typeof Globe> = {
  facebook: Facebook,
  instagram: Instagram,
  youtube: Youtube,
  whatsapp: MessageCircle,
  other: Globe,
}

export default function SocialLinks({
  className = '',
}: {
  className?: string
}) {
  if (site.socials.length === 0) return null

  return (
    <ul className={`flex flex-wrap gap-3 ${className}`}>
      {site.socials.map((s) => {
        const Icon = ICONS[s.platform] ?? Globe

        return (
          <li key={s.url}>
            <a
              href={s.url}
              target="_blank"
              rel="noopener noreferrer"
              aria-label={`${s.label} (se abre en una pestaña nueva)`}
              title={s.label}
              className="grid h-11 w-11 place-items-center rounded-full bg-gold text-navy hover:bg-gold-light transition-colors"
            >
              <Icon size={20} aria-hidden="true" />
            </a>
          </li>
        )
      })}
    </ul>
  )
}
