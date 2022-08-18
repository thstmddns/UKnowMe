<template>
  <iframe src="./Build/index.html" frameborder="0" width="100%" height="95%">
  </iframe>
  <div class="test-btn">
    <button @click="$router.push('main')">back</button>
  </div>
</template>

<script>
export default {

}
</script>

<style>
.test-btn {
  position: absolute;
  right: 20px;
  bottom: 20px;
}
</style>