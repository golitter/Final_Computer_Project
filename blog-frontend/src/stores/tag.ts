import { defineStore } from 'pinia';
import { getTagCountList } from '../api/tag';

interface TagCount {
    id: number;
    name: string;
    count: number;
}

interface TagState {
    tagCounts: TagCount[];
    loading: boolean;
    error: string | null;
}

export const useTagStore = defineStore('tag', {
    state: (): TagState => ({
        tagCounts: [],
        loading: false,
        error: null
    }),
    actions: {
        async fetchTagCounts() {
            this.loading = true;
            this.error = null;
            try {
                const response = await getTagCountList();
                if (response && response.data) {
                    this.tagCounts = response.data;
                } else {
                    throw new Error('Invalid response format');
                }
            } catch (error) {
                console.error('Failed to fetch tag counts:', error);
                this.error = error instanceof Error ? error.message : 'Failed to fetch tags';
                this.tagCounts = [];
            } finally {
                this.loading = false;
            }
        },
    },
});
