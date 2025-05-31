import { defineStore } from 'pinia';
import { getCategoryCountList } from '../api/category';

interface CategoryState {
    categoryCounts: any[];
}

export const useCategoryStore = defineStore('category', {
    state: (): CategoryState => ({
        categoryCounts: [],
    }),
    actions: {
        async fetchCategoryCounts() {
            const data = await getCategoryCountList();
            this.categoryCounts = data;
            // console.log('categoryCounts', this.categoryCounts);
        },
    },
});
