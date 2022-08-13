<template>
  <div>
    <div id="progress">
      <progress id="progressTag" :value="avatarFun.avatarProgress" max="100"></progress>
      <div class="loading">
        <span>LOADING</span>
      </div>
    </div>

    <div id="nowAvatarDiv"></div>
  </div>
</template>

<script>
import { onMounted } from "vue";
import { useAvatarStore } from "@/stores/main/avatar";
import { useAccountStore } from '@/stores/land/account';

export default {
  setup() {
    const avatarFun = useAvatarStore();
    const account = useAccountStore();

    onMounted(() => {
      var avatar;
      if (account.currentUser.gender == "M") {
        avatar = 11;
      } else {
        avatar = 21;
      }
      avatarFun.load(avatar);
    });

    return { avatarFun };
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
  z-index: 0;
  position: absolute;
  width: 120%;
  top: 40%;
  left: 50%;
  transform: translate(-50%, 0%);
}

#progressTag {
  position: absolute;
  width: 350px;
  height: 50px;
  left: 50%;
  transform: translate(-50%, 0%);
  text-align: center;
}

.loading {
  position: absolute;
  left: 50%;
  top: 45px;
  transform: translate(-50%, 0%);
  font-size: x-large;
  color: rgb(37, 37, 37);
}

.loading span {
  display: inline-block;
  margin: 0 -0.05em;
  animation: loading 0.5s infinite alternate;
}

progress {
  accent-color: #a056ff;
}

loading span:nth-child(3) {
  animation-delay: 0.2s;
}

loading span:nth-child(4) {
  animation-delay: 0.3s;
}

loading span:nth-child(5) {
  animation-delay: 0.4s;
}

loading span:nth-child(6) {
  animation-delay: 0.5s;
}

loading span:nth-child(7) {
  animation-delay: 0.6s;
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
