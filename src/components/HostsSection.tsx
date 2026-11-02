
import { site } from '../config/site'

export default function HostsSection() {
  return (
    <section id="locutores" className="section-padding">
      <div className="container mx-auto">
        <h2 className="font-display text-3xl text-navy mb-8">
          Nuestros locutores
        </h2>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {site.hosts.map((host) => (
            <article
              key={host.name}
              className="overflow-hidden rounded-[28px] border border-gray-200 bg-[#F7F5F0] shadow-sm"
            >
              <div className="aspect-[4/3] overflow-hidden bg-[#EAE8E2]">
                {host.image ? (
                  <img
                    src={host.image}
                    alt={`Fotografía de ${host.name}`}
                    className="h-full w-full object-cover object-center"
                    loading="lazy"
                  />
                ) : (
                  <div className="h-full flex items-center justify-center">
                    <span className="text-gold text-4xl font-display">
                      {host.name
                        .split(' ')
                        .map((word) => word[0])
                        .slice(0, 2)
                        .join('')}
                    </span>
                  </div>
                )}
              </div>

              <div className="p-6 text-center">
                <h3 className="font-semibold text-navy">
                  {host.name}
                </h3>

                <p className="text-sm text-slate-500 mt-2">
                  Locutor de San Felipe Stereo
                </p>
              </div>
            </article>
          ))}
        </div>
      </div>
    </section>
  )
}
