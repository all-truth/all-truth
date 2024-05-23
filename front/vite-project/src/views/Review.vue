<template>
  <div class="container">
    <button @click="goBack" class="previous carousel-control-prev-icon px-4 my-4" aria-hidden="true"></button>
    <div class="row featurette">
      <div class="col-md-7">
        <h2 class="featurette-heading fw-normal lh-1">{{ state.review.title }}</h2>
        <div class="my-4">
          <span class="name">{{ state.review.storeName }}</span>
          <span class="text-body-secondary">{{ state.review.region }}</span>
        </div>
        <p class="lead my-5">{{ state.review.content }}</p>
      </div>
      <div class="col-md-5">
        <small class="text-body-secondary">인증 영수증</small>
        <svg class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 500x500" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="var(--bs-secondary-bg)"></rect><text x="50%" y="50%" fill="var(--bs-secondary-color)" dy=".3em">500x500</text></svg>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter, useRoute } from 'vue-router';
import { onMounted, reactive } from 'vue';
import axios from 'axios'

export default {
  name: 'Review',
  setup() {
    const route = useRoute();
    const router = useRouter();
    const reviewId = route.params.id;
    const state = reactive({
      review: []
    })

    onMounted(() => {
      axios.get(`/api/review/${reviewId}`).then((res) => {
        console.log(res.data);
        state.review = res.data;
      }).catch((error) => {
        console.error('리뷰 조회 중 에러가 발생했습니다. ', error);
      });
    })
    
    /**
     * 이전 페이지로 이동
     */
    const goBack = () => {
      router.back();
    }

    return {
      reviewId,
      state,
      goBack,
    }
  }
}
</script>

<style scoped>
.previous {
  display: inline-block;
  width: 2.5rem;
  height: 2.5rem;
  background-color: transparent; /* 배경을 투명으로 설정 */
  border: none;
  border-radius: 50%;
  background-size: 100%, 100%;
  background-repeat: no-repeat;
}

.previous.carousel-control-prev-icon {
  background-image: url('data:image/svg+xml;charset=utf8,%3Csvg xmlns="http://www.w3.org/2000/svg" fill="%23000" viewBox="0 0 8 8"%3E%3Cpath d="M5.5 0l1 1-3 3 3 3-1 1-4-4 4-4z"/%3E%3C/svg%3E'); /* 왼쪽 화살표 검정 아이콘으로 변경 */
}

.name {
  font-size: 18px;
  color: cadetblue;
}

</style>