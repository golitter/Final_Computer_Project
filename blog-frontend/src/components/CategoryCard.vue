<template>
  <self-card :icon="['fas', 'folder']" iconColor="#1e90ff" title="Categories">
    <div v-if="loading" class="loading-container">
      <el-skeleton :rows="3" animated />
    </div>
    <template v-else>
      <div v-if="categoryCounts.length > 0" class="category-list">
        <router-link 
          v-for="category in topThreeCategories" 
          :key="category.id" 
          :to="`/category/${category.id}`"
          class="category-item"
        >
          <div class="category-info">
            <span class="category-name">{{ category.name }}</span>
            <span class="category-count">{{ category.count }}</span>
          </div>
          <div class="category-progress">
            <el-progress 
              :percentage="calculatePercentage(category.count)"
              :show-text="false"
              :stroke-width="4"
              :color="getRandomColor()"
            />
          </div>
        </router-link>
      </div>
      <el-empty v-else description="No categories found" />
    </template>
  </self-card>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { storeToRefs } from 'pinia';
import { useCategoryAboutStore } from '@/stores/categoryAbout';
import SelfCard from './SelfCard.vue';

// Use store
const categoryStore = useCategoryAboutStore();
const { categoryCounts, loading } = storeToRefs(categoryStore);

// Calculate top three categories
const topThreeCategories = computed(() => {
  return categoryCounts.value.slice(0, 3);
});

// Fetch category data when component is mounted
onMounted(() => {
  categoryStore.fetchCategoryCounts();
});

// Calculate percentage
const calculatePercentage = (count: number): number => {
  if (categoryCounts.value.length === 0) return 0;
  const maxCount = Math.max(...categoryCounts.value.map(c => c.count));
  return Math.round((count / maxCount) * 100);
};

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

const colorCache = new Map<number, string>();

const getRandomColor = (): string => {
  const randomIndex = Math.floor(Math.random() * colors.length);
  return colors[randomIndex];
};
</script>

<style scoped lang="scss">
.loading-container {
  padding: 10px;
}

.category-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.category-item {
  text-decoration: none;
  color: var(--el-text-color-primary);
  transition: all 0.3s ease;
  padding: 8px 12px;
  border-radius: 6px;
  background-color: var(--el-bg-color-page);

  &:hover {
    background-color: var(--el-color-primary-light-9);
    transform: translateX(4px);

    .category-name {
      color: var(--el-color-primary);
    }
  }
}

.category-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 6px;
}

.category-name {
  font-size: 14px;
  transition: color 0.3s ease;
}

.category-count {
  font-size: 12px;
  color: var(--el-text-color-secondary);
  background-color: var(--el-color-info-light-9);
  padding: 2px 6px;
  border-radius: 10px;
  min-width: 24px;
  text-align: center;
}

.category-progress {
  margin-top: 4px;
}

:deep(.el-progress-bar__outer) {
  border-radius: 4px;
  background-color: var(--el-color-info-light-8);
}

:deep(.el-progress-bar__inner) {
  border-radius: 4px;
  transition: all 0.3s ease;
}
</style>