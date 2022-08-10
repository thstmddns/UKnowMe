<template>
  <main-modal v-if="main.btnCh !== 0" />
  <div class="background">
    <avatar-select />
    <now-avatar />
    <button-list />
  </div>
</template>

<script>
import AvatarSelect from "@/components/main/AvatarSelect.vue";
import NowAvatar from "@/components/main/NowAvatar.vue";
import ButtonList from "@/components/main/ButtonList.vue";
import MainModal from "@/components/main/MainModal.vue";
import { useMainStore } from "@/stores/main/main";

export default {
  name: "MainPage",
  components: { AvatarSelect, NowAvatar, ButtonList, MainModal },
  setup() {
    const main = useMainStore();
    return { main };
  },
};
</script>

<style>
body {
  margin: 0;
}
.background {
  background: linear-gradient(180deg, #ebdcfe 0%, rgba(217, 217, 217, 0) 100%);
  width: 100vw;
  height: 100vh;
}
</style>