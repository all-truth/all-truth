<template>
  <div class="pb-3 mb-0 small lh-sm border-bottom w-100">
      <div class="d-flex justify-content-between">
        <textarea class="textarea" placeholder="댓글을 작성해주세요." v-model="commentText"></textarea>
      </div>
      <button class="write_button btn btn-primary my-2" type="button" @click="write">작성</button>
    </div>
</template>

<script>
import { ref } from 'vue';
import instance from '../../api/axios';

export default {
  name: 'Write',
  props: {
    reviewId: Number,
  },
  setup(props, { emit }) {
    const commentText = ref('');

    const write = () => {
      instance.post(`/api/review/${props.reviewId}/comment`, { content: commentText.value }).then((res) => {
        window.alert('댓글이 작성되었습니다.');
        emit('comment-added', res.data);
        commentText.value = '';
      }).catch((error) => {
        console.error("댓글 작성 중 에러가 발생했습니다. ", error);
      });
    };

    return {
      commentText,
      write,
    }
  }
}
</script>

<style scoped>
.textarea {
  width: 100%;
  height: 150px;
}

.write_button {
  float: right;
}
</style>