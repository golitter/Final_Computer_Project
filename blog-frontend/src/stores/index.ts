import { defineStore } from 'pinia';
import { getAdminInfo } from '../api/user';
import { getArticleCount } from '../api/article';
import { getCategoryCountList } from '../api/category';
import { getTagCountList } from '../api/tag';
import { getUserInfo } from "../utils/storage";

// Define adminAbout store
export const useAdminStore = defineStore('adminAbout', {
  state: () => ({
    adminInfo: {
      nickName: '',
      signature: '',
      githubUrl: 'https://github.com/golitter',
    },
    articleCountInfo: {
      article: 0,
      category: 0,
      tag: 0,
    },
    isAdmin: getUserInfo() ? getUserInfo().isAdmin : false,
  }),
  actions: {
    async getAdminInfo() {
      const data = await getAdminInfo();
      this.updateAdminInfo(data);
      return data;
    },
    async getArticleCount() {
      const data = await getArticleCount();
      this.updateArticleCountInfo(data);
      return data;
    },
    updateAdminInfo(adminInfo: any) {
      this.adminInfo = { ...this.adminInfo, ...adminInfo };
    },
    updateArticleCountInfo(articleCountInfo: any) {
      this.articleCountInfo = { ...this.articleCountInfo, ...articleCountInfo };
    },
    updateIsAdmin() {
      this.isAdmin = getUserInfo() ? getUserInfo().isAdmin : false;
    }
  }
});

// Define categoryAbout store
export const useCategoryStore = defineStore('categoryAbout', {
  state: () => ({
    categoryCounts: [],
  }),
  actions: {
    async getCategoryCounts() {
      const data = await getCategoryCountList();
      this.updateCategoryCounts(data);
    }
  },
  mutations: {
    updateCategoryCounts(categoryCounts: any[]) {
      this.categoryCounts = categoryCounts;
    }
  }
});

// Define tagAbout store
export const useTagStore = defineStore('tagAbout', {
  state: () => ({
    tagCounts: [],
  }),
  actions: {
    async getTagCounts() {
      const data = await getTagCountList();
      this.updateTagCounts(data);
    }
  },
  mutations: {
    updateTagCounts(tagCounts: any[]) {
      this.tagCounts = tagCounts;
    }
  }
});
