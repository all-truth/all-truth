<template>
  <div class="album py-5 bg-body-tertiary">
    <div class="container">

      <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <div class="col">
          <Review />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Review from './Review.vue'

export default {
  name: 'Content',
  components: {
    Review,
  },
}
</script>

<style scoped>

</style>