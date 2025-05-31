<template>
    <div id="back_to_top" :class="button_class" @click="scroll_to_top">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512">!Font Awesome Free 6.7.2 by @fontawesome -
            https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.
            <path
                d="M214.6 41.4c-12.5-12.5-32.8-12.5-45.3 0l-160 160c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L160 141.2 160 448c0 17.7 14.3 32 32 32s32-14.3 32-32l0-306.7L329.4 246.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3l-160-160z" />
        </svg>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";

const button_class = ref("hidden");

const scroll_to_top = () => {
    window.scrollTo({ top: 0, behavior: "smooth" });
};

// Listen for scroll events
const handle_scroll = () => {
    if (window.scrollY > 300) {
        button_class.value = "";
    } else {
        button_class.value = "hidden";
    }
};

// Add event listener when component is mounted
onMounted(() => {
    window.addEventListener("scroll", handle_scroll);
});

// Clean up event listener
import { onBeforeUnmount } from "vue";
onBeforeUnmount(() => {
    window.removeEventListener("scroll", handle_scroll);
});
</script>

<style scoped lang="scss">
#back_to_top {
    position: fixed;
    bottom: 20px;
    right: 20px;
    z-index: 9999;

    width: 50px;
    height: 50px;
    background: white;
    border-radius: 50%;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
    color: var(--theme-color);
    display: flex;
    align-items: center;
    justify-content: center;

    cursor: pointer;
    opacity: 1;
    user-select: none;
    outline: none;
    -webkit-tap-highlight-color: transparent;
    -webkit-touch-callout: none;
    transition: bottom 0.2s, opacity 0.2s, background-color 0.2s;

    &.hidden {
        bottom: -50px;
        opacity: 0;
    }

    &:hover {
        background-color: var(--theme-color);
        color: red;
    }

    svg {
        fill: currentColor;
        width: 21px;
        height: 21px;
    }
}
</style>

