import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

export const useSessionStore = defineStore('session', () => {
  const storedUsername = typeof window === 'undefined'
    ? null
    : window.localStorage.getItem('learning-ui.username')
  const storedPassword = typeof window === 'undefined'
    ? null
    : window.localStorage.getItem('learning-ui.password')

  const username = ref(storedUsername ?? 'student')
  const password = ref(storedPassword ?? 'password')

  const hasCredentials = computed(() => username.value.length > 0 && password.value.length > 0)

  function updateCredentials(nextUsername: string, nextPassword: string) {
    username.value = nextUsername
    password.value = nextPassword

    if (typeof window !== 'undefined') {
      window.localStorage.setItem('learning-ui.username', nextUsername)
      window.localStorage.setItem('learning-ui.password', nextPassword)
    }
  }

  return {
    username,
    password,
    hasCredentials,
    updateCredentials,
  }
})
