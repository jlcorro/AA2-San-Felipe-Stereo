import { Loader2, Pause, Play, Volume2 } from 'lucide-react'
import { site } from '../config/site'
import type { PlayerStatus, RadioPlayer } from '../hooks/useRadioPlayer'

const LABEL: Record<PlayerStatus, string> = {
  unconfigured: 'Streaming sin configurar',
  idle: 'Listo para reproducir',
  loading: 'Conectando…',
  live: 'EN VIVO',
  error: 'No se pudo reproducir',
}

function PlayButton({ player, size = 'lg' }: { player: RadioPlayer; size?: 'lg' | 'sm' }) {
  const active = player.status === 'live' || player.status === 'loading'
  const dim = size === 'lg' ? 'h-16 w-16' : 'h-11 w-11'
  return (
    <button type="button" onClick={player.toggle} disabled={player.status === 'unconfigured'}
      aria-label={active ? 'Pausar la emisora' : 'Reproducir la emisora'}
      className={`${dim} grid shrink-0 place-items-center rounded-full bg-gold text-navy hover:bg-gold-light disabled:cursor-not-allowed disabled:opacity-40`}>
      {player.status === 'loading' ? <Loader2 className="animate-spin" aria-hidden /> : active ? <Pause aria-hidden /> : <Play aria-hidden className="translate-x-0.5" />}
    </button>
  )
}

export function Equalizer() {
  return <div className="eq" aria-hidden><span /><span /><span /><span /></div>
}

export default function LiveRadioPlayer({ player }: { player: RadioPlayer }) {
  const { status, error, volume, setVolume } = player
  return (
    <section id="en-vivo" aria-labelledby="titulo-vivo" className="bg-navy-2 py-16 text-white">
      <div className="mx-auto max-w-3xl px-4">
        <h2 id="titulo-vivo" className="font-display text-3xl font-bold">Escucha {site.name} en vivo</h2>
        <div className="mt-6 rounded-3xl border border-gold/30 bg-navy p-6 shadow-xl">
          <div className="flex flex-wrap items-center gap-5">
            <PlayButton player={player} />
            <div className="min-w-0 flex-1">
              <p className="font-display text-xl font-semibold">{site.name}</p>
              <p className="mt-1 flex items-center gap-2 text-sm" role="status" aria-live="polite">
                {status === 'live' && <span className="rounded bg-red-600 px-2 py-0.5 text-xs font-bold">EN VIVO</span>}
                <span className="text-cream/80">{status === 'live' ? 'Conexión establecida' : LABEL[status]}</span>
              </p>
            </div>
            {status === 'live' && <Equalizer />}
          </div>
          <div className="mt-6 flex items-center gap-3">
            <Volume2 size={20} className="text-gold" aria-hidden />
            <label htmlFor="volumen" className="sr-only">Volumen</label>
            <input id="volumen" type="range" min={0} max={1} step={0.05} value={volume} onChange={(e) => setVolume(Number(e.target.value))} className="w-full accent-[#D9AD52]" />
          </div>
          {status === 'unconfigured' && (
            <p className="mt-5 rounded-xl bg-white/5 p-4 text-sm text-cream/90">
              El streaming aún no está configurado. Define <code className="text-gold-light">VITE_RADIO_STREAM_URL</code> en el archivo <code className="text-gold-light">.env</code> y reinicia el servidor.
            </p>
          )}
          {status === 'error' && (
            <p role="alert" className="mt-5 rounded-xl border border-red-400/40 bg-red-500/10 p-4 text-sm text-red-100">
              {error} Puedes volver a intentarlo con el botón de reproducir.
            </p>
          )}
        </div>
      </div>
    </section>
  )
}

export function MiniPlayer({ player }: { player: RadioPlayer }) {
  if (player.status === 'idle' || player.status === 'unconfigured') return null
  return (
    <div className="fixed inset-x-0 bottom-0 z-30 border-t border-gold/30 bg-navy/95 px-4 py-2 text-white backdrop-blur">
      <div className="mx-auto flex max-w-6xl items-center gap-3">
        <PlayButton player={player} size="sm" />
        <p className="flex-1 truncate text-sm font-semibold">{site.name} <span className="font-normal text-cream/70">· {LABEL[player.status]}</span></p>
        {player.status === 'live' && <Equalizer />}
      </div>
    </div>
  )
}
