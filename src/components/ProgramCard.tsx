import { Clock } from 'lucide-react'
import type { Program } from '../data/programs'

export default function ProgramCard({ program }: { program: Program }) {
  return (
    <li className="flex gap-4 rounded-2xl border border-navy/10 bg-white p-4 shadow-sm">
      <div className="flex w-28 shrink-0 items-start gap-1.5 text-sm font-bold text-navy">
        <Clock size={16} className="mt-0.5 text-gold" aria-hidden />
        <span>{program.start}{program.end && <><br /><span className="font-normal text-navy/70">a {program.end}</span></>}</span>
      </div>
      <div className="min-w-0">
        <h3 className="font-semibold">{program.name}</h3>
        <p className="text-sm text-navy/70">{program.category} · {program.host}</p>
        {program.pending && <p className="mt-2 inline-block rounded bg-gold-light/60 px-2 py-0.5 text-xs font-semibold">{program.pending}</p>}
      </div>
    </li>
  )
}
