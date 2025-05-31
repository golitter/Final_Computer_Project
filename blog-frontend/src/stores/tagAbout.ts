import { defineStore } from 'pinia';
import { getTagCountList, type TagCount, type ApiResponse } from '../api/tag';
import { ElMessage } from 'element-plus';

// Define TagState interface
interface TagState {
  tagCounts: TagCount[];
  loading: boolean;
}

export const useTagAboutStore = defineStore('tag', {
  // Define state
  state: (): TagState => ({
    tagCounts: [],
    loading: false
  }),

  // Define actions
  actions: {
    async fetchTagCounts(): Promise<void> {
      if (this.loading) return;
      
      try {
        this.loading = true;
        const response = await getTagCountList();
        
        if (response.status === 200) {
          if (Array.isArray(response.data)) {
            this.tagCounts = response.data;
          } else {
            console.error('Invalid data format:', response.data);
            ElMessage.error('Invalid tag data format');
          }
        } else {
          console.error('Failed to fetch tag counts:', response.message);
          ElMessage.error(response.message || 'Failed to fetch tag data');
        }
      } catch (error) {
        console.error('Failed to fetch tag counts:', error);
        ElMessage.error('Failed to fetch tag data');
      } finally {
        this.loading = false;
      }
    },
  },
});
