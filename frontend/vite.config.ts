import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  base: './',
  define: {
    global: 'window' // Fix for "global is not defined" error in 'sockjs-client' lib
  },
  server: {
    proxy: {
      // Proxy requests to Spring during development
      '/api': 'http://localhost:8080'
    }
  }
})
