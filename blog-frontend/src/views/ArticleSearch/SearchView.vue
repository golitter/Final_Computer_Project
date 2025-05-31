<template>
  <div class="search-container">
    <div class="search-header">
      <h2>Search Results</h2>
      <p v-if="searchQuery">Search Keyword: {{ searchQuery }}</p>
    </div>

    <div v-if="searchResults.length > 0" class="search-results">
      <div v-for="article in searchResults" :key="article.id" class="article-card" @click="goToArticleDetail(article)">
        <h3>{{ article.title }}</h3>
        <p class="summary">{{ article.summary }}</p>
        <div class="article-meta">
          <span class="time">{{ formatDate(article.createTime) }}</span>
          <span class="views">Views: {{ article.viewCount }}</span>
        </div>
      </div>
    </div>
    <div v-else class="no-results">
      <p>No related articles found</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';

interface Article {
  id: number;
  title: string;
  summary: string;
  content: string;
  createTime: string;
  viewCount: number;
}

const route = useRoute();
const router = useRouter();
const searchQuery = ref(route.query.q as string);
const searchResults = ref<Article[]>([]);

// Parse search results
onMounted(() => {
  try {
    const results = route.query.results as string;
    if (results) {
      searchResults.value = JSON.parse(results);
    }
  } catch (error) {
    console.error('Failed to parse search results:', error);
    ElMessage.error('Failed to load search results');
  }
});

// Format date
const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  });
};

// Navigate to article detail page
const goToArticleDetail = (article: Article) => {
  router.push(`/article/${article.id}`);
};
</script>

<style scoped lang="scss">
.search-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;

  .search-header {
    margin-bottom: 30px;
    text-align: center;

    h2 {
      font-size: 24px;
      color: var(--el-text-color-primary);
      margin-bottom: 10px;
    }

    p {
      color: var(--el-text-color-regular);
    }
  }

  .search-results {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
  }

  .article-card {
    background: #fff;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: all 0.3s ease;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.15);
    }

    h3 {
      font-size: 18px;
      margin-bottom: 10px;
      color: var(--el-text-color-primary);
    }

    .summary {
      color: var(--el-text-color-regular);
      margin-bottom: 15px;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }

    .article-meta {
      display: flex;
      justify-content: space-between;
      color: var(--el-text-color-secondary);
      font-size: 14px;
    }
  }

  .no-results {
    text-align: center;
    padding: 40px;
    color: var(--el-text-color-regular);
  }
}
</style> 