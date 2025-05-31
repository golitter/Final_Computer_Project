<template>
    <div class="admin_card">
        <!-- TODO Need to set global configuration -->
        <el-avatar :size="50" :src="adminInfo.avatar" class="avatar"></el-avatar>
        <div class="admin_description">
            <span class="name">{{ adminInfo.username }}</span>
            <p>Shaanxi University of Science and Technology</p>
            <p>
                <font-awesome-icon :icon="['fas', 'fa-location-dot']" class="social_icon" id="csdn" /> Xi'an, China
            </p>
            <p>{{ adminInfo.signature }}</p>
        </div>

        <!-- Article Information -->
        <div class="article_info_container">
            <div class="article_info">
                <p>POSTS</p>
                <p>{{ articleCountInfo.article }}</p>
            </div>
            <div class="article_info">
                <p>CATEGORIES</p>
                <p>{{ articleCountInfo.category }}</p>
            </div>
            <div class="article_info">
                <p>TAGS</p>
                <p>{{ articleCountInfo.tag }}</p>
            </div>
        </div>

        <!-- Follow on GitHub -->
        <el-button type="primary" round class="follow_button" @click="goToGithubProfile">Follow</el-button>

        <!-- Social Media Icons -->
        <div class="social_icon_container">
            <!-- CSDN -->
            <a :href="adminInfo.csdnUrl">
                <font-awesome-icon :icon="['fas', 'c']" class="social_icon" id="csdn" />
            </a>

            <!-- GitHub -->
            <a :href="adminInfo.githubUrl">
                <font-awesome-icon :icon="['fab', 'github']" class="social_icon" id="github" />
            </a>

            <!-- QQ -->
            <font-awesome-icon :icon="['fab', 'qq']" class="social_icon" id="qq" />

            <!-- Email -->
            <a :href="'mailto:' + adminInfo.email">
                <font-awesome-icon :icon="['fas', 'envelope']" class="social_icon" id="email" />
            </a>
        </div>
    </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useAdminStore } from "../stores/admin";
import request from '@/utils/request';

const store = useAdminStore();

// Computed properties
const adminInfo = computed(() => store.$state.adminInfo);
const articleCountInfo = computed(() => store.$state.articleCountInfo);

// Get article count information
const getCountInfo = async () => {
    try {
        const response = await request({
            url: '/article/count',
            method: 'get'
        });
        
        if (response.data) {
            // Update count information in store
            store.$state.articleCountInfo = {
                article: response.data.articleCount,
                category: response.data.categoryCount,
                tag: response.data.tagCount
            };
        }
    } catch (error) {
        console.error('Failed to get count information:', error);
    }
};

// Function to navigate to GitHub profile
const goToGithubProfile = () => {
    window.location.href = adminInfo.value.githubUrl;
};

// Get count information when component is mounted
onMounted(() => {
    getCountInfo();
});
</script>

<style scoped lang="scss">
@use '@/assets/css/index.scss' as *; // Import variable file

.admin_card {
    // background: wheat;
    border-radius: 8px;
    box-shadow: var(--card-box-shadow);
    text-align: center;
    height: 400px;
    width: 100%;
    padding: 15px;
    box-sizing: border-box;
    box-shadow: $box-shadow;

    .avatar {
        width: 120px;
        height: 120px;
        transition: transform 0.5s ease-out;

        &:hover {
            animation: shake 0.5s;
        }
    }
    .name {
        height: 25px;
        font-size: 25px;
        font-weight: bold;
        color: black;
        line-height: 25px;  // 使文本垂直居中
    }
    .admin_description {
        p {
            margin: 4px;
            font-size: 14px;
            color: #555;
            overflow: hidden;
            display: -webkit-box;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 1;
        }
    }

    .article_info_container {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0% 8%;
        margin-bottom: 10px;

        .article_info {
            margin: 0 5%;

            p:nth-child(1) {
                margin-bottom: 0;
            }

            p:nth-child(2) {
                margin-top: 3px;
            }
        }
    }

    .social_icon_container {
        margin-top: 15px;

        .social_icon {
            margin: 0px 3%;
            font-size: 20px;
            color: #555;
            transition: all 0.3s;
            cursor: pointer;
            transform: translateY(0);

            &:hover {
                transform: translateY(-5px);
            }
        }

        #csdn:hover {
            color: orangered;
        }

        #github:hover {
            color: black;
        }

        #qq:hover {
            color: blue;
        }

        #email:hover {
            color: whitesmoke;
        }
    }

    .follow_button {
        display: block;
        margin: 0 auto;
        width: 100%;
    }

    .github_icon {
        margin-right: 10px;
    }
}
</style>
