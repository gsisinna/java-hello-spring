import { createVuetify } from 'vuetify'

export const vuetify = createVuetify({
  defaults: {
    VCard: {
      rounded: 'xl',
      elevation: 0,
    },
    VBtn: {
      rounded: 'xl',
    },
    VTextField: {
      variant: 'outlined',
      rounded: 'xl',
    },
    VTextarea: {
      variant: 'outlined',
      rounded: 'xl',
    },
    VSelect: {
      variant: 'outlined',
      rounded: 'xl',
    },
  },
  theme: {
    defaultTheme: 'studio',
    themes: {
      studio: {
        dark: false,
        colors: {
          background: '#f4eee4',
          surface: '#fffaf2',
          primary: '#214a4c',
          secondary: '#db8f54',
          accent: '#4f9b94',
          error: '#b33f48',
          info: '#355e7c',
          success: '#2d7d63',
          warning: '#c07a20',
        },
      },
    },
  },
})
