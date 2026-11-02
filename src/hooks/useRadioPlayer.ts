import { useCallback, useEffect, useRef, useState } from 'react'

export type PlayerStatus = 'unconfigured' | 'idle' | 'loading' | 'live' | 'error'

function describeError(code?: number): string {
  switch (code) {
    case 2:
      return 'Se perdió la conexión con el servidor de audio. Revisa tu internet y vuelve a intentarlo.'
    case 3:
      return 'El navegador no pudo decodificar el audio. Verifica que el servidor transmita MP3 o AAC.'
    case 4:
      return 'El navegador no pudo reproducir esta fuente. Causas posibles: formato no compatible, URL http en un sitio https (contenido mixto) o bloqueo del servidor (CORS). Usa una URL https con MP3/AAC y revisa la configuración del servidor de streaming.'
    default:
      return 'No se pudo reproducir la emisora. Inténtalo de nuevo en unos segundos.'
  }
}

export function useRadioPlayer(url: string) {
  const audioRef = useRef<HTMLAudioElement>(null)
  const [status, setStatus] = useState<PlayerStatus>(url ? 'idle' : 'unconfigured')
  const [error, setError] = useState('')
  const [volume, setVolume] = useState(0.8)

  useEffect(() => {
    if (audioRef.current) audioRef.current.volume = volume
  }, [volume])

  useEffect(() => {
    const a = audioRef.current
    if (!a) return
    const onPlay = () => setStatus('loading')
    const onWaiting = () => a.getAttribute('src') && setStatus('loading')
    const onPlaying = () => { setError(''); setStatus('live') }
    const onPause = () => setStatus(url ? 'idle' : 'unconfigured')
    const onError = () => {
      if (!a.getAttribute('src')) return
      setError(describeError(a.error?.code))
      setStatus('error')
    }
    const events: [string, () => void][] = [['play', onPlay], ['waiting', onWaiting], ['playing', onPlaying], ['pause', onPause], ['error', onError]]
    events.forEach(([e, fn]) => a.addEventListener(e, fn))
    return () => {
      events.forEach(([e, fn]) => a.removeEventListener(e, fn))
      a.pause()
    }
  }, [url])

  const play = useCallback(async () => {
    const a = audioRef.current
    if (!a || !url || (!a.paused && a.getAttribute('src'))) return
    setError('')
    setStatus('loading')
    try {
      a.src = url // se recarga la transmisión para escuchar lo que va al aire, no audio en búfer
      await a.play()
    } catch (e) {
      if (e instanceof DOMException && e.name === 'AbortError') return
      setError(e instanceof DOMException && e.name === 'NotAllowedError'
        ? 'El navegador bloqueó la reproducción. Pulsa de nuevo el botón de reproducir.'
        : describeError(a.error?.code ?? 4))
      setStatus('error')
    }
  }, [url])

  const pause = useCallback(() => {
    const a = audioRef.current
    if (!a) return
    a.pause()
    a.removeAttribute('src')
    a.load()
    setStatus(url ? 'idle' : 'unconfigured')
  }, [url])

  const toggle = useCallback(() => {
    if (status === 'live' || status === 'loading') pause()
    else void play()
  }, [status, play, pause])

  return { audioRef, status, error, volume, setVolume, play, pause, toggle }
}

export type RadioPlayer = ReturnType<typeof useRadioPlayer>
