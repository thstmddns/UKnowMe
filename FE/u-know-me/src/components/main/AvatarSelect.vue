<template>
  <div class="avatarCollection">
    <div class="avatar-card-container">
      <avatar-card
        v-for="(avatar, i) in avatars.avatar"
        :avatar="avatar"
        :key="i"
      />
    </div>
  </div>
</template>

<script>
import AvatarCard from "@/components/main/AvatarCard.vue";
import { useAvatarStore } from '@/stores/main/avatar'

export default {
  name: "AvatarSelect",
  components: { AvatarCard },
  setup() {
    const avatars = useAvatarStore()
    return {
      avatars
    }
  }
};
</script>

<style>
.avatarCollection {
  position: absolute;
  width: 18%;
  height: 100%;
  z-index: 2;
  background: rgba(217, 217, 217, 0.5);
  overflow-x: hidden;
  overflow-y: auto;
  display: flex;
  justify-content: center;
}
.avatarCollection::-webkit-scrollbar {
  width: 10px;
}
.avatarCollection::-webkit-scrollbar-thumb {
  height: 30%;
  background: #a056ff;
  border-radius: 10px;
}
</style>
