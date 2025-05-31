<template>
    <div id="article-details">
        <!-- Add KaTeX CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/katex@0.16.9/dist/katex.min.css">
        <!-- Add mathjax CSS -->
        <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"> -->
        
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

            <!-- Article content -->
            <div class="post_body">
                <!-- Article title -->
                <h1 class="article_title" v-if="articleLoaded">{{ articleDetails.title }}</h1>
                
                <!-- Article metadata -->
                <div id="article_desc">
                    <span>
                        <font-awesome-icon :icon="['fas', 'calendar']" />
                        {{ articleDetails.createTime?.slice(0, -3) }}
                    </span>
                    <span>
                        <font-awesome-icon :icon="['fas', 'user']" />
                        {{ adminInfo.username }}
                    </span>
                    <span>
                        <font-awesome-icon :icon="['fas', 'eye']" />
                        {{ articleDetails.viewCount }} views
                    </span>
                    <router-link v-if="isAdmin" :to="`/article/${articleDetails.id}/change`">
                        <font-awesome-icon :icon="['fas', 'edit']" />
                        Edit
                    </router-link>
                    <el-button 
                        v-if="isAdmin"
                        type="text" 
                        style="color: #f56c6c;"
                        @click="handleDelete">
                        <font-awesome-icon :icon="['fas', 'trash']" />
                        Delete
                    </el-button>
                </div>

                <el-divider />

                <!-- Article content -->
                <div class="article_content" v-html="articleDetails.content"></div>

                <!-- Copyright notice -->
                <div class="article_signature">
                    <div class="copyright">
                        <div class="copyright_item">
                            <span class="copyright_title">Author: </span>
                            <span class="copyright_content">
                                <router-link to="/">{{ adminInfo.username }}</router-link>
                            </span>
                        </div>
                        <div class="copyright_item">
                            <span class="copyright_title">Article Link: </span>
                            <span class="copyright_content">
                                <a :href="articleUrl">{{ articleUrl }}</a>
                            </span>
                        </div>
                        <div class="copyright_item">
                            <span class="copyright_title">Copyright: </span>
                            <span class="copyright_content">
                                All articles in this blog are licensed under
                                <a href="https://creativecommons.org/licenses/by-nc-nd/4.0/" target="_blank">
                                    BY-NC-SA
                                </a>
                                unless otherwise stated. Please indicate the source when reprinting!
                            </span>
                        </div>
                    </div>
                </div>

                <!-- Article tags -->
                <div class="article_tags" v-if="articleDetails.tags?.length">
                    <div class="tags-wrapper">
                        <span class="tags-label">
                            <font-awesome-icon :icon="['fas', 'tags']" />
                            Tags:
                        </span>
                        <div class="tags-list">
                            <el-tag 
                                v-for="tag in articleDetails.tags" 
                                :key="tag.id" 
                                class="article-tag"
                                effect="light"
                            >
                                <router-link :to="'/tag/' + tag.id">{{ tag.name }}</router-link>
                            </el-tag>
                        </div>
                    </div>
                </div>

                <!-- Previous and next articles -->
                <div class="article-navigation">
                    <div class="nav-item prev-article" v-if="previousArticle.id"
                        :class="{ 'full-width': !nextArticle.id }">
                        <router-link :to="`/article/${previousArticle.id}`">
                            <div class="nav-content">
                                <div class="nav-label">
                                    <font-awesome-icon :icon="['fas', 'angle-left']" />
                                    Previous
                                </div>
                                <div class="nav-title">{{ previousArticle.title }}</div>
                            </div>
                        </router-link>
                    </div>

                    <div class="nav-item next-article" v-if="nextArticle.id"
                        :class="{ 'full-width': !previousArticle.id }">
                        <router-link :to="`/article/${nextArticle.id}`">
                            <div class="nav-content">
                                <div class="nav-label">
                                    Next
                                    <font-awesome-icon :icon="['fas', 'angle-right']" />
                                </div>
                                <div class="nav-title">{{ nextArticle.title }}</div>
                            </div>
                        </router-link>
                    </div>
                </div>
            </div>
        </div>

        <!-- Back to top -->
        <self-to-top />
        
        <!-- Page footer -->
        <self-footer />
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, nextTick, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import markdownIt from "@/utils/markdown-it-setup";
import buildCodeBlock from "@/utils/code-block";
import { getArticleDetails, getPreviousNextArticle, updateViewCount, deleteArticle } from "@/api/article";
import SelfHeader from '@/components/SelfHeader.vue';
import AdminCard from '@/components/AdminCard.vue';
import RandomArticleCard from '@/components/RandomArticleCard.vue';
import CategoryCard from '@/components/CategoryCard.vue';
import TagCard from '@/components/TagCard.vue';
import SelfToTop from "@/components/SelfToTop.vue";
import SelfFooter from "@/components/SelfFooter.vue";
import { useAdminStore } from '@/stores/admin';
import { Article, ArticleDetails, ArticleMeta } from "@/types/article";
import { storeToRefs } from 'pinia';

// Route related
const route = useRoute();
const router = useRouter();
const articleId = ref<number>(Number(route.params.id));

// Store
const adminStore = useAdminStore();
const { adminInfo, isAdmin } = storeToRefs(adminStore);

// Article data
const articleLoaded = ref(false);
const articleUrl = ref(window.location.href);
const articleDetails = reactive<ArticleDetails>({ createTime: "" });
const previousArticle = reactive<Partial<ArticleMeta>>({ id: 0, title: '' });
const nextArticle = reactive<Partial<ArticleMeta>>({ id: 0, title: '' });

// Get article details
const fetchArticleDetails = async () => {
    try {
        const data = await getArticleDetails(articleId.value);
        Object.assign(articleDetails, data);

        articleDetails.content = markdownIt.render(data.content);
        // console.log("articleDetails.content", articleDetails.content);
        nextTick(() => {
            buildCodeBlock(".article_content");
            articleLoaded.value = true;
            // Update current page URL
            articleUrl.value = window.location.href;
            // Scroll to top of page
            window.scrollTo(0, 0);
        });
    } catch (error) {
        ElMessage.error("Failed to load article");
    }
};

// Get previous and next articles
const fetchPreviousNextArticle = async () => {
    try {
        const { data } = await getPreviousNextArticle(articleId.value);
        if (data.previous) Object.assign(previousArticle, data.previous);
        if (data.next) Object.assign(nextArticle, data.next);
    } catch (error) {
        ElMessage.error("Failed to get adjacent articles");
    }
};

// Update view count
const updateView = () => {
    updateViewCount(articleId.value);
};

// Watch route parameter changes
watch(
    () => route.params.id,
    async (newId) => {
        if (newId) {
            articleId.value = Number(newId);
            articleLoaded.value = false;
            await fetchArticleDetails();
            await fetchPreviousNextArticle();
            updateView();
        }
    }
);

// Delete article
const handleDelete = () => {
    ElMessageBox.confirm('Are you sure you want to delete this article?', 'Warning', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
    }).then(async () => {
        try {
            await deleteArticle(articleId.value);
            ElMessage.success('Delete successful');
            router.push('/');
        } catch (error) {
            ElMessage.error('Delete failed');
        }
    });
};

// Initialize
onMounted(() => {
    fetchArticleDetails();
    fetchPreviousNextArticle();
    updateView();
});
</script>

<style lang="scss" scoped>
@use './index.scss' as *;
</style>