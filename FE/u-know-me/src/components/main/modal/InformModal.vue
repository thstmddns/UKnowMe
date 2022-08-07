<template>
  <div class="sign-head">
    <div>개인 정보 수정</div>
  </div>
  <InformProfile v-if="pBtnCh===1" />
  <InformSecure v-if="pBtnCh===2" /> 
</template>

<script>
import { ref } from 'vue'
import { storeToRefs } from "pinia";
import { useMainStore } from "@/stores/main/main";
import { useAccountStore } from "@/stores/land/account";
import InformProfile from "@/components/main/modal/InformProfile.vue"
import InformSecure from "@/components/main/modal/InformSecure.vue"

export default {
  name: "LogoutModal",
  components: {
    InformProfile,
    InformSecure,
  },
  setup() {
    const main = useMainStore();
    const account = useAccountStore();
    const { pBtnCh } = storeToRefs(main);
    const password = ref('')
    const credentials = ref({
      id: '',
      name: '',
      nickname: '',
      gender: '',
      birth: '',
      tel: '',
      smoke: '',
      address: '',
    })
    return {
      main,
      account,
      pBtnCh,
      password,
      credentials,
    };
  },
};
</script>

<style>
.text-center {
  text-align: center;
}
</style>