<template>
  <div>
    <div id="nowAvatar">
    </div>
  </div>
</template>

<script>
import {onMounted} from 'vue';
import Avatar from '@/assets/main/avatar';
export default {
  setup() {
    onMounted(() => {
      Avatar.load();
    })
  }
}
</script>

<style>
#nowAvatar {
  position: absolute;
  width: 100%;
  height: 100%;
  left: 50%;
  transform: translate(-50%, 0%);
}
</style>
