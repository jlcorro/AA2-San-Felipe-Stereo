import { site } from './config/site'
import { useRadioPlayer } from './hooks/useRadioPlayer'
import Header from './components/Header'
import HeroSection from './components/HeroSection'
import LiveRadioPlayer, { MiniPlayer } from './components/LiveRadioPlayer'
import ProgramSchedule from './components/ProgramSchedule'
import HostsSection from './components/HostsSection'
import AboutSection from './components/AboutSection'
import CommunityNews from './components/CommunityNews'
import ContactSection from './components/ContactSection'
import Footer from './components/Footer'

export default function App() {
  const player = useRadioPlayer(site.streamUrl)
  const showMini = player.status === 'live' || player.status === 'loading' || player.status === 'error'
  return (
    <div className={showMini ? 'pb-16' : ''}>
      <audio ref={player.audioRef} preload="none" />
      <Header onListen={() => void player.play()} />
      <main>
        <HeroSection onListen={() => void player.play()} />
        <LiveRadioPlayer player={player} />
        <ProgramSchedule />
        <HostsSection />
        <AboutSection />
        <CommunityNews />
        <ContactSection />
      </main>
      <Footer />
      <MiniPlayer player={player} />
    </div>
  )
}
