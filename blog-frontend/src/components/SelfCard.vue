<template>
    <div class="card">
        <div class="card_header">
            <span :style="header_icon_style">
                <font-awesome-icon :icon="icon" class="header_icon" />
            </span>
            <span>{{ title }}</span>
        </div>
        <div class="card_body">
            <slot>Card body</slot>
        </div>
    </div>
</template>

<script setup lang='ts'>
import { reactive, defineProps } from "vue";

const props = defineProps<{
    title: string;
    icon: string[]; // Assume icon is a string array
    iconColor?: string; // Optional property
}>();

const header_icon_style = reactive({
    color: props.iconColor || "var(--text-color)",
});
</script>

<style lang="scss" scoped>
.card {
    background: white;
    border-radius: 8px;
    box-shadow: var(--card_box_shadow);
    padding: 20px 24px;
    width: 100%;
    margin-top: 25px;
    box-sizing: border-box;
}

.card_header {
    text-align: left !important;
    margin-bottom: 20px;
}

.header_icon {
    font-size: 18px;
    margin-right: 10px;
}

.card_header span {
    font-size: 17px;
    color: var(--text_color);
}
</style>
