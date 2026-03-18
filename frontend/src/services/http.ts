import axios from 'axios'

export const http = axios.create({
  baseURL: '/',
  timeout: 10000,
})

export function createBasicAuthHeader(username: string, password: string) {
  return `Basic ${btoa(`${username}:${password}`)}`
}
