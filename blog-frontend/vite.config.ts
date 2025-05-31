import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, 'src') // Relative path alias configuration, use @ instead of src
    }
  },
  assetsInclude: ['**/*.pdf'],
  css: {
    preprocessorOptions: {
      scss: {
        javascriptEnabled: true,
        additionalData: '@use "./src/styles/variable.scss" as *;',
      },
    },
  },
})
