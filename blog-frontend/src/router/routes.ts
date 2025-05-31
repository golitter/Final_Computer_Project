import path from 'path';

// Expose configuration routes (constant routes): routes accessible to all users
export const constantRoute = [
  {
    // Home page
    path: '/',
    component: () => import('../views/home/index.vue'),
    name: 'home',
  },
  {
    // Register
    path: '/register',
    component: () => import('../views/register/index.vue'),
    name: 'register',
    meta: {
      title: 'Register', // Menu title
      hidden: true, // Whether the route title is hidden in the menu: true: hidden, false: visible
      icon: 'Promotion', // Icon on the left side of menu text, supports all element-plus icons
    },
  },
  {
    // Login
    path: '/login',
    component: () => import('../views/login/index.vue'),
    name: 'login',
    meta: {
      title: 'Login',
      hidden: true,
      icon: 'Promotion',
    },
  },
  {
    path: "/article/:id",
    name: "Article",
    component: () => import('../views/ArticlePage/index.vue'),
    props: true
  },
  {
    path: "/article/add",
    name: "ArticleAdd",
    component: () => import('../views/ArticleEditPage/index.vue'),
    meta: {
      needAuthentication: true
    }
  },
  {
    path: "/article/:id/change",
    name: "ArticleChange",
    component: () => import('../views/ArticleChangePage/index.vue'),
    props: true,
    meta: {
      needAuthentication: true,
      title: 'Edit Article'
    }
  },
  {
    path: "/manage",
    name: "Manage",
    component: () => import('../views/manage/index.vue'),
    meta: {
      title: 'Article Management',
      needAuthentication: true
    }
  },
  {
    path: "/tag",
    alias: "/article/tag",
    name: "TagList",
    component: () => import('../views/tag/tagList.vue'),
    meta: { title: 'Tag List' }
  },
  {
    path: "/tag/:name/:id",
    name: "TagDetails",
    component: () => import('../views/tag/tagDetails.vue'),
    props: true,
    meta: { title: 'Tag Details' }
  },
  {
    path: "/archive",
    name: "Archive",
    component: () => import('../views/archive/index.vue'),
    meta: { title: 'Article Archive' }
  },
  {
    path: '/category/:name',
    name: 'CategoryDetails',
    component: () => import('../views/category/categoryDetails.vue'),
    props: true,
    meta: {
        title: 'Category Details'
    }
  },
  {
    path: '/category',
    name: 'CategoryList',
    component: () => import('../views/category/categoryList.vue'),
    meta: {
        title: 'Category'
    }
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/about/index.vue'),
    meta: {
      title: 'About Me'
    }
  }
]

