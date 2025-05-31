<template>
    <div class="tag-list-wrapper">
        <!-- Page header -->
        <self-header />

        <!-- Main content area -->
        <div class="tag-list-content">
            <div class="tag-list-card">
                <h2 class="tag-list-title">Tag Cloud</h2>
                <p class="tag-list-desc">Total {{ tagCounts.length }} tags</p>
                
                <!-- Loading state -->
                <el-skeleton :rows="3" animated v-if="tagStore.loading" />
                
                <template v-else>
                    <!-- Use tag cloud component -->
                    <tag-cloud 
                        v-if="tagList.length > 0"
                        :tags="tagList" 
                        @tag-click="handleTagCloud"
                    />

                    <!-- Tag list statistics -->
                    <div class="tag-statistics" v-if="tagList.length > 0">
                        <el-card v-for="tag in tagList" 
                                :key="tag.name" 
                                class="tag-stat-card"
                                @click="handleTagClick(tag.name, tag.id)">
                            <div class="tag-stat-content">
                                <span class="tag-name">{{ tag.name }}</span>
                                <span class="tag-count">{{ tag.count }} articles</span>
                            </div>
                        </el-card>
                    </div>

                    <!-- No data prompt -->
                    <el-empty v-else description="No tags found" />
                </template>
            </div>
        </div>

        <!-- Page footer -->
        <self-footer class="tag-list-footer" />
    </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useTagAboutStore } from '@/stores/tagAbout';
import { storeToRefs } from 'pinia';
import SelfHeader from '@/components/SelfHeader.vue';
import SelfFooter from '@/components/SelfFooter.vue';
import TagCloud from '@/components/TagCloud.vue';

const router = useRouter();
const tagStore = useTagAboutStore();
const { tagCounts } = storeToRefs(tagStore);

// Convert tag data to the format required by the tag cloud component
const tagList = computed(() => {
    // console.log(tagCounts.value);   
    return tagCounts.value.map(tag => ({
        name: tag.name,
        count: tag.count,
        id: tag.id
    }));
});

// Handle tag click for tag cloud
const handleTagCloud = (tagName: string) => {
    const tag = tagList.value.find(t => t.name === tagName);
    if (tag) {
        router.push(`/tag/${tagName}/${tag.id}`);
    }
};

// Handle tag click for tag card
const handleTagClick = (tagName: string, tagId: number) => {
    router.push(`/tag/${tagName}/${tagId}`);
};

// Get tag data when component is mounted
onMounted(async () => {
    await tagStore.fetchTagCounts();
});
</script>

<style lang="scss" scoped>
.tag-list-wrapper {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: var(--el-bg-color-page, #f5f7fa);
    position: relative;
    padding-bottom: 60px; /* Reserve space for footer */
}

.tag-list-content {
    flex: 1;
    padding: 40px 0;
    max-width: 1200px;
    margin: 0 auto;
    width: 100%;
}

.tag-list-card {
    background: white;
    border-radius: 8px;
    box-shadow: var(--el-box-shadow-light);
    padding: 30px;
    animation: fadeIn 0.6s ease-in-out;
}

.tag-list-title {
    font-size: 24px;
    color: var(--el-text-color-primary);
    margin: 0 0 10px 0;
    text-align: center;
}

.tag-list-desc {
    color: var(--el-text-color-secondary);
    text-align: center;
    margin-bottom: 30px;
}

.tag-statistics {
    margin-top: 40px;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 20px;
    padding-top: 20px;
    border-top: 1px solid var(--el-border-color-lighter);
}

.tag-stat-card {
    cursor: pointer;
    transition: all 0.3s ease;

    &:hover {
        transform: translateY(-3px);
        box-shadow: var(--el-box-shadow);
    }
}

.tag-stat-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8px;
}

.tag-name {
    font-size: 16px;
    color: var(--el-text-color-primary);
    font-weight: 500;
}

.tag-count {
    font-size: 14px;
    color: var(--el-text-color-secondary);
}

.tag-list-footer {
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
    .tag-list-content {
        padding: 40px 20px;
    }
}

@media screen and (max-width: 768px) {
    .tag-list-content {
        padding: 20px 10px;
    }

    .tag-list-card {
        padding: 20px;
    }

    .tag-statistics {
        grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
        gap: 10px;
    }
}
</style>