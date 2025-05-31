<template>
    <div id="footer">

        <div class="footer_box">

            <!-- Copyright -->
            <div class="copyright_text">Copyright© 2024 {{ settings.adminName }}</div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useWebsiteStore } from "@/stores/web"; // Import pinia store
import settings from "@/../settings";
// Reactive state

// Use pinia store
const websiteStore = useWebsiteStore();
</script>

<style scoped lang="scss">
#footer {
    background: #232323;
    margin-top: 5px;
    padding: 15px 0 45px 0;
    width: 100%;
    color: rgb(133, 133, 133);
    text-align: center;
    position: absolute;
}



.footer_box {
    width: 70%;
    margin: 0 auto;
}

.copyright_text {
    font-size: 20px;
}

</style>
