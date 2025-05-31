<template>
  <div class="card">
    <div class="card_header">
      <div>
        <span><font-awesome-icon :icon="['fas', 'box-archive']" class="header_icon" /></span>
        <span>Archive</span>
      </div>
      <router-link to="/archive/" class="header_more_link" title="View More">
        <font-awesome-icon :icon="['fas', 'angle-right']" class="header_more_icon" />
      </router-link>
    </div>
    <div class="card_body archive_list">
      <router-link v-for="archive in archiveCounts" :key="archive.date" :to="`/archive/${archive.date}`"
        class="archive_item">
        <span class="archive_date">
          {{ archive.date.split('/')[0] }} Year {{ archive.date.split('/')[1] }} Month
        </span>
        <span class="archive_count">{{ archive.count }}</span>
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import { getArchiveCountList } from '../api/archive';

interface Archive {
  date: string;
  count: number;
}

const archiveCounts = reactive<Archive[]>([]);

onMounted(() => {
  getArchiveCountList(1, 100).then((data) => {
    // Use type assertion to clarify the return data structure
    archiveCounts.push(...(data.rows as Archive[]));
  });
});
</script>

<style lang="scss" scoped>
@use '@/assets/css/index.scss' as *; // Import variable file

$card_padding: 20px 24px;
$header_font_size: 17px;
$icon_size: 18px;
$text_color: var(--text-color);
$theme_color: var(--theme-color);

.card {
  background: white;
  border-radius: 8px;
  box-shadow: var(--card-box-shadow);
  padding: $card_padding;
  width: 100%;
  margin-top: 25px;
  box-sizing: border-box;
  box-shadow: $box-shadow;

}

.card_header {
  text-align: left !important;
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .header_icon {
    font-size: $icon_size;
    margin-right: 10px;
    color: dodgerblue;
  }

  .header_more_link {
    transition: all 0.2s;

    &:hover {
      animation: moveLinkAni 1s infinite;
    }

    .header_more_icon {
      font-size: $icon_size;
      color: $text_color;
    }
  }

  span {
    font-size: $header_font_size;
    color: $text_color;
  }
}

.archive_item {
  display: flex;
  justify-content: space-between;
  text-decoration: none;
  padding: 10px;
  color: $text_color;
  font-size: 14px;
  transition: all 0.4s;
  border-radius: 4px;

  &:hover {
    background-color: $theme_color;
    color: white;
    padding: 10px 17px;
  }

  .archive_date {
    flex: 1;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}

@keyframes moveLinkAni {

  0%,
  100% {
    transform: translateX(0);
  }

  50% {
    transform: translateX(3px);
  }
}
</style>