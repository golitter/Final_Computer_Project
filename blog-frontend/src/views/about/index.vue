<template>
  <div class="about-container">
    <!-- Social Links Section -->
    <div class="social-links">
      <h2>Social Links</h2>
      <div class="links-grid">
        <a href="https://github.com/golitter" target="_blank" class="social-link">
          <font-awesome-icon :icon="['fab', 'github']" />
          <span>GitHub</span>
        </a>
        <a href="mailto:golitter@qq.com" class="social-link">
          <font-awesome-icon :icon="['fas', 'envelope']" />
          <span>Email</span>
        </a>
        <!-- More social links can be added here -->
      </div>
    </div>

    <!-- PDF Resume Section -->
    <div class="resume-container">
      <h2>Resume</h2>
      <div class="pdf-container">
        <embed
          src="/src/assets/pdfs/CV.pdf"
          type="application/pdf"
          width="100%"
          height="800px"
          class="pdf-viewer"
        />
        <div class="pdf-fallback">
          <a href="/src/assets/pdfs/CV.pdf" download="CV.pdf" class="download-link">
            <font-awesome-icon :icon="['fas', 'download']" />
            <span>Download Resume</span>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
// 组件逻辑
</script>

<style scoped lang="scss">
.about-container {
  max-width: 1200px;
  margin: 40px auto 0;
  padding: 20px;
}

.social-links {
  margin-bottom: 20px;
  background: var(--el-bg-color);
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);

  h2 {
    margin-bottom: 20px;
    color: var(--el-text-color-primary);
    font-size: 24px;
  }
}

.links-grid {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}

.social-link {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 20px;
  background: var(--el-bg-color-page);
  border-radius: 8px;
  color: var(--el-text-color-primary);
  text-decoration: none;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    background: var(--el-color-primary-light-9);
    color: var(--el-color-primary);
  }

  .svg-inline--fa {
    font-size: 20px;
  }

  span {
    font-size: 16px;
    font-weight: 500;
  }
}

.resume-container {
  background: var(--el-bg-color);
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);

  h2 {
    margin-bottom: 20px;
    color: var(--el-text-color-primary);
    font-size: 24px;
  }
}

.pdf-container {
  position: relative;
}

.pdf-fallback {
  margin-top: 15px;
  text-align: center;
}

.download-link {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: var(--el-color-primary);
  color: white;
  border-radius: 6px;
  text-decoration: none;
  transition: all 0.3s ease;

  &:hover {
    background: var(--el-color-primary-dark-2);
    transform: translateY(-2px);
  }

  .svg-inline--fa {
    font-size: 16px;
  }
}

.pdf-viewer {
  border-radius: 8px;
  background: white;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

@media screen and (max-width: 768px) {
  .about-container {
    padding: 15px;
  }

  .social-links, .resume-container {
    padding: 20px;
  }

  .pdf-viewer {
    height: 500px;
  }
}
</style>
