<template>
  <video :class="{'OtherVideo1': main.option.matchingRoom == 1, 'OtherVideo2' : main.option.matchingRoom == 2}" autoplay />
</template>

<script>
import { useMainStore } from "@/stores/main/main";

export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
  },
  setup() {
    const main = useMainStore();
    return { main };
  },
  mounted() {
    this.streamManager.addVideoElement(this.$el);
  },
};
</script>

<style>
.OtherVideo1 {
  border: 3px solid purple;
  border-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  max-width: calc(100vw / 2 - 40px);
  height: calc(100vh - 260px);
  max-height: calc((100vw / 2 - 40px) * 3 / 4);
}
.OtherVideo2 {
  border: 3px solid purple;
  border-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  max-width: calc(100vw / 2 - 40px);
  height: calc((100vh - 200px) / 2 - 80px);
  max-height: calc((100vw / 2 - 40px) * 3 / 4);
}
</style>