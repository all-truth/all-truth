<template>
  <Header />
  <RouterView />
  <Footer />
</template>

<script>
import Header from './components/Header.vue';
import Footer from './components/Footer.vue';
import { RouterView } from 'vue-router';

export default {
  name: 'App',
  components: {
    Header,
    Footer,
  },
};
</script>

<style scoped></style>
