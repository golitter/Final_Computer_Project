<template>
    <div id="home">
        <!-- Page header -->
        <self-header />

        <div class="container">
            <!-- Sidebar -->
            <div class="side_content">
                <admin-card />
                <random-article-card />
                <category-card />
                <tag-card />
            </div>

            <!-- Published articles -->
            <div class="post_article_list">
                <post-article-card 
                    v-for="(article, index) in post_articles" 
                    :key="article.id" 
                    :article="article" 
                />

                <!-- Pagination -->
                <el-pagination
                    v-if="article_total > 0"
                    layout="prev, pager, next"
                    :total="article_total"
                    :page-size="page_size"
                    :current-page="current_page"
                    class="pagination"
                    @current-change="on_current_page_changed"
                />
            </div>
        </div>

        <!-- Page footer -->
        <self-footer />

        <!-- Scroll to top button -->
        <self-to-top />
    </div>
</template>

<script setup lang="ts">
import SelfHeader from '@/components/SelfHeader.vue';
import AdminCard from '@/components/AdminCard.vue';
import RandomArticleCard from '@/components/RandomArticleCard.vue';
import CategoryCard from '@/components/CategoryCard.vue';
import TagCard from '@/components/TagCard.vue';
import PostArticleCard from '@/components/PostArticleCard.vue';
import SelfToTop from '@/components/SelfToTop.vue';
import { ElPagination } from 'element-plus';
import { reactive, ref, onMounted } from 'vue';
import { getPostArticleList } from '@/api/article';
import { ArticleResponse } from '@/types/article';
import { useAdminStore } from '@/stores/admin';
import SelfFooter from '@/components/SelfFooter.vue';
const page_size = 10;
const post_articles = reactive<ArticleResponse['data']['rows']>([]); // TODO embedded data
const article_total = ref(0);
const useStore = useAdminStore();
const current_page = ref(1); // Current page
const admin_name = useStore.$state.adminInfo.username;

// Get article list when component is mounted
onMounted(() => {
    on_current_page_changed(current_page.value);
});
function on_current_page_changed(page_num: number) {
    getPostArticleList(page_num, page_size).then((data: ArticleResponse) => {
        article_total.value = Number(data.data.total);
        console.log('test', article_total.value);

        // Update current page
        current_page.value = page_num;
        data.data.rows.forEach((article) => {
            if (article.createTime) {
                article.createTime = article.createTime.split(' ')[0];
            }
        });

        // Reset list content
        post_articles.splice(0, post_articles.length, ...data.data.rows);
        // Scroll to top
        window.scrollTo(0, 0);

    });
}
</script>

<style lang="scss" scoped>
.container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
    display: flex;
    gap: 24px;
    min-height: calc(100vh - 60px - 80px); // Subtract header and footer height
    animation: fadeInUp 0.6s ease-out;
}

.side_content {
    width: 300px;
    flex-shrink: 0;
    display: flex;
    flex-direction: column;
    gap: 24px;
    position: sticky;
    top: 80px;
    height: fit-content;
}

.post_article_list {
    flex: 1;
    min-width: 0;
    display: flex;
    flex-direction: column;
    gap: 24px;
}

.pagination {
    margin-top: auto;
    padding: 24px 0;
    display: flex;
    justify-content: center;

    :deep(.el-pager) {
        li {
            margin: 0 4px;
            border-radius: 4px;
            transition: all 0.3s ease;
            background-color: var(--el-bg-color);
            border: 1px solid var(--el-border-color-lighter);
            
            &.is-active {
                background-color: var(--el-color-primary);
                color: white;
                border-color: var(--el-color-primary);
            }

            &:hover:not(.is-active) {
                color: var(--el-color-primary);
                border-color: var(--el-color-primary);
            }
        }
    }

    :deep(.btn-prev),
    :deep(.btn-next) {
        margin: 0 4px;
        border-radius: 4px;
        background-color: var(--el-bg-color);
        border: 1px solid var(--el-border-color-lighter);
        transition: all 0.3s ease;

        &:hover {
            color: var(--el-color-primary);
            border-color: var(--el-color-primary);
        }

        &.is-disabled {
            color: var(--el-text-color-disabled);
            border-color: var(--el-border-color-lighter);
            cursor: not-allowed;
        }
    }
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@media screen and (max-width: 1200px) {
    .container {
        max-width: 100%;
        padding: 24px 16px;
    }
}

@media screen and (max-width: 900px) {
    .container {
        flex-direction: column;
        gap: 16px;
    }

    .side_content {
        width: 100%;
        position: static;
        order: 2;
    }

    .post_article_list {
        order: 1;
        gap: 16px;
    }

    .pagination {
        padding: 16px 0;
    }
}
</style>
