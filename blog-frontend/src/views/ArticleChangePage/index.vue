<template>
    <div class="article-change-wrapper">
        <!-- Page header -->
        <self-header />

        <!-- Main content area -->
        <div class="article-change-content">
            <div class="change-card">
                <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="60px" class="change-form">
                    <!-- Article Management -->
                    <el-form-item prop="title" label="Title">
                        <el-row type="flex" justify="start" align="middle">
                            <el-col :span="20">
                                <el-input v-model="ruleForm.title" size="large" placeholder="Edit article title"
                                    style="width: 100%;"></el-input>
                            </el-col>

                            <!-- Buttons -->
                            <el-col :span="4" style="display: flex; justify-content: flex-start;">
                                <el-button 
                                    type="primary" 
                                    @click="submitForm(ruleFormRef, false)" 
                                    color="#1892ff"
                                    class="el-button" 
                                    id="submit-button"
                                    :loading="isSubmitting"
                                    :disabled="isSubmitting">
                                    {{ isSubmitting ? 'Updating...' : 'Save Changes' }}
                                </el-button>
                                <el-button @click="cancelSubmit" :disabled="isSubmitting">
                                    Cancel
                                </el-button>
                            </el-col>
                        </el-row>
                    </el-form-item>

                    <!-- Summary -->
                    <el-form-item prop="summary" label="Summary">
                        <el-input v-model="ruleForm.summary" class="w-full m-3" size="large" type="textarea"
                            placeholder="Edit article summary"></el-input>
                    </el-form-item>

                    <div class="inline-form-row">
                        <!-- Category -->
                        <el-form-item label="Category" prop="category" inline style="width: 35%">
                            <el-select-v2 v-model="ruleForm.category" :options="categories" placeholder="Edit article category"
                                style="width: 100%; vertical-align: middle" allow-create filterable clearable />
                        </el-form-item>

                        <!-- Tags -->
                        <el-form-item label="Tags" prop="tag" inline style="width: 60%">
                            <el-select-v2 v-model="ruleForm.tags" :options="tags" placeholder="Edit article tags"
                                style="width: 100%; vertical-align: middle" multiple allow-create filterable clearable />
                        </el-form-item>
                    </div>

                    <!-- Editor -->
                    <el-form-item prop="content" label="Content">
                        <v-md-editor 
                            v-model="ruleForm.content" 
                            :disabled-menus="['image']"
                            height="500px"
                        />
                    </el-form-item>
                </el-form>
            </div>
        </div>

        <!-- Page footer -->
        <self-footer class="article-change-footer" />
    </div>
</template>

<script lang="ts" setup>
import SelfFooter from "@/components/SelfFooter.vue"
import SelfHeader from "@/components/SelfHeader.vue"
import { ref, reactive, computed, nextTick, onMounted, toRaw } from "vue";
import { useCategoryAboutStore } from "@/stores/categoryAbout";
import { useTagAboutStore } from "@/stores/tagAbout";
import { ElMessage, ElMessageBox } from "element-plus";
import { editArticle, getArticleDetails } from "@/api/article";
import router from "@/router";
import markdownIt from "@/utils/markdown-it-setup";

import mitt from "mitt";
import { Article } from "@/types/article";
const eventBus = mitt();

// Props
const props = defineProps<{ id: string }>();

interface ArticleForm {
    id: number;
    title: string;
    summary: string;
    content: string;
    category: string;
    tags: string[];
    thumbnail?: string;
    isDraft: boolean;
}

// State
const content = ref("");
const ruleFormRef = ref();
const categoryStore = useCategoryAboutStore();
const tagStore = useTagAboutStore();
const isSubmitting = ref(false);

// Call the method to fetch category data when component is mounted
onMounted(async () => {
    await categoryStore.fetchCategoryCounts();
    
    // Get article details
    try {
        const article = await getArticleDetails(Number(props.id));
        // Fill form data
        console.log("article.category", article.categoryId);
        const tag_names = article.tags.map(tag => tag.name);
        ruleForm.id = article.id;
        ruleForm.title = article.title;
        ruleForm.summary = article.summary;
        ruleForm.content = article.content;
        ruleForm.category = article.categoryName;
        ruleForm.tags = tag_names || [];
        ruleForm.thumbnail = article.thumbnail;
        ruleForm.isDraft = article.isDraft;
    } catch (error) {
        console.error("Failed to get article details:", error);
        ElMessage.error("Failed to get article details, please refresh the page and try again");
    }
});

