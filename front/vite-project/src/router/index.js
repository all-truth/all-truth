import { createRouter, createWebHistory } from 'vue-router'
import Content from '../components/Content.vue'
import Login from '../views/Login.vue'

const routes = [
  { path: '/', component: Content },
  { path: '/login', component: Login },
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes
})

export default router;
