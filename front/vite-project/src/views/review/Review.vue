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
        <div class="col-md-5">
          <img src="/default_review_img.png" alt="" width="250px" height="250px">
          <small class="receipt_name text-body-secondary">인증 영수증</small>
        </div>
        <p class="lead my-5">{{ state.review.content }}</p>
      </div>

      <div class="col-md-5">
        <div id="myCarousel" class="carousel slide mb-6" data-bs-ride="carousel">
          <div class="carousel-indicators">
            <button v-for="(image, idx) in state.review.images" :key="image.id" 
            type="button" :data-bs-target="'#myCarousel'" :data-bs-slide-to="idx" 
            :class="{ active: idx === 0 }" :aria-current="idx === 0 ? 'true' : undefined" 
            :aria-label="'Slide ' + (idx + 1)">
            </button>
          </div>
          <div class="carousel-inner">
            <Image v-for="(image, idx) in state.review.images" :key="image.id" :image="image" :class="{ active: idx === 0 }" />
          </div>
          <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </div>

    </div>
  </div>  
</template>

<script>
import { useRouter, useRoute } from 'vue-router';
import { onMounted, reactive } from 'vue';
import axios from 'axios'
import Image from './Image.vue'

export default {
  name: 'Review',
  components: {
    Image
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const reviewId = route.params.id;
    const state = reactive({
      review: {
        images: []
      }
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
  border-style: none;
}

.previous.carousel-control-prev-icon {
  background-image: url('data:image/svg+xml;charset=utf8,%3Csvg xmlns="http://www.w3.org/2000/svg" fill="%23000" viewBox="0 0 10 10"%3E%3Cpath d="M5.5 0l1 1-3 3 3 3-1 1-4-4 4-4z"/%3E%3C/svg%3E'); /* 왼쪽 화살표 검정 아이콘으로 변경 */
}

.receipt_name {
  display: block;
  text-align: center;
}

.name {
  font-size: 18px;
  color: cadetblue;
}

</style>