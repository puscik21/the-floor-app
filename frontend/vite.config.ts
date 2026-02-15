import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  base: './',
  server: {
    proxy: {
      // Proxy requests to Spring during development
      '/api': 'http://localhost:8080'
    }
  }
})
