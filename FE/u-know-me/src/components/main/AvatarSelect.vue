<template>
  <div class="avatarCollection">
    <div v-if="account.currentUser.gender == 'M'" class="avatar-card-container">
      <avatar-card
        v-for="(avatar, i) in avatars.avatarMan"
        :avatar="avatar"
        :key="i"
      />
    </div>
    <div v-if="account.currentUser.gender == 'W'" class="avatar-card-container">
      <avatar-card
        v-for="(avatar, i) in avatars.avatarWoman"
        :avatar="avatar"
        :key="i"
      />
    </div>
  </div>
  <button class="main-btn" id="avatarBtn" @click="toggleAvatar()">
    아바타&#160;&#160;&#160;<i class="fa-solid fa-person-half-dress"></i>
  </button>
</template>

<script>
import AvatarCard from "@/components/main/AvatarCard.vue";
import { useAvatarStore } from "@/stores/main/avatar";
import { useAccountStore } from "@/stores/land/account";

export default {
  name: "AvatarSelect",
  components: { AvatarCard },
  setup() {
    const avatars = useAvatarStore();
    const account = useAccountStore();

    //media 반응형
    const mediaViewContent = window.matchMedia(`(max-width: 700px)`); // 1
    const viewChangeHandler = (mediaViewContent) => {
      var toggleBtn = document.getElementById("avatarBtn");
      var toggle = document.querySelector(".avatarCollection");

      if (mediaViewContent.matches === true) {
        toggle.style.left = "-300px";
        toggleBtn.style.bottom = "50px";
      } else {
        toggle.style.left = "0px";
        toggleBtn.style.bottom = "-70px";
      }
    };
    mediaViewContent.addEventListener("change", viewChangeHandler);

    return { avatars, account };
  },
  methods: {
    toggleAvatar() {
      var toggle = document.querySelector(".avatarCollection");

      if (toggle.style.left == "0px") {
        toggle.style.left = "-300px";
      } else {
        toggle.style.left = "0px";
      }
    },
  },
};
</script>

<style>
.avatarCollection {
  position: absolute;
  left: -300px;
  width: fit-content;
  z-index: 2;
  height: calc(100% - 170px);
  top: 5px;
  padding-right: 20px;
  padding-left: 20px;
  border-radius: 20px;
  background: rgba(217, 217, 217, 0.5);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  overflow-x: hidden;
  overflow-y: auto;
  justify-content: center;
  backdrop-filter: blur(5px);
  transition: 0.5s;
}
.avatarCollection::-webkit-scrollbar {
  width: 10px;
}
.avatarCollection::-webkit-scrollbar-thumb {
  height: 30%;
  background: #a056ff;
  border-radius: 10px;
}
#avatarBtn {
  position: absolute;
  left: 60px;
  bottom: 50px;
  margin: 0px;
  z-index: 2;
  transition: 0.5s;
}
@media screen and (max-width: 700px) {
  .avatarCollection {
    left: -300px;
  }
  #avatarBtn {
    bottom: 50px;
  }
}
@media screen and (min-width: 700px) {
  .avatarCollection {
    left: 0px;
    height: calc(100% - 10px);
    top: 5px;
  }
  #avatarBtn {
    bottom: -70px;
  }
}
</style>
