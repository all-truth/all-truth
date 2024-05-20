<template>
  <header class="py-3 mb-3 border-bottom">
    <div class="px-3">
      <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
          <a href="/" class="d-flex align-items-center my-2 my-lg-0 me-lg-auto text-black text-decoration-none">
            <span class="home">올투르쓰</span>
          </a>

          <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search">
            <input type="search" class="form-control" placeholder="Search reviews..." aria-label="Search">
          </form>
          
          <div class="review-write px-5">
            <a href="#" class="nav-link text-secondary text-center" v-if="isAuthenticated">
              <img src="/write_review.png" alt="리뷰 작성 이미지" class="review-write-image">
              <div>리뷰 작성</div>
            </a>
          </div>

          <div class="text-end">
            <a href="#" class="btn btn-outline-dark me-2" v-if="!isAuthenticated">Login</a>
          </div>

          <div class="dropdown text-end" v-if="isAuthenticated">
            <a href="#" class="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
              <img src="/default_profile_icon.png" alt="프로필 이미지" width="32" height="32" class="rounded-circle">
            </a>
            <ul class="dropdown-menu text-small">
              <li>
                <a class="dropdown-item" href="#">Profile
                  <i class='bx bxs-user-check'></i>
                </a>
              </li>
              <li>
                <router-link to="/orders" class="dropdown-item">Reviews
                  <i class='bx bxs-file' aria-hidden="true"></i>
                </router-link>
              </li>
              <li>
                <router-link to="/cart" class="dropdown-item">Replies
                  <i class='bx bx-reply-all' aria-hidden="true"></i>
                </router-link>
              </li>
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
export default {
  name: 'Header',
  setup () {
    

    return {}
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
}

.dropdown {
  cursor: pointer;
}
</style>