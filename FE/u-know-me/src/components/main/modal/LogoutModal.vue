<template>
  <div class="sign-head">
    <div>로그아웃</div>
  </div>
  <div class="logout-content">
    로그아웃 하시겠습니까?
  </div>
  <div class="logout-config-btn">
    <div class="flex justify-around">
      <button @click="main.btnCh=0">아니오</button>
      <button @click="account.logout()">예</button>
    </div>
  </div>
</template>

<script>
import { useMainStore } from "@/stores/main/main";
import { useAccountStore } from "@/stores/land/account";

export default {
  name: "LogoutModal",
  components: {},
  setup() {
    const main = useMainStore();
    const account = useAccountStore();
    return {
      main,
      account,
    };
  },
};
</script>

<style>
.logout-content {
  margin: 0 0 40px 0;
  text-align: center;
  font-weight: 700;
}
.logout-config-btn {
  margin: 16px 0;
}
.logout-config-btn button {
  width: 120px;
}
</style>
