<template>
  <div>

    <!-- <progress value="22" max="100"></progress> -->
    <div id="progress">

      <progress id="progressTag" :value="avatarProgress" max="100"></progress>

      <div class="loading">
        <span>L</span>
        <span>O</span>
        <span>A</span>
        <span>D</span>
        <span>I</span>
        <span>N</span>
        <span>G</span>
      </div>
    </div>

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
  z-index: 10;
  position: absolute;
  width: 120%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, 0%);
}

#progressTag {
  position: absolute;
 width: 350px;
 height : 50px;
  left: 50%;
  transform: translate(-50%, 0%);
  text-align: center;
}

.loading {
  position: absolute;
  left: 50%;
  top : 45px;
  transform: translate(-50%, 0%);
  font-size:  x-large ;
  color: rgb(37, 37, 37);
}

.loading span {
  display: inline-block;
  margin: 0 -.05em;
  animation: loading 1.4s infinite alternate;
 
}

progress {
  accent-color: #a056ff;
}

loading span:nth-child(3) {
  animation-delay: .2s;
}

loading span:nth-child(4) {
  animation-delay: .3s;
}

loading span:nth-child(5) {
  animation-delay: .4s;
}

loading span:nth-child(6) {
  animation-delay: .5s;
}

loading span:nth-child(7) {
  animation-delay: .6s;
}

@keyframes loading {
  0% {
    opacity: 1;
  }

  100% {
    opacity: 0;
  }
}
</style>
