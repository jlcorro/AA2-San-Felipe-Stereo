import { site } from '../config/site'

export default function Logo({ className = 'h-10' }: { className?: string }) {
  if (site.logoSrc) {
    return <img src={site.logoSrc} alt={`Logotipo de ${site.name}`} className={`${className} w-auto object-contain`} />
  }
  return (
    <span className="font-display text-xl font-bold tracking-tight text-white">
      San Felipe <span className="text-gold">Stereo</span>
    </span>
  )
}
