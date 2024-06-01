<template>
  <div class="container">
    <button @click="goBack" class="previous carousel-control-prev-icon px-4 my-4" aria-hidden="true"></button>
      <!-- 프로필 이미지 -->
      <div class="profile">{{ state.user.nickname }} 님 안녕하세요!</div>
      <div class="my-5">
        <button type="button" class="profile-img" @click="profileImageButton">
          <img :src="`${state.user.image === '' || state.user.image === null ? '/default_profile_icon.png' : state.user.image}`" class="profile-img" width="140" height="140" /><br/>
          <input type="file" multiple accept="image/*" class="form_file" @change="profileImageChange" ref="profileImageInput"/>
        </button>
      </div>
      
      <div class="d-flex flex-column flex-md-row p-4 gap-4 py-md-5 align-items-center justify-content-center">
        <div class="list-group profile__list">
          <!-- 닉네임 -->
          <router-link :to="{ path: '/update', query: { filter: 'nickname' } }" class="list-group-item list-group-item-action d-flex gap-3 py-3" aria-current="true">
            <div class="d-flex gap-2 w-100 justify-content-between">
              <div>
                <h6 class="mb-0">닉네임</h6>
              </div>
              <small class="opacity-50 text-nowrap">{{ state.user.nickname }}</small>
            </div>
          </router-link>
          <!-- 비밀번호 -->
          <router-link :to="{ path: '/update', query: { filter: 'password' } }" class="list-group-item list-group-item-action d-flex gap-3 py-3" aria-current="true">
            <div class="d-flex gap-2 w-100 justify-content-between">
              <div>
                <h6 class="mb-0">비밀번호 변경</h6>
              </div>
              <small class="opacity-50 text-nowrap">></small>
            </div>
          </router-link>
        </div>
      </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'
import { reactive, onMounted, ref } from 'vue'
import instance from '../../api/axios.js'
import store from '../../store/index.js'

export default {
  name: 'Profile',

  setup() {
    const router = useRouter();
    const state = reactive({
      user: {},
      form: {
        image: null,
      },
    });
    const profileImageInput = ref(null);

    onMounted(() => {
      instance.get('/api/user').then((res) => {
        console.log(res.data);
        state.user = res.data;
      }).catch((error) => {
        console.error('사용자 정보 조회 중 에러 발생 ', error);
      });
    });

    // 이전 페이지로 이동
    const goBack = () => {
      router.back();
    }

    const profileImageButton = () => {
      profileImageInput.value.click();
    }

    const profileImageChange = (event) => {
      state.form.image = event.target.files[0];

      // FormData 인스턴스 생성
      const formData = new FormData();
      
      formData.append('image', state.form.image);

      instance.patch('/api/user', formData, {
        headers: {
          'ContentType': 'multipart/form-data'
        }
      }).then((res) => {
        console.log(res.data);
        state.user = res.data;
        store.dispatch('setCurrentUser', state.user);
      }).catch((error) => {
        console.error('프로필 이미지 수정 중 에러 발생 ', error);
      });
    }

    return {
      state,
      goBack,
      profileImageInput,
      profileImageButton,
      profileImageChange
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

.profile-img {
  width: 140px;
  height: 140px;
  border: none;
  border-radius: 70%;
}

.profile__list {
  width: 100%;
}

.form {
  display: flex;
  border-radius: 2rem;
  flex-direction: column;
  padding: 4rem 2rem;
  gap: 1.6rem;
  width: 100%;
  max-width: 960px;
  min-width: 400px;
  margin: 0 auto;
}

.form > input[type='text'] {
  margin-bottom: 1rem;
}

.form_text_group {
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
  flex-basis: 50%;
}

.form_text_group label {
  font-size: 1.1rem;
  font-weight: bold;
}

.form_text_group > input {
  width: 100%;
}

.files_content_input {
  display: flex;
  align-items: center;
  border: 1px solid rgb(174, 174, 174);
}
.files_content_input > p {
  flex-grow: 1;
  margin: 0;
}

.files_content_input > button {
  border: none;
  background-color: rgb(174, 174, 174);
  color: white;
  padding: 0.4rem 0.8rem;
}

.form_file {
  display: none;
}

</style>