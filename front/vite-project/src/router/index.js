import { createRouter, createWebHistory } from 'vue-router'
import Content from '../components/Content.vue'
import Login from '../views/Login.vue'
import Review from '../views/review/Review.vue'
import Write from '../views/review/Write.vue'

const routes = [
  { path: '/', component: Content },
  { path: '/login', component: Login },
  { path: '/review/:id', component: Review },
  { path: '/write', component: Write },
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes,
});

export default router;
