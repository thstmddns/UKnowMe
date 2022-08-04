<template>
  <p>테스트 입니다.</p>
   <div>
    <p>Naver Login Access Pages</p>
  </div>
</template>

<script>
import { onMounted } from 'vue'

export default {
  setup() {
    onMounted(() => {
      var naver_id_login  = new window.naver_id_login("Client Id", "callback URL");
      console.log("access token", naver_id_login.getAccessToken()); // 정상적 로그인이 된 경우 access token값 출력
    })
  }
}
</script>

<style>

</style>