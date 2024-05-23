<template>
  <div class="card shadow-sm">
    <div class="card-body">
      <span class="img" :style="{backgroundImage: `url(${review.images.length > 0 ? review.images[0] : '/default_review_img.png'})`}"></span>
      <p class="card-text">{{ handleText.sliceText(review.title) }}</p>
      <div class="d-flex justify-content-between align-items-center py-3">
        <strong class="name">{{ review.storeName }}</strong>
        <small class="text-body-secondary">{{ review.region }}</small>
      </div>
    </div>
  </div>
</template>

<script>
import handleText from '../scripts/handleString'

export default {
  name: 'Review',
  props: {
    review: Object
  },
  setup() {

    return {
      handleText,
    }
  }
}
</script>

<style scoped>
.name {
  font-size: 15px;
  color: cadetblue;
}

.img {
  display: inline-block;
  width: 100%;
  height: 225px;
  background-size: cover;
  background-position: center;
}

</style>