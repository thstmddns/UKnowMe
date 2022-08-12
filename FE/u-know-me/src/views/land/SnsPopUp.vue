<template>
   <div>
    <p>SNS Login Access Pages</p>
  </div>
</template>

<script>
export default {
  setup() {
    opener.window.document.getElementById('snsLogin').click()
    window.close()
  }
}
</script>

<style>

</style>