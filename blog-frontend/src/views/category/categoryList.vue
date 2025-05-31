<template>
    <div class="category-list-wrapper">
        <!-- Page header -->
        <self-header />

        <!-- Main content area -->
        <div class="category-list-content">
            <div class="category-list-card">
                <div class="category-header">
                    <h2 class="category-title">Categories</h2>
                    <p class="category-desc">Total {{ categoryCounts.length }} categories</p>
                </div>

                <!-- Loading state -->
                <div v-if="loading" class="loading-container">
                    <el-skeleton :rows="5" animated />
                </div>

                <!-- Category list -->
                <div v-else class="category-grid">
                    <el-card v-for="category in categoryCounts" 
                            :key="category.id"
                            class="category-item"
                            shadow="hover"
                            @click="handleCategoryClick(category.id)">
                        <div class="category-content">
                            <h3 class="category-name">{{ category.name }}</h3>
                            <p class="article-count">{{ category.count }} articles</p>
                        </div>
                    </el-card>

                    <!-- No data prompt -->
                    <el-empty v-if="categoryCounts.length === 0" description="No categories found" />
                </div>
            </div>
        </div>

        <!-- Page footer -->
        <self-footer class="category-list-footer" />
    </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';
import { useCategoryAboutStore } from '@/stores/categoryAbout';
import SelfHeader from '@/components/SelfHeader.vue';
import SelfFooter from '@/components/SelfFooter.vue';

const router = useRouter();
const categoryStore = useCategoryAboutStore();
const { categoryCounts, loading } = storeToRefs(categoryStore);

// Handle category click
const handleCategoryClick = (id: string) => {
    router.push(`/category/${id}`);
};

// Load data when component is mounted
onMounted(() => {
    categoryStore.fetchCategoryCounts();
});
</script>

<style lang="scss" scoped>
.category-list-wrapper {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: var(--el-bg-color-page);
    position: relative;
    padding-bottom: 60px;
}

.category-list-content {
    flex: 1;
    padding: 40px 0;
    max-width: 800px;
    margin: 0 auto;
    width: 100%;
}

.category-list-card {
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

.loading-container {
    padding: 20px;
}

.category-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
}

.category-item {
    cursor: pointer;
    transition: all 0.3s ease;

    &:hover {
        transform: translateY(-3px);
    }
}

.category-content {
    text-align: center;

    .category-name {
        font-size: 18px;
        color: var(--el-text-color-primary);
        margin: 0 0 8px 0;
    }

    .article-count {
        color: var(--el-text-color-secondary);
        font-size: 14px;
        margin: 0;
    }
}

.category-list-footer {
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
    .category-list-content {
        padding: 40px 15px;
    }
}

@media screen and (max-width: 768px) {
    .category-list-content {
        padding: 20px 10px;
    }

    .category-list-card {
        padding: 15px;
    }

    .category-title {
        font-size: 24px;
    }

    .category-grid {
        grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
        gap: 15px;
    }
}
</style>
