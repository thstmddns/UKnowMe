<template>
  <div class="avatarCollection">
    <div>아바타 선택</div>
    <avatar-card/>
    <avatar-card/>
  </div>
</template>

<script>
import AvatarCard from '@/components/main/AvatarCard.vue'

export default {
  name: 'AvatarSelect',
  components: { AvatarCard },
}
</script>

<style>
.avatarCollection {
  position: absolute;
  width: 300px;
  height: 100%;
  background: rgba(217, 217, 217, 0.5);
}
</style>