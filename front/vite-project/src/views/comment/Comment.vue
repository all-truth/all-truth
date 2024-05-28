<template>
    <svg class="bd-placeholder-img flex-shrink-0 me-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 32x32" preserveAspectRatio="xMidYMid slice" focusable="false">
      <title>Placeholder</title>
      <rect width="100%" height="100%" fill="#007bff"></rect>
    </svg>
    <div class="pb-3 mb-0 small lh-sm border-bottom w-100">
      <div class="d-flex justify-content-between">
        <strong class="text-gray-dark">{{ comment.nickname }}</strong>
        <button class="btn btn-danger" type="button" v-if="state.user !== null && comment.userId === state.user.id" @click="remove(comment.id)">삭제</button>
      </div>
      <span class="d-block">{{ comment.content }}</span>
    </div>
</template>

<script>
import { watchEffect, reactive } from 'vue';
import instance from '../../api/axios'

export default {
  name: 'Comment',
  props: {
    comment: Object,
    user: Object
  },
  emits: ['comment-removed'],
  setup(props, { emit }) {
    const state = reactive({
      user: {}
    });

    // watchEffect를 통해 props의 변화를 감지
    watchEffect(() => {
      state.user = props.user;
    });

    const remove = async (commentId) => {
      await instance.delete(`/api/review/comment/${commentId}`).then(() => {
        window.alert('리뷰가 삭제되었습니다.');
        emit('comment-removed', commentId);
      }).catch((error) => {
        console.error('리뷰 삭제 중 에러가 발생했습니다. ', error);
      });
    };

    return {
      state,
      remove,
    }
  }
}
</script>

<style scoped>

</style>