// Get categoryCounts from store
const categoryCounts = computed(() => categoryStore.categoryCounts);
const categories = computed(() => {
    return categoryCounts.value.map((i) => ({
        value: i.name,
        label: i.name,
    }));
});
const tagCounts = computed(() => tagStore.tagCounts);
const tags = computed(() => {
    return tagCounts.value.map((i) => ({
        value: i.name,
        label: i.name,
    }));
});

const ruleForm = reactive<ArticleForm>({
    id: Number(props.id),
    title: "",
    summary: "",
    content: "",
    category: "",
    tags: [],
    thumbnail: "",
    isDraft: false,
});

const rules = reactive({
    title: [
        {
            required: true,
            message: "Article title cannot be empty",
            trigger: "change",
        },
    ],
    content: [
        {
            required: true,
            message: "Article content cannot be empty",
            trigger: "change",
        },
    ],
    category: [
        {
            required: true,
            message: "Category cannot be empty",
            trigger: "change",
        },
    ],
});

const submitForm = async (form: any, isDraft: boolean) => {
    if (!form || isSubmitting.value) return;
    
    // First validate the form
    let valid = false;
    try {
        valid = await form.validate();
    } catch (error) {
        ElMessage.error("Required fields cannot be empty");
        return;
    }

    if (!valid) {
        ElMessage.error("Required fields cannot be empty");
        return;
    }

    try {
        isSubmitting.value = true;
        ruleForm.isDraft = isDraft;
        generateSummary();

        const id = await editArticle({
            ...ruleForm,
            id: Number(props.id)
        });
        const article_id = Number(id.data);
        console.log("id", id);  
        ElMessage.success("Article updated successfully");
        eventBus.emit("articlePosted");
        // Use replace instead of push to replace current page history
        await router.replace({
            path: `/article/${article_id}`,
            replace: true
        });
        // Force page reload
        window.location.reload();
    } catch (error) {
        console.error("Failed to update article:", error);
        ElMessage.error("Update failed, please check your input!");
    } finally {
        isSubmitting.value = false;
    }
};

const cancelSubmit = () => {
    ElMessageBox.confirm(
        "Are you sure you want to cancel editing this blog?",
        "Please confirm your action",
        {
            confirmButtonText: "Confirm Cancel",
            cancelButtonText: "Let me think again",
            type: "warning",
        }
    ).then(() => {
        router.push(`/article/${props.id}`);
    });
};

const validateForm = (form: any) => {
    if (!form) return false;

    form.validate((valid: boolean) => {
        if (!valid) {
            ElMessage.error("Required fields cannot be empty");
            return false;
        }
    });

    return true;
};

const generateSummary = () => {
    if (ruleForm.summary) {
        return;
    }

    let html = markdownIt.render(ruleForm.content);
    ruleForm.summary = html.replace(/<[^>]+>/g, "").slice(0, 150);
};

// Scroll to top after content update
nextTick(() => {
    window.scrollTo({ top: 0 });
});

</script>

<style lang="scss" scoped>
.article-change-wrapper {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: var(--el-bg-color-page, #f5f7fa);
    position: relative;
    padding-bottom: 60px; /* Reserve space for footer */
}

.article-change-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    padding: 20px 0;
    min-height: calc(100vh - 120px); /* Subtract header and footer height */
    margin-top: 40px; /* Add distance from header */
}

.change-card {
    background: white;
    border-radius: 8px;
    box-shadow: var(--el-box-shadow-light);
    padding: 20px 30px;
    width: 85%;
    margin: 0 auto;
    box-sizing: border-box;
    animation: fadeInUp 1s;

    .inline-form-row {
        display: flex;
        justify-content: space-between;
        gap: 20px;
    }

    .el-button {
        transition: all 0.4s;
    }
}

#submit-button:disabled {
    cursor: not-allowed;
}

.article-change-footer {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    width: 100%;
}

@keyframes fadeInUp {
    from {
        transform: translateY(50px);
        opacity: 0;
    }

    to {
        transform: translateY(0);
        opacity: 1;
    }
}

// Responsive design
@media screen and (max-width: 1200px) {
    .change-card {
        width: 90%;
    }
}

@media screen and (max-width: 768px) {
    .change-card {
        width: 95%;
        padding: 15px;

        .inline-form-row {
            flex-direction: column;
            gap: 0;
        }

        :deep(.el-form-item) {
            width: 100% !important;
        }
    }
}
</style>
