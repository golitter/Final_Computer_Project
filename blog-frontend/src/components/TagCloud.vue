<template>
    <div class="tag-cloud-container">
        <div class="tag-cloud" ref="cloudRef">
            <a v-for="tag in tags" 
               :key="tag.name"
               href="#"
               :style="{
                   color: getRandomColor(),
                   fontSize: getSize(tag.count) + 'px',
                   padding: '5px 10px',
                   display: 'inline-block',
                   margin: '5px',
                   transition: 'all 0.3s ease'
               }"
               @click.prevent="handleClick(tag.name)"
               @mouseover="handleMouseOver"
               @mouseout="handleMouseOut"
            >
                {{ tag.name }} ({{ tag.count }})
            </a>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

interface Tag {
    name: string;
    count: number;
}

const props = defineProps<{
    tags: Tag[];
}>();

const emit = defineEmits<{
    (e: 'tag-click', name: string): void
}>();

const cloudRef = ref<HTMLElement | null>(null);

// Calculate font size based on tag count
const getSize = (count: number) => {
    const baseSize = 14;
    const maxSize = 28;
    const size = baseSize + count * 2;
    return Math.min(size, maxSize);
};

// Get random color
const getRandomColor = () => {
    const colors = [
        '#1892ff',
        '#722ed1',
        '#eb2f96',
        '#52c41a',
        '#faad14',
        '#13c2c2',
        '#2f54eb',
        '#fa541c'
    ];
    return colors[Math.floor(Math.random() * colors.length)];
};

// Handle tag click
const handleClick = (name: string) => {
    emit('tag-click', name);
};

// Mouse hover effect
const handleMouseOver = (event: MouseEvent) => {
    const target = event.target as HTMLElement;
    target.style.transform = 'scale(1.1)';
    target.style.textShadow = '2px 2px 4px rgba(0,0,0,0.2)';
};

const handleMouseOut = (event: MouseEvent) => {
    const target = event.target as HTMLElement;
    target.style.transform = 'scale(1)';
    target.style.textShadow = 'none';
};
</script>

<style scoped>
.tag-cloud-container {
    background: white;
    border-radius: 8px;
    padding: 20px;
    margin: 20px 0;
    box-shadow: var(--el-box-shadow-light);
}

.tag-cloud {
    text-align: center;
    line-height: 1.6;
}

.tag-cloud a {
    text-decoration: none;
    cursor: pointer;
    border-radius: 4px;
}

.tag-cloud a:hover {
    opacity: 0.8;
}
</style> 