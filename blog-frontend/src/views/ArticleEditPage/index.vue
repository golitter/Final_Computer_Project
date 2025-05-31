<template>
    <div class="article-edit-wrapper">
        <!-- Page header -->
        <self-header />

        <!-- Main content area -->
        <div class="article-edit-content">
            <div class="edit-card">
                <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="60px" class="edit-form">
                    <!-- Article management -->
                    <el-form-item prop="title" label="Title">
                        <el-row type="flex" justify="start" align="middle">
                            <el-col :span="20">
                                <el-input v-model="ruleForm.title" size="large" placeholder="Article title"
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
                                    {{ isSubmitting ? 'Publishing...' : 'Publish Now' }}
                                </el-button>
                                <el-button 
                                    class="el-button" 
                                    id="draft-button" 
                                    @click="submitForm(ruleFormRef, true)"
                                    :loading="isSubmitting"
                                    :disabled="isSubmitting">
                                    {{ isSubmitting ? 'Saving...' : 'Save as Draft' }}
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
                            placeholder="Please provide a brief introduction to this blog"></el-input>
                    </el-form-item>

                    <div class="inline-form-row">
                        <!-- Category -->
                        <el-form-item label="Category" prop="category" inline style="width: 35%">
                            <el-select-v2 v-model="ruleForm.category" :options="categories" placeholder="Select a category"
                                style="width: 100%; vertical-align: middle" allow-create filterable clearable />
                        </el-form-item>

                        <!-- Tags -->
                        <el-form-item label="Tags" prop="tag" inline style="width: 60%">
                            <el-select-v2 v-model="ruleForm.tags" :options="tags" placeholder="Add some tags"
                                style="width: 100%; vertical-align: middle" multiple allow-create filterable clearable />
                        </el-form-item>
                    </div>

                    <!-- Editor -->
                    <!-- https://developer.aliyun.com/article/1613629 -->
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
        <self-footer class="article-edit-footer" />
    </div>
</template>

<script lang="ts" setup>
import SelfFooter from "@/components/SelfFooter.vue"
import SelfHeader from "@/components/SelfHeader.vue"
import { ref, reactive, computed, nextTick, onMounted, toRaw } from "vue";
import { useCategoryAboutStore } from "@/stores/categoryAbout";
import { useTagAboutStore } from "@/stores/tagAbout";
import { componentSizeMap, ElMessage, ElMessageBox } from "element-plus";
import { addArticle, editArticle, getArticleDetails } from "@/api/article";
import router from "@/router";
import markdownIt from "@/utils/markdown-it-setup";

import mitt from "mitt";
import { Article } from "@/types/article";
const eventBus = mitt();

// Props
const props = defineProps<{ id?: string }>();


// State
const isInEditMode = computed(() => !!props.id);
const content = ref("");
const ruleFormRef = ref();
const uploaderRef = ref();
const title = computed(() => (isInEditMode.value ? "Edit Blog" : "New Blog"));
const categoryStore = useCategoryAboutStore();
const tagStore = useTagAboutStore();
const isSubmitting = ref(false);

// Call the method to fetch category data when component is mounted
onMounted(async () => {
    await categoryStore.fetchCategoryCounts();
});

// Get categoryCounts from store
const categoryCounts = computed(() => categoryStore.categoryCounts);
// console.log("categoryCounts", categoryCounts);
const categories = computed(() => {
    return categoryCounts.value.map((i) => ({
        value: i.name,
        label: i.name,
    }));
});

const tagCounts = computed(() => tagStore.tagCounts);
// console.log("tagCounts", tagCounts);
const tags = computed(() => {
    return tagCounts.value.map((i) => ({
        value: i.name,
        label: i.name,
    }));
});

const ruleForm = reactive({
    id: undefined,
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

        let name = isDraft ? "Draft" : "Blog";
        if (!isInEditMode.value) {
            const rawRuleForm = toRaw(ruleForm);
            const response = await addArticle({
                id: 0, // New article ID will be generated by backend
                title: rawRuleForm.title,
                content: rawRuleForm.content,
                category: rawRuleForm.category,
                summary: rawRuleForm.summary,
                tags: rawRuleForm.tags,
                thumbnail: rawRuleForm.thumbnail,
                isDraft: rawRuleForm.isDraft,
            });
            ElMessage.success(name + " saved successfully");
            // Use replace instead of push to replace current page history
            await router.replace({
                path: `/article/${response.data}`,
                replace: true
            });
            // Force page reload to ensure new article is fully loaded
            window.location.reload();
        } else {
            const id = await editArticle({
                ...ruleForm,
                id: Number(props.id)
            });
            ElMessage.success(name + " edited successfully");
            eventBus.emit("articlePosted");
            // Also use replace in edit mode
            await router.replace({
                path: `/article/${id}`,
                replace: true
            });
            // Force page reload
            window.location.reload();
        }
    } catch (error) {
        console.error("Submission failed:", error);
        ElMessage.error("Submission failed, please check your input!");
    } finally {
        isSubmitting.value = false;
    }
};

const cancelSubmit = () => {
    ElMessageBox.confirm(
        "Are you sure you want to cancel publishing this blog?",
        "Please confirm your action",
        {
            confirmButtonText: "Confirm Cancel",
            cancelButtonText: "Let me think again",
            type: "warning",
        }
    ).then(() => {
        router.push("/");
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
.article-edit-wrapper {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background-color: var(--el-bg-color-page, #f5f7fa);
    position: relative;
    padding-bottom: 60px; /* Reserve space for footer */
}

.article-edit-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    padding: 20px 0;
    min-height: calc(100vh - 120px); /* Subtract header and footer height */
    margin-top: 40px; /* Add distance from header */
}

.edit-card {
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

#submit-button:disabled,
#draft-button:disabled {
    cursor: not-allowed;
}

.article-edit-footer {
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
    .edit-card {
        width: 90%;
    }
}

@media screen and (max-width: 768px) {
    .edit-card {
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
