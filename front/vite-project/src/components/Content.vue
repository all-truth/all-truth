<template>
  <div class="album py-5 bg-body-tertiary">
    <div class="container">
      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <div class="col" v-for="review in reviews" :key="review.id">
          <Reviews :review="review" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, computed } from 'vue';
import Reviews from '../views/review/Reviews.vue';
import store from '../store/index.js';
import axios from 'axios';

export default {
  name: 'Content',
  components: {
    Reviews,
  },
  setup() {
    onMounted(() => {
      store.dispatch('fetchReviews');
    });

    const reviews = computed(() => store.getters.reviews);

    return {
      reviews,
    };
  },
};
</script>

<style scoped></style>
