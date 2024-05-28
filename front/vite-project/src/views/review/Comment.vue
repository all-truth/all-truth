<template>
    <svg class="bd-placeholder-img flex-shrink-0 me-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 32x32" preserveAspectRatio="xMidYMid slice" focusable="false">
      <title>Placeholder</title>
      <rect width="100%" height="100%" fill="#007bff"></rect>
    </svg>
    <div class="pb-3 mb-0 small lh-sm border-bottom w-100">
      <div class="d-flex justify-content-between">
        <strong class="text-gray-dark">{{ comment.nickname }}</strong>
        <a href="#" v-if="state.user !== null && comment.userId === state.user.id">삭제</a>
      </div>
      <span class="d-block">{{ comment.content }}</span>
    </div>
</template>

<script>
import { watchEffect, reactive } from 'vue';

export default {
  name: 'Comment',
  props: {
    comment: Object,
    user: Object
  },
  setup(props) {
    const state = reactive({
      user: {}
    });

    watchEffect(() => {
      state.user = props.user;
    });

    return {
      state
    }
  }
}
</script>

<style scoped>

</style>