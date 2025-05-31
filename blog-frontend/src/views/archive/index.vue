<template>
    <div class="archive-wrapper">
        <!-- Page header -->
        <self-header />

        <!-- Main content area -->
        <div class="archive-content">
            <div class="archive-card">
                <h2 class="archive-title">Article Archive</h2>
                <p class="archive-desc">Total {{ totalArticles }} articles</p>

                <!-- Loading state -->
                <el-skeleton :rows="6" animated v-if="loading" />

                <template v-else>
                    <!-- Timeline -->
                    <el-timeline v-if="archiveList.length > 0">
                        <el-timeline-item
                            v-for="year in archiveList"
                            :key="year.year"
                            :timestamp="year.year"
                            placement="top"
                            :type="year.articles.length > 5 ? 'primary' : 'info'"
                        >
                            <el-card class="year-card">
                                <h3 class="year-title">{{ year.year }} ({{ year.articles.length }} articles)</h3>
                                <div class="article-list">
                                    <div v-for="article in year.articles"
                                         :key="article.id"
                                         class="article-item"
                                         @click="handleArticleClick(article.id)">
                                        <div class="article-date">{{ formatDate(article.createTime) }}</div>
                                        <div class="article-title">{{ article.title }}</div>
                                    </div>
                                </div>
                            </el-card>
                        </el-timeline-item>
                    </el-timeline>

                    <!-- No data prompt -->
                    <el-empty v-else description="No articles found" />
                </template>
            </div>
        </div>

        <!-- Page footer -->
        <self-footer class="archive-footer" />
    </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import SelfHeader from '@/components/SelfHeader.vue';
import SelfFooter from '@/components/SelfFooter.vue';
import { getPostArticleList } from '@/api/article';
import type { Article as ArticleType } from '@/types/article';

// Extend article type to ensure required fields
interface Article {
    id: number;
    title: string;
    createTime: string;
    content?: string;
    category?: string;
    summary?: string;
    tags?: string[];
    thumbnail?: string;
    isDraft?: boolean;
    viewCount?: number;
}

interface YearGroup {
    year: string;
    articles: Article[];
}

const router = useRouter();
const loading = ref(false);
const articles = ref<Article[]>([]);
const currentPage = ref(1);
const pageSize = ref(50); // Archive page can load more articles at once

// Format date
const formatDate = (dateStr: string) => {
    const date = new Date(dateStr);
    return `${date.getMonth() + 1}/${date.getDate()}`;
};

// Articles grouped by year
const archiveList = computed<YearGroup[]>(() => {
    const groups: { [key: string]: Article[] } = {};
    
    articles.value.forEach(article => {
        const year = new Date(article.createTime).getFullYear().toString();
        if (!groups[year]) {
            groups[year] = [];
        }
        groups[year].push(article);
    });

    return Object.entries(groups)
        .map(([year, articles]) => ({
            year,
            articles: articles.sort((a, b) => 
                new Date(b.createTime).getTime() - new Date(a.createTime).getTime()
            )
        }))
        .sort((a, b) => Number(b.year) - Number(a.year));
});

// Total number of articles
const totalArticles = computed(() => articles.value.length);

// Handle article click
const handleArticleClick = (articleId: number) => {
    router.push(`/article/${articleId}`);
};

// Get article list
const fetchArticles = async () => {
    loading.value = true;
    try {
        const response = await getPostArticleList(
            currentPage.value,
            pageSize.value
        );
        if (response.status === 200 && response.data?.rows) {
            // Ensure data matches Article interface requirements
            articles.value = response.data.rows
                .filter(article => article.createTime) // Only keep articles with creation time
                .map(article => ({
                    id: article.id,
                    title: article.title,
                    createTime: article.createTime || '', // Provide default value
                    content: article.content,
                    category: article.category,
                    summary: article.summary,
                    tags: article.tags,
                    thumbnail: article.thumbnail,
                    isDraft: article.isDraft,
                    viewCount: article.viewCount
                }));
        } else {
            ElMessage.error(response.message || 'Failed to get article list');
        }
    } catch (error) {
        console.error('Failed to fetch articles:', error);
        ElMessage.error('Failed to get article list');
    } finally {
        loading.value = false;
    }
};

// Get article list when component is mounted
onMounted(() => {
    fetchArticles();
});
</script>

<style lang="scss" scoped>
.archive-wrapper {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: var(--el-bg-color-page, #f5f7fa);
    position: relative;
    padding-bottom: 60px;
}

.archive-content {
    flex: 1;
    padding: 40px 0;
    max-width: 800px;
    margin: 0 auto;
    width: 100%;
}

.archive-card {
    background: white;
    border-radius: 8px;
    box-shadow: var(--el-box-shadow-light);
    padding: 25px;
    animation: fadeIn 0.6s ease-in-out;
}

.archive-title {
    font-size: 24px;
    color: var(--el-text-color-primary);
    margin: 0 0 10px 0;
    text-align: center;
}

.archive-desc {
    color: var(--el-text-color-secondary);
    text-align: center;
    margin-bottom: 30px;
}

.year-card {
    margin-bottom: 20px;
}

.year-title {
    font-size: 18px;
    color: var(--el-text-color-primary);
    margin: 0 0 15px 0;
}

.article-list {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.article-item {
    display: flex;
    align-items: center;
    padding: 8px;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s ease;

    &:hover {
        background-color: var(--el-color-primary-light-9);
        transform: translateX(5px);
    }
}

.article-date {
    min-width: 80px;
    color: var(--el-text-color-secondary);
    font-size: 14px;
}

.article-title {
    flex: 1;
    color: var(--el-text-color-primary);
    font-size: 15px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.archive-footer {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    width: 100%;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

// Responsive design
@media screen and (max-width: 1200px) {
    .archive-content {
        padding: 40px 15px;
    }
}

@media screen and (max-width: 768px) {
    .archive-content {
        padding: 20px 10px;
    }

    .archive-card {
        padding: 15px;
    }

    .article-date {
        min-width: 70px;
        font-size: 13px;
    }

    .article-title {
        font-size: 14px;
    }
}
</style>
