import { createRouter, createWebHistory } from 'vue-router'
import Content from '../components/Content.vue'
import Login from '../views/Login.vue'
import Review from '../views/review/Review.vue'
import Write from '../views/review/Write.vue'
import Profile from '../views/user/Profile.vue'
import Update from '../views/user/Update.vue'

const routes = [
  { path: '/', component: Content },
  { path: '/login', component: Login },
  { path: '/review/:id', component: Review },
  { path: '/write', component: Write },
  { path: '/profile', component: Profile },
  { path: '/update', component: Update },
]

const router = createRouter({
  history: createWebHistory(),
  routes: routes,
});

export default router;
