<template>
  <button class="code-copy-btn" @click="copyCode" :title="buttonText">
    <font-awesome-icon :icon="['fas', copied ? 'check' : 'copy']" />
  </button>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElMessage } from 'element-plus';

const props = defineProps<{
  code: string;
}>();

const copied = ref(false);
const buttonText = ref('Copy Code');

const copyCode = async () => {
  try {
    await navigator.clipboard.writeText(props.code);
    copied.value = true;
    buttonText.value = 'Copied!';
    ElMessage.success('Code copied to clipboard');
    
    setTimeout(() => {
      copied.value = false;
      buttonText.value = 'Copy Code';
    }, 2000);
  } catch (err) {
    ElMessage.error('Copy failed, please copy manually');
  }
};
</script>

<style scoped lang="scss">
.code-copy-btn {
  position: absolute;
  top: 0.5em;
  right: 0.5em;
  padding: 0.4em 0.8em;
  color: #abb2bf;
  background: rgba(255, 255, 255, 0.1);
  border: none;
  border-radius: 4px;
  cursor: pointer;
  opacity: 0;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  gap: 0.5em;
  font-size: 0.9em;

  &:hover {
    background: rgba(255, 255, 255, 0.2);
  }
}
</style> 