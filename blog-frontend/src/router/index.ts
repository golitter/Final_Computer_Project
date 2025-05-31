// Implement template route configuration through vue-router plugin
import { createRouter, createWebHashHistory } from 'vue-router'
import { constantRoute } from './routes'
// Create router
const router = createRouter({
  // Hash mode for routing
  history: createWebHashHistory(),
  routes: constantRoute,
  // Scroll behavior
  scrollBehavior() {
    return {
      left: 0,
      top: 0,
    }
  },
})

const searchRoute = {
  path: '/search',
  name: 'Search',
  component: () => import('../views/ArticleSearch/SearchView.vue'),
  meta: {
    title: 'Search Results'
  }
};

// const articleDetailRoute = {
//   path: '/article/:id',
//   name: 'ArticleDetail',
//   component: () => import('../views/ArticleDetailView.vue'),
//   meta: {
//     title: 'Article Details'
//   }
// };

router.addRoute(searchRoute);
// router.addRoute(articleDetailRoute);

export default router
