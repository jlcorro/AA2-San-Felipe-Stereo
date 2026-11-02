import { HeartHandshake, Landmark, Leaf, Scale, Users } from 'lucide-react'

const VALUES = [
  { icon: HeartHandshake, text: 'Convivencia pacífica' },
  { icon: Landmark, text: 'Participación democrática' },
  { icon: Users, text: 'Equidad de género' },
  { icon: Scale, text: 'Respeto por los derechos fundamentales' },
  { icon: Leaf, text: 'Protección del medio ambiente' },
]

export default function AboutSection() {
  return (
    <section id="nosotros" aria-labelledby="titulo-nos" className="mx-auto max-w-6xl px-4 py-16">
      <h2 id="titulo-nos" className="font-display text-3xl font-bold">Quiénes somos</h2>
      <p className="mt-3 max-w-2xl text-navy/80">Informamos, entretenemos, fortalecemos el tejido social y somos la voz de la comunidad, con respeto por la cultura local.</p>
      <div className="mt-8 grid gap-6 md:grid-cols-2">
        <article className="rounded-3xl bg-navy p-8 text-cream"><h3 className="font-display text-2xl text-gold-light">Misión</h3>
          <p className="mt-3 leading-relaxed">Planear, producir y transmitir contenidos radiales que informen, entretengan y conecten con nuestra audiencia, manteniendo el respeto por la cultura local y fortaleciendo el tejido social. Llevar a nuestros oyentes contenido relevante, responsable y cercano que represente la voz de la comunidad.</p></article>
        <article className="rounded-3xl bg-navy-2 p-8 text-cream"><h3 className="font-display text-2xl text-gold-light">Visión</h3>
          <p className="mt-3 leading-relaxed">Ser, para el año 2030, la junta de programación líder de la región, mantener un elevado índice de recordación en la población objetivo y liderar en innovación y servicios a la comunidad.</p></article>
      </div>
      <h3 className="mt-10 font-display text-2xl font-semibold">Nuestros valores</h3>
      <ul className="mt-4 grid gap-3 sm:grid-cols-2 lg:grid-cols-5">
        {VALUES.map(({ icon: Icon, text }) => (
          <li key={text} className="flex items-center gap-3 rounded-2xl bg-white p-4 text-sm font-medium ring-1 ring-navy/10"><Icon className="shrink-0 text-gold" aria-hidden />{text}</li>
        ))}
      </ul>
    </section>
  )
}
