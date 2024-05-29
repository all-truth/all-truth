<template>
  <div class="container">
    <button @click="goBack" class="previous carousel-control-prev-icon px-4 my-4" aria-hidden="true"></button>
    <!-- <button v-if="review.userId === state.user.id" class="review__remove btn btn-danger" @click="removeReview">삭제</button> -->
    <button class="review__remove btn btn-danger" @click="removeReview">삭제</button>
    <div class="row featurette">

      <!-- 리뷰 상세 정보 -->
      <div class="col-md-7">
        <h2 class="featurette-heading fw-normal lh-1">{{ state.review.title }}</h2>
        <div class="my-4">
          <span class="name">{{ state.review.storeName }}</span>
          <span class="text-body-secondary">{{ state.review.region }}</span>
        </div>
        <div class="col-md-5">
          <span class="img" :style="{backgroundImage: `url(${state.review.receiptImage !== null ? state.review.receiptImage : '/default_review_img.png'})`}"></span>
          <img :src="`${state.review.receiptImage}`" alt="" width="250px" height="250px">
          <small class="receipt_name text-body-secondary">인증 영수증</small>
        </div>
        <p class="lead my-5">{{ state.review.content }}</p>
      </div>

      <!-- 리뷰 이미지 -->
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
            <span class="prev carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
          </button>
          <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
            <span class="next carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
          </button>
        </div>
      </div>

      <!-- 댓글 -->
      <div class="my-3 p-3 bg-body rounded shadow-sm">
        <h6 class="border-bottom pb-2 mb-0">Comments</h6>
        <div class="d-flex text-body-secondary pt-3" v-for="comment in state.comments" :key="comment.commentId">
          <Comment :comment="comment" :user="state.user" @comment-removed="removeComment" />
        </div>
        <!-- 댓글 작성 -->
        <div v-if="isAuthenticated" class="d-flex text-body-secondary pt-3">
          <Write :reviewId="Number(reviewId)" @comment-added="addComment" />
        </div>
      </div>

    </div>
  </div>  
</template>

<script>
import { useRouter, useRoute } from 'vue-router';
import { onMounted, reactive, computed } from 'vue';
import axios from 'axios'
import instance from '../../api/axios'
import Image from './Image.vue'
import Comment from '../comment/Comment.vue'
import Write from '../comment/Write.vue'
import store from '../../store/index'

export default {
  name: 'Review',
  components: {
    Image,
    Comment,
    Write,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const reviewId = route.params.id;
    const state = reactive({
      review: {
        images: []
      },
      comments: [],
      user: {}
    });
    const isAuthenticated = computed(() => store.state.isAuthenticated);

    onMounted(async () => {
      try {
        const reviewRes = await axios.get(`/api/review/${reviewId}`);
        state.review = reviewRes.data;

        const commentRes = await axios.get(`/api/review/${reviewId}/comment`);
        state.comments = commentRes.data;

        const accessToken = localStorage.getItem('accessToken');

        if(accessToken) {
          const userRes = await instance.get('/api/user');
          state.user = userRes.data;
        }

      } catch (error) {
        console.error('리뷰 조회 중 에러가 발생했습니다. ', error);
      }
    })

    // 댓글 추가 반영
    const addComment = (newComment) => {
      state.comments.push(newComment);
    }

    // 댓글 삭제 반영
    const removeComment = (commentId) => {
      state.comments = state.comments.filter(comment => comment.id !== commentId);
    }

    
    /**
     * 이전 페이지로 이동
     */
    const goBack = () => {
      router.back();
    }

    /**
     * 리뷰 삭제
     */
    const removeReview = () => {
      instance.delete(`/api/review/${reviewId}`).then(() => {
        window.alert("리뷰가 삭제되었습니다.");
        router.push({ path: '/' });
      }).catch((error) => {
        console.error("리뷰 삭제 중 에러가 발생했습니다. ", error);
      });
    };

    return {
      reviewId,
      state,
      isAuthenticated,
      goBack,
      removeReview,
      addComment,
      removeComment,
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

.review__remove {
  margin-top: 1rem;
  float: right;
}

.receipt_name {
  display: block;
  text-align: center;
}

.name {
  font-size: 18px;
  color: cadetblue;
}

.prev, .next {
  background-color: black;
}
</style>