<template>
    <self-card :icon="['fas', 'note-sticky']" iconColor="#1e90ff" title="Random Articles">
        <div v-if="loading" class="loading-container">
            <el-skeleton :rows="3" animated />
        </div>
        <template v-else>
            <div v-if="random_articles.length > 0" class="random_article_list">
                <div v-for="article in random_articles" :key="article.id" class="random_article_item">
                    <router-link :to="`/article/${article.id}`" class="random_article_title">
                        {{ article.title }}
                    </router-link>
                    <div class="random_article_meta">
                        <span class="create-time">{{ format_date(article.createTime) }}</span>
                        <span class="view-count">{{ article.viewCount }} views</span>
                    </div>
                </div> 
            </div>
            <el-empty v-else description="No articles found" />
        </template>
    </self-card>
</template>

<script setup lang="ts">
import SelfCard from "./SelfCard.vue";
import { ref, onMounted } from "vue";
import { getRandomArticleList } from "../api/article";
import { HotArticle } from "../types/article";

// Create reactive data using ref
const random_articles = ref<HotArticle[]>([]);
const loading = ref(true);

// Fetch random articles
const fetchRandomArticles = async () => {
    try {
        loading.value = true;
        const response = await getRandomArticleList();
        console.log('Random articles response:', response);
        // Check response format and extract data
        if (response && typeof response === 'object') {
            if ('data' in response) {
                // If response is wrapped in data field
                random_articles.value = Array.isArray(response.data) ? response.data : [];
            } else if (Array.isArray(response)) {
                // If response is directly an array
                random_articles.value = response;
            } else {
                random_articles.value = [];
            }
        } else {
            random_articles.value = [];
        }
        console.log('Processed random articles:', random_articles.value);
    } catch (error) {
        console.error("Failed to fetch random articles:", error);
        random_articles.value = [];
    } finally {
        loading.value = false;
    }
};

onMounted(() => {
    fetchRandomArticles();
});

function format_date(date: string): string {
    return date.split(' ')[0] || '';
}
</script>

<style scoped lang="scss">
.loading-container {
    padding: 10px;
}

.random_article_list {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.random_article_item {
    padding: 8px 12px;
    border-radius: 6px;
    background-color: var(--el-bg-color-page);
    transition: all 0.3s ease;

    &:hover {
        background-color: var(--el-color-primary-light-9);
        transform: translateX(4px);

        .random_article_title {
            color: var(--el-color-primary);
        }
    }
}

.random_article_title {
    color: var(--el-text-color-primary);
    font-size: 14px;
    text-decoration: none;
    transition: color 0.3s;
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-bottom: 6px;
}

.random_article_meta {
    display: flex;
    justify-content: space-between;
    font-size: 12px;
    color: var(--el-text-color-secondary);

    .create-time, .view-count {
        background-color: var(--el-color-info-light-9);
        padding: 2px 6px;
        border-radius: 10px;
    }
}
</style> 