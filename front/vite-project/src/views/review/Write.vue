<template>
  <div class="container">
    <form class="form" @submit.prevent="write">
      <div class="form_text_group">
        <label for="title">제목을 입력해주세요</label>
        <input type="text" name="title" v-model="state.form.title" />
      </div>

      <!-- 상호명 지역명 -->
      <div class="meta">
        <div class="form_text_group">
          <label for="title">상호명</label>
          <input type="text" v-model="state.form.storeName" name="store" />
        </div>

        <div class="form_text_group">
          <label for="title">지역명</label>
          <input type="text" v-model="state.form.region" name="region" />
        </div>
      </div>

      <!-- 영수증, 이미지들 -->
      <div class="files">
        <div class="files_content">
          <label for="title">리뷰 이미지</label>
          <div class="files_content_input">
            <p v-if="state.form.reviewImages.length !== 0">
              <p v-for="(image, idx) in state.form.reviewImages" :key="idx">
                {{ image.name }}
              </p>
            </p>
            <p v-else></p> 
            
            <button type="button" @click="receiptButton">파일 찾기</button>
            <input type="file" multiple accept="image/*" class="form_file" @change="reviewImagesChange" ref="receiptImageInput" />
          </div>
        </div>
        <div class="files_content">
          <label for="title">영수증 이미지</label>
          <div class="files_content_input">
            <p v-if="state.form.receiptImage !== null">
              {{ state.form.receiptImage.name }}
            </p>
            <p v-else></p>
            <button type="button" @click="reviewButton">파일 찾기</button>
            <input type="file" accept="image/*" class="form_file" @change="receiptImageChange" ref="reviewImageInput" />
          </div>
        </div>
      </div>

      <!-- 리뷰 내용 -->
      <textarea name="content" maxlength="500" v-model="state.form.content"></textarea>

      <button class="form_submit_btn" type="submit">작성하기</button>
    </form>
  </div>
</template>
<script>
import { reactive, ref } from 'vue';
import instance from '../../api/axios';
import router from '../../router/index';
import store from '../../store/index';

export default {
  name: 'Write',
  setup() {
    const state = reactive({
      form: {
        title: "",
        storeName: "",
        region: "",
        reviewImages: [],
        receiptImage: null,
        content: "",
      }
    });

    const receiptImageInput = ref(null);
    const reviewImageInput = ref(null);

    const receiptButton = () => {
      receiptImageInput.value.click();
    }

    const reviewButton = () => {
      reviewImageInput.value.click();
    }

    const reviewImagesChange = (event) => {
      state.form.reviewImages = Array.from(event.target.files);
      console.log(state.form.reviewImages);
    };

    const receiptImageChange = (event) => {
      state.form.receiptImage = event.target.files[0];
    };

    const write = async () => {
      // FormData 인스턴스 생성
      const formData = new FormData();

      formData.append('reviewReq', new Blob([JSON.stringify({
        title: state.form.title,
        storeName: state.form.storeName,
        region: state.form.region,
        content: state.form.content,
      })], {
        type: "application/json"
      }));

      // 리뷰 이미지 파일 추가
      state.form.reviewImages.forEach((file) => {
        formData.append('images', file);
      });

      // 영수증 이미지 파일 추가
      if(state.form.receiptImage) {
        formData.append('receiptImage', state.form.receiptImage);
      }

      try {
        const reviewRes = await instance.post('/api/review', formData, {
          headers: {
            'ContentType': 'multipart/form-data'
          }
        });

        window.alert("리뷰가 작성되었습니다.");
        store.dispatch('addReview', reviewRes.data);

        router.push({ path: '/' });

      } catch (error) {
        console.error("리뷰 작성에 실패했습니다. ", error);
        alert("리뷰 작성에 실패했습니다.");
      }
    }

    return {
      state,
      receiptButton,
      reviewButton,
      receiptImageInput,
      reviewImageInput,
      reviewImagesChange,
      receiptImageChange,
      write,
    }
  }
}
</script>

<style scoped>
.container {
  margin: 0 auto;
}

.form {
  display: flex;
  border-radius: 2rem;
  background-color: rgb(243, 254, 254);
  flex-direction: column;
  padding: 4rem 2rem;
  gap: 1.6rem;
  width: 100%;
  max-width: 960px;
  min-width: 400px;
  margin: 0 auto;
}

.form > textarea {
  resize: none;
  height: 20rem;
  border-radius: 1rem;
}

.form > input[type='text'] {
  margin-bottom: 1rem;
}

.form > button {
  background-color: white;
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

.meta {
  display: flex;
  /* 세로 사이 간격 */
  gap: 4rem;
}

.files {
  display: flex;
  width: 100%;
  /* 세로 사이 간격 */
  gap: 4rem;
}

.files_content {
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
  flex-basis: 50%;
  margin-bottom: 4rem;
}

.files_content label {
  font-size: 1.1rem;
  font-weight: bold;
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

.form_submit_btn {
  align-self: end;
  font-size: 1.4rem;
  border: none;
  border-radius: 2rem;
  padding: 0.5rem 1rem;
}
</style>
