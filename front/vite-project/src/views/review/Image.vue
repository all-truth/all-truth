<template>
  <div class="carousel-item" :class="{ active: isActive }">
    <img :src="image.url" class="d-block w-100" alt="리뷰 이미지">
  </div>
</template>

<script>
export default {
  name: 'Image',
  props: {
    image: Object,
    isActive: Boolean,
  }
}
</script>

<style scoped>

</style>