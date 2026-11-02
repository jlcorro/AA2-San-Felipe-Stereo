export type DayGroup = 'weekdays' | 'saturday' | 'sunday'

export interface Program {
  start: string
  end?: string // solo cuando la emisora la haya informado
  name: string
  category: string
  host: string
  pending?: string // nota de horario por confirmar
}

export const DAY_TABS: { id: DayGroup; label: string }[] = [
  { id: 'weekdays', label: 'Lunes a viernes' },
  { id: 'saturday', label: 'Sábado' },
  { id: 'sunday', label: 'Domingo' },
]

const SF = 'San Felipe Stereo'
const CR = 'Corporación Radial San Felipe Stereo'
const p = (start: string, name: string, category: string, host: string, extra: Partial<Program> = {}): Program => ({ start, name, category, host, ...extra })
const MID = 'Horario por confirmar.'

export const PROGRAMS: Record<DayGroup, Program[]> = {
  weekdays: [
    p('5:00 a. m.', 'Nuestro Folklor', 'Musical', SF),
    p('6:00 a. m.', 'Himno Nacional', 'Institucional', SF),
    p('6:05 a. m.', 'Reflexión Diaria', 'Institucional', SF),
    p('6:15 a. m.', 'Amanecer Vallenato', 'Musical', SF),
    p('8:30 a. m.', 'Notideportes', 'Deportivo', 'Everaldo Jiménez'),
    p('9:00 a. m.', 'Programación Musical Cross Over', 'Musical', SF),
    p('10:00 a. m.', 'Hablando con la Policía', 'Convivencia ciudadana', 'Policía Nacional'),
    p('10:30 a. m.', 'Programación Musical Cross Over', 'Musical', SF),
    p('11:30 a. m.', 'Servicio Informativo Comunitario', 'Servicio social', SF),
    p('12:00 m.', 'Vallenato Clásico', 'Musical', SF),
    p('2:00 p. m.', 'Unión Cristiana', 'Religioso', 'Everaldo Jiménez'),
    p('3:00 p. m.', 'El Show del Reggaetón', 'Musical', SF),
    p('4:00 p. m.', 'El Swing de la Tarde', 'Musical', 'Luis Corro Sarza', { end: '5:55 p. m.' }),
    p('6:00 p. m.', 'Himno Nacional', 'Institucional', SF),
    p('6:05 p. m.', 'Noticiero Regional', 'Informativo', 'Edilberto Corro Sarza'),
    p('7:00 p. m.', 'Pasaporte al Mundo', 'Informativo', 'Arnulfo Herrera Jr.'),
    p('8:00 p. m.', 'Recuerdos de Antaño', 'Complacencia musical', 'Luis Corro Sarza'),
    p('10:00 p. m.', 'Música Cross Over', 'Musical', SF),
  ],
  saturday: [
    p('5:00 a. m.', 'Nuestro Folklor', 'Cultural', CR),
    p('6:00 a. m.', 'Himno Nacional', 'Institucional', CR),
    p('6:05 a. m.', 'Reflexión Diaria', 'Institucional', CR),
    p('6:15 a. m.', 'Vallenatos Clásicos', 'Musical', CR),
    p('9:00 a. m.', 'El Artista de la Semana de la Región', 'Cultural', 'Everaldo Jiménez'),
    p('10:00 a. m.', 'Los 20 Éxitos de la Semana', 'Musical', 'Luis Corro Sarza'),
    p('12:00 m.', 'Programación Musical Crossover', 'Musical', CR),
    p('3:00 p. m.', 'El Artista Vallenato de la Semana', 'Cultural', 'Everaldo Jiménez'),
    p('4:00 p. m.', 'El Show Afrocaribe', 'Musical', 'Luis Corro Sarza'),
    p('6:00 p. m.', 'Himno Nacional', 'Institucional', CR),
    p('6:05 p. m.', 'Crossovers del Fin de Semana', 'Musical', CR),
    p('12:00 a. m.', 'Crossovers Clásicos', 'Musical', CR, { pending: MID }),
  ],
  sunday: [
    p('6:00 a. m.', 'Himno Nacional', 'Institucional', CR),
    p('6:05 a. m.', 'Reflexión del Día', 'Institucional', CR),
    p('6:15 a. m.', 'Juglares de la Región', 'Cultural', 'Luis Corro Sarza'), 
    p('9:00 a. m.', 'Informativo Escolar', 'Cultural', 'Estudiantes'),
    p('10:00 a. m.', 'Programación Musical Crossovers', 'Musical', CR),
    p('12:00 m.', 'Vallenatos del Ayer', 'Musical', CR),
    p('2:00 p. m.', 'Servicios Sociales y Complacencias Musicales', 'Informativo y musical', 'Everaldo Jiménez'),
    p('4:00 p. m.', 'Programación Musical Crossovers', 'Musical', CR),
    p('6:00 p. m.', 'Himno Nacional', 'Institucional', CR),
    p('6:05 p. m.', 'Parejitas Musicales (complacencias)', 'Musical', 'Luis Corro Sarza'),
    p('12:00 a. m.', 'Clásicos Tropicales', 'Musical', CR, { pending: MID }),
  ],
}
