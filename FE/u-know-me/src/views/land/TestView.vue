<template>
  <p>테스트 입니다.</p>
   <div>
    <p>Naver Login Access Pages</p>
  </div>
</template>

<script>
import { useAccountStore } from '@/stores/land/account'

export default {
  setup() {
    //naver
    const account = useAccountStore()
    const client_id = "5OSOWuXn6DTVQB4_h5Pc" 
    const callbackUrl = "http://localhost:8080/"
    const naver_id_login = new window.naver_id_login(client_id, callbackUrl);
    account.naverLogin(naver_id_login.getAccessToken())
  }
}
</script>

<style>

</style>