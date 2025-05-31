<template>
    <el-card class="box_card">
        <template #header>
            <div class="card_header bouncing_header">
                <h2>{{ setting.title }}</h2>
            </div>
        </template>

        <div class="card_body">
            <p class="title">{{ title }}</p>

            <slot>Form Content</slot>
        </div>
    </el-card>
</template>

<script setup lang="ts">
import setting from '../../settings';
const props = defineProps<{
    title: string;
}>();
</script>

<style lang="scss" scoped>
.card_header {
    justify-content: space-between;
    align-items: center;
    text-align: center;
    height: 35px;
}

.card_header h2 {
    font-family: "Kanit";
    margin-top: 3px;
}

.el_card__header {
    padding-top: 0px;
}

.card_body {
    padding: 0px 20px;
}

.card_body h3,
.card_body p {
    text-align: center;
}

.card_body h3 {
    margin-top: 0px;
}

.text_muted {
    color: #627d98;
}

.text_sm {
    font-size: 0.7875rem !important;
}

.box_card {
    width: 461px;
    border: 1px solid transparent;
    border-radius: 10px;
    box-shadow: none !important;
    margin: 0 auto;
}

.m_2 {
    margin-top: 10px;
}

.m_3 {
    margin-top: 15px;
}

.title {
    font-size: 20px;
    color: #627d98;
    margin-top: 0px;
}

@media screen and (max-width: 500px) {
    .box_card {
        width: 80%;
    }
}

/* Adding bounce animation */
@keyframes bounce {

    0%,
    20%,
    50%,
    80%,
    100% {
        transform: translateY(0);
    }

    40% {
        transform: translateY(-10px);
    }

    60% {
        transform: translateY(-5px);
    }
}

/* Applying bounce animation */
.bouncing_header h2 {
    animation: bounce 1s infinite;
}
</style>
