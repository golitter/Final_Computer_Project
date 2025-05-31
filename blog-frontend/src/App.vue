<template>
  <div class="app">
    <router-view />
    <AIChat />
  </div>
</template>

<script setup lang="ts">
import AIChat from '@/components/AIChat.vue'
</script>

<style scoped>
.app {
  min-height: 100vh;
  position: relative;
}
</style>