import { createApp } from 'vue';
import App from './App.vue';

import router from './router/index';
import store from './store/index';

const app = createApp(App);

app.use(router);
app.use(store);

store.dispatch('initializeAuthentication'); // 로그인 상태 초기화

app.mount('#app');
