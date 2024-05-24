<template>
  <div class="album py-5 bg-body-tertiary">
    <div class="container">

      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <div class="col" v-for="review in state.reviews" :key="review.id">
          <Reviews :review="review" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, reactive } from 'vue';
import Reviews from '../views/review/Reviews.vue'
import axios from 'axios'

export default {
  name: 'Content',
  components: {
    Reviews,
  },
  setup() {
    const state = reactive({
      reviews: []
    })

    onMounted(() => {
      axios.get('/api/reviews').then((res) => {
        state.reviews = res.data;
      }).catch((error) => {
        console.error('리뷰 조회 중 에러가 발생했습니다. ', error);
      });
    });

    return {
      state,
    }
  }
}
</script>

<style scoped>

</style>