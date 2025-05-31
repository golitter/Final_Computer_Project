<template>
  <self-card :icon="['fas', 'tags']" iconColor="#db669f" title="Tags">
    <div v-if="loading" class="loading-container">
      <el-skeleton :rows="3" animated />
    </div>
    <template v-else>
      <div v-if="tag_clouds.length > 0" class="tag_clouds">
        <router-link 
          class="tag_item" 
          v-for="tag in tag_clouds" 
          :key="tag.id" 
          :to="`/tag/${tag.id}`" 
          :style="tag.style"
        >
          {{ tag.name }}
        </router-link>
      </div>
      <el-empty v-else description="No tags found" />
    </template>
  </self-card>
</template>

<script setup lang="ts">
import SelfCard from './SelfCard.vue';
import { ref, computed, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useTagStore } from '../stores/tag';

// Use Pinia Store
const tagStore = useTagStore();
const { tagCounts, loading } = storeToRefs(tagStore);

// Calculate tag cloud styles
const tag_clouds = computed(() => 
  tagCounts.value.map((tag) => ({
    id: tag.id,
    name: tag.name,
    style: {
      fontSize: `${Math.min(tag.count * 2 + 14, 28)}px`,
      color: getRandomColor(),
    },
  }))
);

// Get random color
const colors = [
  '#409EFF', // Blue
  '#67C23A', // Green
  '#E6A23C', // Yellow
  '#F56C6C', // Red
  '#909399', // Gray
  '#9B59B6', // Purple
  '#3498DB', // Sky Blue
  '#1ABC9C', // Turquoise
  '#E74C3C', // Orange Red
  '#34495E'  // Dark Gray
];

const getRandomColor = () => {
  const randomIndex = Math.floor(Math.random() * colors.length);
  return colors[randomIndex];
};

// Get tag data when component is mounted
onMounted(() => {
  tagStore.fetchTagCounts();
});
</script>

<style scoped lang="scss">
.loading-container {
  padding: 10px;
}

.tag_clouds {
  font-size: 14px;
  box-sizing: border-box;
  padding: 8px;
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.tag_item {
  text-decoration: none;
  display: inline-block;
  transition: all 0.3s;
  padding: 4px 8px;
  overflow-wrap: break-word;
  line-height: 1.2;
  border-radius: 4px;
  background-color: var(--el-bg-color-page);

  &:hover {
    color: var(--el-color-primary) !important;
    transform: translateY(-2px);
    background-color: var(--el-color-primary-light-9);
  }
}
</style>
