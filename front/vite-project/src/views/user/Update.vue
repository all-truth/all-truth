<template>
  <div class="container">
    <button @click="goBack" class="previous carousel-control-prev-icon px-4 my-4" aria-hidden="true"></button>
    <div class="col-md-7 col-lg-8 py-4">
      <form class="needs-validation" @submit.prevent="submit">
        <!-- 닉네임 -->
        <div class="row g-3" v-if="filter === 'nickname'">
          <h4 class="mb-3">새로운 닉네임을 입력해주세요.</h4>
          <div class="col-12">
            <input type="text" class="form-control" v-model="updateState.form.nickname">
          </div>
        </div>
    
        <!-- 비밀번호 -->
        <div class="row g-3" v-else-if="filter === 'password'">
          <h4 class="mb-3">비밀번호 변경</h4>
          <div class="col-12 py-3">
            <label for="password">안전한 변경을 위해 현재 비밀번호를 확인할게요.</label>
            <input type="password" name="password" class="form-control" v-model="updateState.form.existsPassword" />
          </div>

          <div class="col-12 py-3">
            <label for="newPassword">새로운 비밀번호를 입력해주세요.</label>
            <input type="password" name="newPassword" class="form-control" v-model="updateState.form.password" />
          </div>

          <div class="col-12 py-3">
            <label for="newPasswordConfirm">확인을 위해 한 번 더 입력해주세요.</label>
            <input type="password" name="newPasswordConfirm" class="form-control" v-model="updateState.form.passwordConfirm" />
          </div>
        </div>
        <hr class="my-4">
        <button class="submit-button w-10 btn btn-primary btn-lg" type="submit">변경 완료</button>
        </form>
      </div>
  </div>
</template>

<script>
import { computed, reactive } from 'vue';
import { useRoute } from 'vue-router';
import instance from '../../api/axios';
import router from '../../router/index'

export default {
  name: 'Update',

  setup() {
    const route = useRoute();
    const filter = computed(() => route.query.filter);
    const updateState = reactive({
      form: {
        nickname: null,
        existsPassword: null,
        password: null,
        passwordConfirm: null
      }
    });

    // 이전 페이지로 이동
    const goBack = () => {
      router.back();
    }

    // 사용자 정보 수정
    const submit = () => {
      const formData = new FormData();
      
      formData.append('nickname', updateState.form.nickname);
      formData.append('password', updateState.form.password);
      formData.append('passwordConfirm', updateState.form.passwordConfirm);

      instance.patch('/api/user', formData, {
        headers: {
          'ContentType': 'multipart/form-data'
        }
      }).then(() => {
        window.alert('변경이 완료되었습니다.');
        router.push({ path: '/profile' });
      }).catch((error) => {
        console.error('사용자 정보 변경 중 에러가 발생했습니다. ', error);
      });
    }

    return {
      filter,
      updateState,
      goBack,
      submit,
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

.submit-button {
  float: right;
}
</style>