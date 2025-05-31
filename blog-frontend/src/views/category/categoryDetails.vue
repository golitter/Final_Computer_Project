<template>
    <div class="category-details-wrapper">
        <!-- Page header -->
        <self-header />

        <!-- Main content area -->
        <div class="category-details-content">
            <div class="category-details-card">
                <div class="category-header">
                    <h2 class="category-title">Category: {{ name }}</h2>
                    <p class="category-desc">Total {{ total }} articles</p>
                </div>

                <!-- Article list -->
                <div class="article-list">
                    <el-card v-for="article in articles" 
                            :key="article.id" 
                            class="article-card"
                            shadow="hover"
                            @click="handleArticleClick(article.id)">
                        <div class="article-content">
                            <h3 class="article-title">{{ article.title }}</h3>
                            <p class="article-summary">{{ article.summary }}</p>
                            <div class="article-meta">
                                <span class="create-time">
                                    <el-icon><Calendar /></el-icon>
                                    {{ formatDate(article.createTime || '') }}
                                </span>
                                <span class="view-count">
                                    <el-icon><View /></el-icon>
                                    {{ article.viewCount || 0 }} views
                                </span>
                            </div>
                        </div>
                    </el-card>

                    <!-- Pagination -->
                    <div class="pagination-container" v-if="total > 0">
                        <el-pagination
                            v-model:current-page="currentPage"
                            v-model:page-size="pageSize"
                            :total="total"
                            :page-sizes="[10, 20, 30, 50]"
                            layout="total, sizes, prev, pager, next, jumper"
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                        />
                    </div>

                    <!-- No data prompt -->
                    <el-empty v-if="total === 0" description="No articles found" />
                </div>
            </div>
        </div>

        <!-- Page footer -->
        <self-footer class="category-details-footer" />
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { Calendar, View } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import { getPostArticleList } from '@/api/article';
import type { Article } from '@/types/article';
import { formatDate } from '@/utils/date';
import SelfHeader from '@/components/SelfHeader.vue';
import SelfFooter from '@/components/SelfFooter.vue';

// Receive route parameters
const props = defineProps<{
    name: string
}>();

const router = useRouter();
const articles = ref<Article[]>([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

// Load article list
const loadArticles = async () => {
    try {
        const response = await getPostArticleList(
            currentPage.value,
            pageSize.value,
            Number(props.name) // Category ID
        );

        if (response.status === 200 && response.data) {
            articles.value = response.data.rows;
            total.value = response.data.total;
        } else {
            ElMessage.error(response.message || 'Failed to get article list');
        }
    } catch (error) {
        console.error('Failed to load articles:', error);
        ElMessage.error('Failed to get article list');
    }
};

// Handle article click
const handleArticleClick = (id: number) => {
    router.push(`/article/${id}`);
};

// Handle page size change
const handleSizeChange = (val: number) => {
    pageSize.value = val;
    currentPage.value = 1;
    loadArticles();
};

// Handle page number change
const handleCurrentChange = (val: number) => {
    currentPage.value = val;
    loadArticles();
};

// Load data when component is mounted
onMounted(() => {
    loadArticles();
});
</script>

<style lang="scss" scoped>
.category-details-wrapper {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: var(--el-bg-color-page);
    position: relative;
    padding-bottom: 60px;
}

.category-details-content {
    flex: 1;
    padding: 40px 0;
    max-width: 800px;
    margin: 0 auto;
    width: 100%;
}

.category-details-card {
    background: white;
    border-radius: 8px;
    box-shadow: var(--el-box-shadow-light);
    padding: 25px;
    animation: fadeIn 0.6s ease-in-out;
}

.category-header {
    text-align: center;
    margin-bottom: 40px;
    padding-bottom: 20px;
    border-bottom: 1px solid var(--el-border-color-lighter);
}

.category-title {
    font-size: 28px;
    color: var(--el-text-color-primary);
    margin: 0 0 10px 0;
}

.category-desc {
    color: var(--el-text-color-secondary);
    font-size: 16px;
}

.article-list {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.article-card {
    cursor: pointer;
    transition: all 0.3s ease;

    &:hover {
        transform: translateY(-3px);
    }
}

.article-content {
    .article-title {
        font-size: 20px;
        color: var(--el-text-color-primary);
        margin: 0 0 12px 0;
        line-height: 1.4;
    }

    .article-summary {
        color: var(--el-text-color-secondary);
        font-size: 14px;
        margin: 0 0 16px 0;
        line-height: 1.6;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }

    .article-meta {
        display: flex;
        gap: 20px;
        color: var(--el-text-color-secondary);
        font-size: 14px;

        span {
            display: flex;
            align-items: center;
            gap: 4px;
        }
    }
}

.pagination-container {
    margin-top: 30px;
    display: flex;
    justify-content: center;
}

.category-details-footer {
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
    .category-details-content {
        padding: 40px 15px;
    }
}

@media screen and (max-width: 768px) {
    .category-details-content {
        padding: 20px 10px;
    }

    .category-details-card {
        padding: 15px;
    }

    .category-title {
        font-size: 24px;
    }

    .article-content {
        .article-title {
            font-size: 18px;
        }
    }
}
</style>
