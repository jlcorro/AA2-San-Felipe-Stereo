/// <reference types="vite/client" />
interface ImportMetaEnv {
  readonly VITE_RADIO_STREAM_URL?: string
  readonly VITE_FORM_ENDPOINT?: string
}
interface ImportMeta {
  readonly env: ImportMetaEnv
}
