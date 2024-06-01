<template>
  <header class="py-3 mb-3 border-bottom">
    <div class="px-3">
      <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
          <router-link to="/" class="d-flex align-items-center my-2 my-lg-0 me-lg-auto text-black text-decoration-none" @click="navigateToHome">
            <span class="home">All Truth</span>
          </router-link>

          <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search" @submit.prevent="search">
            <input type="search" class="form-control" placeholder="Search reviews..." aria-label="Search" v-model="searchText">
          </form>
          
          <div class="review-write px-5">
            <router-link to="/write" class="nav-link text-secondary text-center" v-if="isAuthenticated">
              <img src="/write_review.png" alt="리뷰 작성 이미지" class="review-write-image">
              <div>리뷰 작성</div>
            </router-link>
          </div>

          <div class="text-end">
            <router-link to="/login" class="btn btn-outline-dark me-2" v-if="!isAuthenticated">Login</router-link>
          </div>

          <div class="dropdown text-end" v-if="isAuthenticated">
            <a href="#" class="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
              <img :src="`${user.image === '' || user.iamge === null ? '/default_profile_icon.png' : user.image}`" alt="프로필 이미지" width="32" height="32" class="rounded-circle">
            </a>
            <ul class="dropdown-menu text-small">
              <li>
                <router-link to="/profile" class="dropdown-item">Profile
                  <i class='bx bxs-user-check'></i>
                </router-link>
              </li>
              <li>
                <router-link :to="{ path: '/', query: { filter: 'my-reviews' } }" class="dropdown-item">Reviews
                  <i class='bx bxs-file' aria-hidden="true"></i>
                </router-link>
              </li>
              <!-- <li>
                <a href="#" to="/cart" class="dropdown-item">Replies
                  <i class='bx bx-reply-all' aria-hidden="true"></i>
                </a>
              </li> -->
              <li><hr class="dropdown-divider"></li>
              <li>
                <a to="/login" class="dropdown-item" @click="logout">Sign out
                  <i class="bx bxs-log-out-circle"></i>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import { computed, ref } from 'vue';
import store from '../store/index';
import axios from 'axios';
import router from '../router/index.js';

export default {
  name: 'Header',
  setup () {
    const isAuthenticated = computed(() => store.state.isAuthenticated);
    const searchText = ref('');
    const user = computed(() => store.getters.currentUser);

    const logout = () => {
    //   axios.post('logout').then(() => {
    //     store.dispatch('logout');
    //     router.push({path: '/'});
    //   }).catch((error) => {
    //     console.error('로그아웃 중 에러 발생', error);
    //   });

      store.dispatch('logout');
      console.log('로그아웃');
    };

    const search = () => {
      store.dispatch('setSearchText', searchText.value);

      if(searchText.value) {
        axios.get(`/api/review?search=${encodeURIComponent(searchText.value)}`).then((res) => {
          store.dispatch('setSearchResults', res.data);
        }).catch((error) => {
          console.error('검색 중 오류 발생 ', error);
        })
      } else {
        store.dispatch('setSearchResults', []);
      }
      router.push({ path: '/' });
    };

    const navigateToHome = () => {
      store.dispatch('navigateToHome');
    };

    return {
      isAuthenticated,
      searchText,
      user,
      logout,
      search,
      navigateToHome
    }
  }
}
</script>

<style scoped>
.home {
  font-weight: 800;
  font-size: 1.5rem;
}

.review-write {
  height: 50px; /* 고정 높이 */
}

.review-write-image {
  width: 30px;
  height: 30px;
  margin-bottom: 5px;
  margin: auto;
}

.dropdown {
  cursor: pointer;
  text-align: center !important;
}
</style>
