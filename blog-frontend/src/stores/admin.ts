import { defineStore } from 'pinia';
import { getAdminInfo } from '../api/user';
import { getArticleCount } from '../api/article';
import { getUserInfo } from '../utils/storage';
// @TODO: Due to the use of import syntax, webpack alias needs to be configured in vue.config.js
import avatar from "../assets/images/avatar.jpg"; // Using import

interface AdminInfo {
    username: string;
    signature: string;
    avatar: string;
    githubUrl: string;
    email: string;
    csdnUrl:string;
}

interface ArticleCountInfo {
    article: number;
    category: number;
    tag: number;
}

interface AdminState {
    adminInfo: AdminInfo;
    articleCountInfo: ArticleCountInfo;
    isAdmin: boolean;
}

export const useAdminStore = defineStore('admin', {
    state: (): AdminState => ({
        adminInfo: {
            username: 'golemon',
            signature: 'Talk is cheap, show me the code.',
            avatar:avatar,
            githubUrl: 'https://github.com/golitter',
            email: 'golitter@qq.com',
            csdnUrl:'https://golemon.blog.csdn.net/'
        },
        articleCountInfo: {
            article: 0,
            category: 0,
            tag: 0,
        },
        isAdmin: getUserInfo() ? getUserInfo().isAdmin : false,
    }),
    actions: {
        async fetchAdminInfo() {
            const data = await getAdminInfo();
            this.adminInfo = { ...this.adminInfo, ...data };
        },
        async fetchArticleCount() {
            const data = await getArticleCount();
            this.articleCountInfo = { ...this.articleCountInfo, ...data };
        },
        updateIsAdmin() {
            this.isAdmin = getUserInfo() ? getUserInfo().isAdmin : false;
        },
    },
});
