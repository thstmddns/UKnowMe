<template>
  <div>
    <div id="progress">{{avatarProgress}}</div>
    <!-- <progress value="22" max="100"></progress> -->
    <div id="nowAvatar"></div>
  </div>
</template>

<script>
import { onMounted } from "vue";
import { useAvatarStore } from "@/stores/main/avatar";
import { storeToRefs } from "pinia";

export default {
  setup() {
    const avatarFun = useAvatarStore();

    let { avatarProgress } = storeToRefs(avatarFun);

    onMounted(() => {
      avatarFun.load();
    });
    return { avatarFun, avatarProgress };
  },
};
</script>

<style>
#nowAvatar {
  position: absolute;
  width: 100%;
  height: 100%;
  left: 50%;
  transform: translate(-50%, 0%);
}
#progress {
  position : absolute;
  z-index: 10;
  font-size: 100px;
}
</style>
