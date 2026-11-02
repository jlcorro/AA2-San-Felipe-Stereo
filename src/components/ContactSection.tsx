import { useState, type FormEvent } from 'react'
import { Mail, MapPin, Phone } from 'lucide-react'
import { site } from '../config/site'

type Fields = { name: string; contact: string; subject: string; message: string }
const EMPTY: Fields = { name: '', contact: '', subject: '', message: '' }
type State = 'idle' | 'sending' | 'sent' | 'failed' | 'no-backend'

function validate(f: Fields): Partial<Fields> {
  const e: Partial<Fields> = {}
  const c = f.contact.trim()
  if (f.name.trim().length < 2) e.name = 'Escribe tu nombre.'
  if (!(/^\S+@\S+\.\S+$/.test(c) || c.replace(/\D/g, '').length >= 7)) e.contact = 'Escribe un correo o un teléfono válido.'
  if (f.subject.trim().length < 3) e.subject = 'Escribe un asunto.'
  if (f.message.trim().length < 10) e.message = 'El mensaje debe tener al menos 10 caracteres.'
  return e
}

const FIELDS: { key: keyof Fields; label: string; area?: boolean }[] = [
  { key: 'name', label: 'Nombre' },
  { key: 'contact', label: 'Correo electrónico o teléfono' },
  { key: 'subject', label: 'Asunto' },
  { key: 'message', label: 'Mensaje', area: true },
]

export default function ContactSection() {
  const [f, setF] = useState<Fields>(EMPTY)
  const [errors, setErrors] = useState<Partial<Fields>>({})
  const [state, setState] = useState<State>('idle')

  const submit = async (ev: FormEvent) => {
    ev.preventDefault()
    const e = validate(f)
    setErrors(e)
    if (Object.keys(e).length) return
    if (!site.formEndpoint) { setState('no-backend'); return }
    setState('sending')
    try {
      const r = await fetch(site.formEndpoint, { method: 'POST', headers: { 'Content-Type': 'application/json', Accept: 'application/json' }, body: JSON.stringify(f) })
      if (!r.ok) throw new Error(String(r.status))
      setState('sent'); setF(EMPTY)
    } catch { setState('failed') }
  }

  const mailto = `mailto:${site.email}?subject=${encodeURIComponent(f.subject || 'Contacto desde la web')}&body=${encodeURIComponent(f.message)}`
  const input = 'mt-1 w-full rounded-xl border border-navy/20 bg-white px-4 py-3'

  return (
    <section id="contacto" aria-labelledby="titulo-con" className="mx-auto grid max-w-6xl gap-10 px-4 py-16 md:grid-cols-2">
      <div>
        <h2 id="titulo-con" className="font-display text-3xl font-bold">Contacto</h2>
        <ul className="mt-6 space-y-4">
          <li className="flex items-center gap-3"><Phone className="text-gold" aria-hidden /><a href={site.phoneHref} className="hover:underline">{site.phone}</a></li>
          <li className="flex items-center gap-3"><Mail className="text-gold" aria-hidden /><a href={`mailto:${site.email}`} className="break-all hover:underline">{site.email}</a></li>
          <li className="flex items-center gap-3"><MapPin className="text-gold" aria-hidden />{site.location}</li>
        </ul>
      </div>
      <form onSubmit={submit} noValidate className="space-y-4 rounded-3xl bg-white p-6 shadow-sm ring-1 ring-navy/10">
        {FIELDS.map(({ key, label, area }) => (
          <div key={key}>
            <label htmlFor={`f-${key}`} className="text-sm font-semibold">{label}</label>
            {area
              ? <textarea id={`f-${key}`} rows={4} value={f[key]} onChange={(e) => setF({ ...f, [key]: e.target.value })} aria-invalid={!!errors[key]} aria-describedby={errors[key] ? `e-${key}` : undefined} className={input} />
              : <input id={`f-${key}`} value={f[key]} onChange={(e) => setF({ ...f, [key]: e.target.value })} aria-invalid={!!errors[key]} aria-describedby={errors[key] ? `e-${key}` : undefined} className={input} />}
            {errors[key] && <p id={`e-${key}`} className="mt-1 text-sm text-red-700">{errors[key]}</p>}
          </div>
        ))}
        <button type="submit" disabled={state === 'sending'} className="rounded-full bg-navy px-6 py-3 font-semibold text-gold-light hover:bg-navy-2 disabled:opacity-60">{state === 'sending' ? 'Enviando…' : 'Enviar mensaje'}</button>
        <div role="status" aria-live="polite" className="text-sm">
          {state === 'sent' && <p className="text-green-800">Mensaje enviado. Gracias por escribirnos.</p>}
          {state === 'failed' && <p className="text-red-700">No se pudo enviar el mensaje. Escríbenos a <a className="underline" href={mailto}>{site.email}</a>.</p>}
          {state === 'no-backend' && <p className="text-navy/80">El formulario aún necesita un servicio de envío configurado, por eso no se envió nada. Puedes escribirnos directamente: <a className="font-semibold underline" href={mailto}>abrir mi correo</a>.</p>}
        </div>
      </form>
    </section>
  )
}
