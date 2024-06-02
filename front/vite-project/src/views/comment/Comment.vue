<template>
  <img :src="`${comment.image === '' ? '/default_profile_icon.png' : comment.image}`" class="rounded-circle profile-img" width="32" height="32">
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
      console.log(state.user);
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
.profile-img {
  width: 32px;
  height: 32px;
}

</style>