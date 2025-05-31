<template>
    <div class="post_article_card">
        <div class="post_article_info">
            <!-- Article Title -->
            <router-link :to="`/article/${article.id}`" class="post_article_title">
                {{ article.title }}
            </router-link>

            <!-- Article Summary -->
            <div class="post_article_summary">
                {{ article.summary }}
            </div>

            <!-- Other Metadata -->
            <div class="post_article_meta">
                <div class="meta_item">
                    <font-awesome-icon :icon="['fas', 'calendar-days']" />
                    <span>{{ article.createTime }}</span>
                </div>
                <div class="meta_item">
                    <font-awesome-icon :icon="['fas', 'folder']" />
                    <span>{{ article.categoryName }}</span>
                </div>
                <div class="meta_item">
                    <font-awesome-icon :icon="['fas', 'eye']" />
                    <span>{{ article.viewCount }} views</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps } from "vue";

// Define component props
const props = defineProps<{
    article: {
        id: number;
        title: string;
        createTime?: string;
        categoryName?: string;
        viewCount?: number;
        summary: string;
    };
}>();
</script>

<style lang="scss" scoped>
.post_article_card {
    background: var(--el-bg-color);
    border-radius: 12px;
    box-shadow: var(--el-box-shadow-lighter);
    transition: all 0.3s ease;
    overflow: hidden;
    padding: 24px;

    &:hover {
        transform: translateY(-2px);
        box-shadow: var(--el-box-shadow-light);

        .post_article_title {
            color: var(--el-color-primary);
        }
    }
}

.post_article_info {
    display: flex;
    flex-direction: column;
    gap: 16px;
}

.post_article_title {
    font-size: 1.5rem;
    font-weight: 600;
    color: var(--el-text-color-primary);
    text-decoration: none;
    line-height: 1.4;
    transition: color 0.3s ease;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.post_article_summary {
    color: var(--el-text-color-regular);
    font-size: 1rem;
    line-height: 1.6;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    margin: 8px 0;
}

.post_article_meta {
    display: flex;
    flex-wrap: wrap;
    gap: 16px;
    color: var(--el-text-color-secondary);
    font-size: 0.875rem;

    .meta_item {
        display: flex;
        align-items: center;
        gap: 6px;
        transition: color 0.3s ease;

        &:hover {
            color: var(--el-color-primary);
        }

        .svg-inline--fa {
            font-size: 0.875rem;
        }
    }
}

@media screen and (max-width: 768px) {
    .post_article_card {
        padding: 16px;
    }

    .post_article_title {
        font-size: 1.25rem;
    }

    .post_article_summary {
        font-size: 0.875rem;
        margin: 4px 0;
    }

    .post_article_meta {
        gap: 12px;
        font-size: 0.75rem;

        .meta_item {
            gap: 4px;

            .svg-inline--fa {
                font-size: 0.75rem;
            }
        }
    }
}
</style>
