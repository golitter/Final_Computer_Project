import { defineStore } from 'pinia';

interface WebsiteState {
    startDate: string;
}

export const useWebsiteStore = defineStore('website', {
    state: (): WebsiteState => ({
        startDate: '2024-10-24',
    }),
});
