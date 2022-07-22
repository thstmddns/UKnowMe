<template>
  <div class="background">
    <h1>메인입니다</h1>
    <avatar-select/>
    <now-avatar/>
    <button-list/>
    <match-ani/>
  </div>
</template>

<script>
  import AvatarSelect from '@/components/main/AvatarSelect.vue'
  import NowAvatar from '@/components/main/NowAvatar.vue'
  import MatchAni from '@/components/main/MatchAni.vue'
  import ButtonList from '@/components/main/ButtonList.vue'

  export default {
    name: 'MainPage',
    components: { AvatarSelect, NowAvatar, MatchAni, ButtonList },

  }
</script>

<style>
.background {
  background: linear-gradient(180deg, #EBDCFE 0%, rgba(217, 217, 217, 0) 100%);
  width: 2000px;
  height: 2000px;
}

</style>