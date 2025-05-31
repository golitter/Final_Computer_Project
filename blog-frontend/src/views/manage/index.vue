<template>
  <div v-if="isAuthenticated" class="manage-container">
    <div class="manage-header">
      <div class="header-left">
        <h2 class="header-title">Article Management</h2>
        <div class="header-stats">
          <el-tag type="success" effect="dark" class="stats-tag">
            <el-icon><Document /></el-icon>
            <span class="article-count">Total {{ total }} articles</span>
          </el-tag>
        </div>
      </div>
      <div class="header-right">
        <el-button type="primary" @click="router.push('/article/add')" class="add-button" :icon="Plus">
          Write Article
        </el-button>
      </div>
    </div>

    <div class="filter-section">
      <el-form :inline="true" :model="filterForm" class="filter-form">
        <el-form-item label="Title">
          <el-input
            v-model="filterForm.title"
            placeholder="Search article title"
            clearable
            @clear="handleFilter"
            @keyup.enter="handleFilter"
          />
        </el-form-item>
        <el-form-item label="Tag">
          <el-select
            v-model="filterForm.tagId"
            placeholder="Select tag"
            clearable
            @change="handleFilter"
          >
            <el-option
              v-for="tag in tags"
              :key="tag.id"
              :label="tag.name"
              :value="tag.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleFilter">Search</el-button>
          <el-button @click="resetFilter">Reset</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="article-list">
      <el-card shadow="hover">
        <el-table 
          :data="articles" 
          style="width: 100%"
          :header-cell-style="{
            background: 'var(--el-color-primary-light-9)',
            color: 'var(--el-text-color-primary)',
            fontWeight: 'bold'
          }"
          :row-class-name="tableRowClassName"
        >
          <el-table-column prop="title" label="Title" min-width="300">
            <template #default="{ row }">
              <router-link :to="'/article/' + row.id" class="article-title">
                {{ row.title }}
              </router-link>
            </template>
          </el-table-column>
          <el-table-column prop="viewCount" label="Views" width="120" align="center">
            <template #default="{ row }">
              <el-tag size="small" :type="getViewCountType(row.viewCount)">
                {{ row.viewCount || 0 }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="Create Time" width="200" align="center">
            <template #default="{ row }">
              <el-tooltip 
                :content="formatDate(row.createTime || '')" 
                placement="top"
                effect="light"
              >
                <span class="create-time">{{ formatDate(row.createTime || '') }}</span>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="Actions" width="180" align="center" fixed="right">
            <template #default="{ row }">
              <el-button-group>
                <el-button 
                  type="primary" 
                  size="small" 
                  @click="handleEdit(row.id)"
                  :icon="Edit"
                >
                  Edit
                </el-button>
                <el-button 
                  type="danger" 
                  size="small" 
                  @click="handleDelete(row.id)"
                  :icon="Delete"
                >
                  Delete
                </el-button>
              </el-button-group>
            </template>
          </el-table-column>
        </el-table>
        <div class="pagination">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :total="total"
            :page-sizes="[10, 20, 30, 50]"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            background
            :locale="{
              total: 'Total',
              goto: 'Go to',
              pageClassifier: '',
              pagesize: 'items/page'
            }"
          />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeMount, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Edit, Delete, Plus, Document } from '@element-plus/icons-vue'
import request from '@/utils/request'
import { getPostArticleList, deleteArticle } from '@/api/article'
import type { Article, ArticleResponse } from '@/types/article'
import { formatDate } from '@/utils/date'

interface Tag {
  id: number
  name: string
}

interface FilterForm {
  title: string
  tagId: number | null
}

