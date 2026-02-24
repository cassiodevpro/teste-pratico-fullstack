import { createI18n } from 'vue-i18n'
import en from './en-US.json'
import pt from './pt-BR.json'

export default createI18n({
  legacy: false,
    locale: 'pt-BR',
    fallbackLocale: 'pt-BR',
  messages: {
    en,
    pt
  }
})
