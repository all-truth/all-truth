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
import { onMounted, computed, watch } from 'vue';
import { useRoute } from 'vue-router';
import Reviews from '../views/review/Reviews.vue';
import store from '../store/index.js';

export default {
  name: 'Content',
  components: {
    Reviews,
  },
  setup() {
    const route = useRoute();
    const searchText = computed(() => store.state.searchText);
    const searchResults = computed(() => store.state.searchResults);
    const currentFilter = computed(() => store.getters.currentFilter);

    onMounted(async () => {
      const currentFilter = store.getters.currentFilter;
      if (currentFilter === 'my-reviews') {
        await store.dispatch('fetchUserReviews');
      } else {
        await store.dispatch('fetchReviews');
      }
    });

    watch(() => route.query.filter, (filter) => {
      if (filter === 'my-reviews') {
        getUserReviews();
      } else {
        getReviews();
      }
    });

    watch(currentFilter, (filter) => {
      if (filter === 'my-reviews') {
        getUserReviews();
      } else {
        getReviews();
      }
    });

    const getUserReviews = () => {
      store.dispatch('fetchUserReviews');
    };

    const getReviews = () => {
      store.dispatch('fetchReviews');
    };

    const reviews = computed(() => {
      if (currentFilter.value === 'my-reviews') {
        return store.getters.reviews;
      } else {
        return searchText.value ? searchResults.value : store.getters.reviews;
      }
    });

    return {
      reviews,
    };
  },
};
</script>

<style scoped></style>
