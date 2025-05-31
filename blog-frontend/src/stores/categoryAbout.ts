import { defineStore } from 'pinia';
import { getCategoryCountList } from '../api/category'; 
import { CategoryCount } from '../api/category';
import { ElMessage } from 'element-plus';

interface CategoryState {
    categoryCounts: CategoryCount[];
    loading: boolean;
}

export const useCategoryAboutStore = defineStore('category', {
    state: (): CategoryState => ({
        categoryCounts: [],
        loading: false
    }),
    actions: {
        async fetchCategoryCounts() {
            if (this.loading) return;

            try {
                this.loading = true;
                const response = await getCategoryCountList();
                if (response.status === 200) {
                    this.categoryCounts = response.data; // Only extract data array
                } else {
                    console.error("Failed to get category article count:", response.message);
                    ElMessage.error(response.message || 'Failed to get category data');
                }
            } catch (error) {
                console.error("Error requesting category article count:", error);
                ElMessage.error('Failed to get category data');
            } finally {
                this.loading = false;
            }
        },
    },
});