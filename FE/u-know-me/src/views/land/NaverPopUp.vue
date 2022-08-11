<template>
   <div>
    <p>Naver Login Access Pages</p>
  </div>
</template>

<script>
import { useAccountStore } from '@/stores/land/account'
import { useCookies } from "vue3-cookies";

const { cookies } = useCookies();

export default {
  setup() {
    //naver
    const account = useAccountStore()
    const client_id = "YQdwIoQRJWLg8GBYAaZq"
    const callbackUrl = "http://localhost:8080"
    const naver_id_login = new window.naver_id_login(client_id, callbackUrl);
    const access_token = naver_id_login.getAccessToken()
    const expires_in = naver_id_login.oauthParams.expires_in
    cookies.set('snT', access_token, `${expires_in}s`)
    
    opener.window.document.getElementById('aT').value = naver_id_login.getAccessToken()
    opener.window.document.getElementById('aT').click()
    // window.close()
    return {
      account
    }
  }
}
</script>

<style>

</style>