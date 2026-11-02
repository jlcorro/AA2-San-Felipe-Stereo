import { useState } from 'react'
import { DAY_TABS, PROGRAMS, type DayGroup } from '../data/programs'
import ProgramCard from './ProgramCard'

export default function ProgramSchedule() {
  const [day, setDay] = useState<DayGroup>('weekdays')
  return (
    <section id="programacion" aria-labelledby="titulo-prog" className="mx-auto max-w-4xl px-4 py-16">
      <h2 id="titulo-prog" className="font-display text-3xl font-bold">Programación semanal</h2>
      <p className="mt-2 text-navy/70">Horarios de inicio informados por la emisora. Los que dicen «por confirmar» aún no están definidos.</p>
      <div role="tablist" aria-label="Días de la semana" className="mt-6 flex flex-wrap gap-2">
        {DAY_TABS.map((t) => (
          <button key={t.id} role="tab" id={`tab-${t.id}`} aria-selected={day === t.id} aria-controls="panel-prog" type="button" onClick={() => setDay(t.id)}
            className={`rounded-full px-5 py-2 text-sm font-semibold ${day === t.id ? 'bg-navy text-gold-light' : 'bg-white text-navy ring-1 ring-navy/15 hover:bg-gold-light/40'}`}>
            {t.label}
          </button>
        ))}
      </div>
      <ul id="panel-prog" role="tabpanel" aria-labelledby={`tab-${day}`} className="mt-6 grid gap-3">
        {PROGRAMS[day].map((p, i) => <ProgramCard key={`${day}-${i}`} program={p} />)}
      </ul>
    </section>
  )
}
