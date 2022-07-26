<template>
  <div class="background">
    <avatar-select/>
    <now-avatar/>
    <button-list/>
    <logout-modal v-if="logoutBtn == true"/>
    <!-- <match-ani/> -->
  </div>
</template>

<script>
  import AvatarSelect from '@/components/main/AvatarSelect.vue'
  import NowAvatar from '@/components/main/NowAvatar.vue'
  // import MatchAni from '@/components/main/MatchAni.vue'
  import ButtonList from '@/components/main/ButtonList.vue'
  import LogoutModal from '@/components/main/LogoutModal.vue'

  
  export default {
    name: 'MainPage',
    components: { AvatarSelect, NowAvatar, ButtonList, LogoutModal },

  }
</script>

<style>
body {
  margin: 0;
}
.background {
  background: linear-gradient(180deg, #EBDCFE 0%, rgba(217, 217, 217, 0) 100%);
  width: 100vw;
  height: 100vh;
}

</style>