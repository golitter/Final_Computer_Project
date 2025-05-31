<template>
  <header :class="{ 'header-scrolled': isScrolled }">
    <div class="header_menu">
      <div v-for="item in menuItems" :key="item.name">
        <template v-if="item.target === '_blank'">
          <a 
            :href="'#' + item.href"
            class="header_menu_item" 
            target="_blank"
            rel="noopener noreferrer"
          >
            <font-awesome-icon :icon="item.icon" class="header_icon" />
            <span>{{ item.name }}</span>
          </a>
        </template>
        <router-link 
          v-else
          :to="item.href" 
          class="header_menu_item" 
          :class="{ 'not-logged-in': !store.$state.isAdmin && item.requireLogin }"
          active-class="active"
          @click="handleClick(item)"
        >
          <font-awesome-icon :icon="item.icon" class="header_icon" />
          <span>{{ item.name }}</span>
        </router-link>
      </div>
    </div>

    <!-- Search bar -->
    <div class="header_search">
      <el-input 
        v-model="searchQuery" 
        placeholder="Enter keywords to search..." 
        clearable 
        :prefix-icon="Search"
        @keyup.enter="search"
        class="search-input"
      />
    </div>

    <div id="header_menu_button" @click="drawer = !drawer">
      <font-awesome-icon :icon="['fas', 'bars']" />
    </div>

    <el-drawer 
      v-model="drawer" 
      direction="ltr" 
      :show-close="false" 
      :with-header="false" 
      size="40%"
      class="menu-drawer"
    >
      <admin-menu />
    </el-drawer>
  </header>
</template>

<script setup lang='ts'>
import { reactive, ref, onMounted, onUnmounted } from 'vue';
import { useAdminStore } from '../stores/admin';
import { useRouter } from 'vue-router';
import { Search } from '@element-plus/icons-vue';
import request from '@/utils/request';
import { ElMessage } from 'element-plus';

const store = useAdminStore();
const router = useRouter();
const drawer = ref(false);
const searchQuery = ref('');
const isScrolled = ref(false);

const menuItems = reactive([
  { name: "Home", icon: ["fas", "house"], href: "/" },
  { name: "Archive", icon: ["fas", "box-archive"], href: "/archive" },
  { name: "Categories", icon: ["fas", "folder"], href: "/category" },
  { name: "Tags", icon: ["fas", "tags"], href: "/tag" },
  { name: "About", icon: ["fas", "user"], href: "/about", target: "_blank" },
  { name: "Write Blog", icon: ["fas", "pen"], href: "/article/add", requireLogin: true },
  { name: "Manage", icon: ["fas", "cog"], href: "/manage", requireLogin: true }
]);

// Search functionality
const search = async () => {
  if (searchQuery.value.trim()) {
    try {
      const response = await request({
        url: '/article/search',
        method: 'get',
        params: {
          keyword: searchQuery.value
        }
      });
      
      if (response.data) {
        // Open search results in a new page
        const searchUrl = router.resolve({ 
          path: '/search', 
          query: { 
            q: searchQuery.value,
            results: JSON.stringify(response.data)
          } 
        }).href;
        window.open(searchUrl, '_blank');
      }
    } catch (error) {
      console.error('Search failed:', error);
      ElMessage.error('Search failed, please try again later');
    }
  }
};

// Handle menu item click
const handleClick = (item: any) => {
  if (!store.$state.isAdmin && item.requireLogin) {
    router.push('/login');
  }
};

// Listen for scroll
const handleScroll = () => {
  isScrolled.value = window.scrollY > 50;
};

// Resize listener to hide drawer on larger screens
const handleResize = () => {
  let scale = window.devicePixelRatio;
  let width = document.documentElement.clientWidth * scale;
  if (width > 900 * scale) {
    drawer.value = false;
  }
};

onMounted(() => {
  window.addEventListener("resize", handleResize);
  window.addEventListener("scroll", handleScroll);
});

onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
  window.removeEventListener("scroll", handleScroll);
});
</script>

<style scoped lang="scss">
$header-height: 60px;
$menu-item-color: #606266;
$hover-color: var(--el-color-primary);
@use '@/assets/css/index.scss' as *;

header {
  position: fixed;
  top: 0;
  height: $header-height;
  width: 100%;
  z-index: 9999;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 5%;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
  animation: fadeUpIn 1s;
  --text-color: $menu-item-color;
  --text-hover-color: $hover-color;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);

  &.header-scrolled {
    height: 50px;
    background: rgba(255, 255, 255, 0.98);
    box-shadow: 0 2px 15px 0 rgba(0, 0, 0, 0.1);
  }

  .header_menu {
    display: flex;
    position: relative;
    gap: 30px;
  }

  .header_search {
    display: flex;
    align-items: center;
    margin-left: auto;
    width: 240px;
    margin-right: 20px;
    transition: all 0.3s ease;

    .search-input {
      :deep(.el-input__wrapper) {
        border-radius: 20px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
        padding: 0 15px;
        transition: all 0.3s ease;

        &:hover, &.is-focus {
          box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
        }
      }

      :deep(.el-input__inner) {
        font-size: 14px;
        height: 36px;
      }
    }
  }

  .header_menu_item {
    color: var(--text-color);
    font-size: 15px;
    font-weight: 500;
    padding: 8px 12px;
    border-radius: 6px;
    position: relative;
    display: flex;
    align-items: center;
    gap: 6px;
    transition: all 0.3s ease;

    &.not-logged-in {
      color: var(--el-text-color-disabled);
      opacity: 0.6;
      cursor: pointer;

      &:hover {
        color: var(--el-text-color-disabled);
        background: rgba(var(--el-color-primary-rgb), 0.05);
      }
    }

    .header_icon {
      font-size: 16px;
      transition: transform 0.3s ease;
    }

    &:hover {
      color: var(--text-hover-color);
      background: rgba(var(--el-color-primary-rgb), 0.1);

      .header_icon {
        transform: translateY(-2px);
      }
    }

    &.active {
      color: var(--text-hover-color);
      background: rgba(var(--el-color-primary-rgb), 0.1);
      font-weight: 600;
    }
  }

  #header_menu_button {
    display: none;
    cursor: pointer;
    font-size: 20px;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    transition: all 0.3s ease;

    &:hover {
      color: var(--text-hover-color);
      background: rgba(var(--el-color-primary-rgb), 0.1);
    }
  }

  :deep(.el-drawer__body) {
    padding: 0;
  }

  @media screen and (max-width: 900px) {
    padding: 0 20px;
    
    .header_menu {
      display: none;
    }

    .header_search {
      width: 180px;
      margin-right: 10px;
    }

    #header_menu_button {
      display: flex;
    }
  }

  @media screen and (max-width: 480px) {
    .header_search {
      display: none;
    }

    #header_menu_button {
      margin-left: auto;
    }
  }
}

@keyframes fadeUpIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
