<template>
    <svg class="bd-placeholder-img flex-shrink-0 me-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 32x32" preserveAspectRatio="xMidYMid slice" focusable="false">
      <title>Placeholder</title>
      <rect width="100%" height="100%" fill="#007bff"></rect>
    </svg>
    <div class="pb-3 mb-0 small lh-sm border-bottom w-100">
      <div class="d-flex justify-content-between">
        <strong class="text-gray-dark">{{ comment.nickName }}</strong>
        <a href="#" v-if="user !== null && comment.userId === user.id">삭제</a>
      </div>
      <span class="d-block">{{ comment.content }}</span>
    </div>
</template>

<script>
import store from '../../store/index'

export default {
  name: 'Comment',
  props: {
    comment: Object
  },
  setup() {
    const user = store.state.user;

    return {
      user,
    }
  }
}
</script>

<style scoped>

</style>