const router = useRouter()
const articles = ref<Article[]>([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const isAuthenticated = ref(false)
const tags = ref<Tag[]>([])

const filterForm = reactive<FilterForm>({
  title: '',
  tagId: null
})

const getViewCountType = (count: number) => {
  if (!count) return 'info'
  if (count >= 1000) return 'danger'
  if (count >= 500) return 'warning'
  if (count >= 100) return 'success'
  return 'info'
}

const tableRowClassName = ({ rowIndex }: { rowIndex: number }) => {
  if (rowIndex % 2 === 0) {
    return 'even-row'
  }
  return 'odd-row'
}

const checkAuth = () => {
  const token = localStorage.getItem('token')
  if (!token) {
    ElMessage({
      message: 'Please login first',
      type: 'warning',
      duration: 2000
    })
    router.push('/login')
    return false
  }
  isAuthenticated.value = true
  return true
}

const loadTags = async () => {
  try {
    const response = await request({
      url: '/tag/tagCountList',
      method: 'get',
      needAuthentication: true
    })

    console.log('Tag list response:', response)

    if (response && response.data) {
      tags.value = response.data.map((tag: any) => ({
        id: tag.id,
        name: tag.name
      }))
    }
  } catch (error: any) {
    console.error('Failed to get tag list:', error)
    if (error.response?.status === 401) {
      isAuthenticated.value = false
      ElMessage.error('Login expired, please login again')
      router.push('/login')
    } else {
      ElMessage.error('Failed to get tag list')
    }
  }
}

const handleFilter = () => {
  currentPage.value = 1
  console.log('filterForm', filterForm)
  loadArticles()
}

const resetFilter = () => {
  filterForm.title = ''
  filterForm.tagId = null
  handleFilter()
}

const loadArticles = async () => {
  if (!checkAuth()) return
  
  try {
    const response = await getPostArticleList(
      currentPage.value,
      pageSize.value,
      undefined,
      filterForm.tagId || undefined,
      undefined,
      filterForm.title || ''
    )

    console.log('Article list response:', response)

    if (response && response.data) {
      articles.value = response.data.rows
      total.value = Number(response.data.total)
      console.log('Total articles:', total.value)
    } else {
      articles.value = []
      total.value = 0
      console.warn('No article data received')
    }
  } catch (error: any) {
    console.error('Error getting article list:', error?.response || error)
    if (error.response?.status === 401) {
      isAuthenticated.value = false
      ElMessage.error('Login expired, please login again')
      router.push('/login')
    } else {
      ElMessage.error('Failed to get article list: ' + (error.response?.data?.message || error.message || 'Unknown error'))
    }
  }
}

const handleSizeChange = (val: number) => {
  pageSize.value = val
  loadArticles()
}

const handleCurrentChange = (val: number) => {
  currentPage.value = val
  loadArticles()
}

const handleDelete = (id: number) => {
  ElMessageBox.confirm(
    'Are you sure you want to delete this article?',
    'Warning',
    {
      confirmButtonText: 'Confirm',
      cancelButtonText: 'Cancel',
      type: 'warning',
    }
  )
  .then(async () => {
    try {
      await deleteArticle(id);
      ElMessage({
        type: 'success',
        message: 'Delete successful'
      });
      loadArticles();
    } catch (error) {
      console.error('Failed to delete article:', error);
      ElMessage({
        type: 'error',
        message: 'Delete failed'
      });
    }
  })
  .catch(() => {
    ElMessage({
      type: 'info',
      message: 'Delete cancelled'
    });
  });
}

const handleEdit = (id: number) => {
  if (!checkAuth()) return
  router.push(`/article/edit/${id}`)
}

onBeforeMount(() => {
  checkAuth()
})

onMounted(async () => {
  if (checkAuth()) {
    try {
      await Promise.all([loadTags(), loadArticles()])
    } catch (error) {
      console.error('Failed to initialize data:', error)
    }
  }
})
</script>

<style lang="scss" scoped>
.manage-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
}

.manage-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  padding: 20px;
  background: var(--el-bg-color);
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);

  .header-left {
    display: flex;
    align-items: center;
    gap: 24px;

    .header-title {
      margin: 0;
      color: var(--el-text-color-primary);
      font-weight: 600;
      font-size: 28px;
      position: relative;

      &::after {
        content: '';
        position: absolute;
        bottom: -8px;
        left: 0;
        width: 40px;
        height: 4px;
        background: var(--el-color-primary);
        border-radius: 2px;
      }
    }

    .header-stats {
      display: flex;
      align-items: center;
      gap: 16px;

      .stats-tag {
        display: flex;
        align-items: center;
        gap: 8px;
        padding: 8px 16px;
        font-size: 14px;
        border-radius: 20px;

        .article-count {
          font-weight: 500;
        }
      }
    }
  }

  .header-right {
    display: flex;
    align-items: center;

    .add-button {
      display: flex;
      align-items: center;
      gap: 8px;
      padding: 12px 24px;
      font-size: 15px;
      font-weight: 500;
      border-radius: 8px;
      transition: all 0.3s ease;

      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(var(--el-color-primary-rgb), 0.3);
      }
    }
  }
}

.filter-section {
  margin-bottom: 24px;
  padding: 24px;
  background-color: var(--el-bg-color);
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);

  .filter-form {
    display: flex;
    flex-wrap: wrap;
    gap: 16px;
  }
}

.article-list {
  background-color: var(--el-bg-color);
  border-radius: 8px;

  .article-title {
    color: var(--el-text-color-primary);
    text-decoration: none;
    transition: color 0.3s;
    font-weight: 500;
    display: inline-block;
    max-width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;

    &:hover {
      color: var(--el-color-primary);
    }
  }

  .create-time {
    color: var(--el-text-color-secondary);
    font-size: 13px;
  }
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  padding: 16px 0;
}

:deep(.even-row) {
  background-color: var(--el-bg-color);
}

:deep(.odd-row) {
  background-color: var(--el-fill-color-light);
}

:deep(.el-table) {
  --el-table-border-color: var(--el-border-color-lighter);
  border-radius: 8px;
  overflow: hidden;

  th {
    font-weight: 600;
  }
}

:deep(.el-button-group) {
  display: flex;
  gap: 8px;
}

:deep(.el-card) {
  border: none;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}
</style>
