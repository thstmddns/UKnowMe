<template>
  <video :class="{'otherVideo1': main.option.matchingRoom == 1 && chat.mobile == false, 'otherVideo2' : main.option.matchingRoom == 2 || chat.mobile == true}" autoplay />
</template>

<script>
import { useMainStore } from "@/stores/main/main";
import { useChatStore } from '@/stores/chat/chat';

export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
  },
  setup() {
    const main = useMainStore();
    const chat = useChatStore();
    return { main, chat };
  },
  mounted() {
    this.streamManager.addVideoElement(this.$el);
  },
};
</script>

<style>
.otherVideo1 {
  border: 3px solid purple;
  border-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  max-width: calc(100vw / 2 - 40px);
  height: calc(100vh - var(--chat-sub-size) - 60px);
  max-height: calc((100vw / 2 - 40px) * 3 / 4);
}
.otherVideo2 {
  border: 3px solid purple;
  border-radius: 20px;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  max-width: calc(100vw / var(--video-size) - 40px);
  height: calc((100vh - var(--chat-sub-size)) / 2 - 80px);
  max-height: calc((100vw / var(--video-size) - 40px) * 3 / 4);
}
</style>