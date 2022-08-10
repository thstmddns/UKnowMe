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
  <button class="main-btn" id="avatarBtn" @click="toggleAvatar()">
    아바타&#160;&#160;&#160;<i class="fa-solid fa-person-half-dress"></i>
  </button>
</template>

<script>
import AvatarCard from "@/components/main/AvatarCard.vue";
import { useAvatarStore } from "@/stores/main/avatar";

export default {
  name: "AvatarSelect",
  components: { AvatarCard },
  setup() {
    const avatars = useAvatarStore();

    //media 반응형
    const mediaViewContent = window.matchMedia(`(max-width: 700px)`); // 1
    const viewChangeHandler = (mediaViewContent) => {
      var toggleBtn = document.getElementById("avatarBtn");
      var toggle = document.querySelector(".avatarCollection");

      if (mediaViewContent.matches === true) {
        toggle.style.display = "none";
        toggleBtn.style.display = "block";
      } else {
        toggle.style.display = "block";
        toggleBtn.style.display = "none";
      }
    };

    mediaViewContent.addEventListener("change", viewChangeHandler);

    return {
      avatars,
    };
  },
  methods: {
    toggleAvatar() {
      var toggle = document.querySelector(".avatarCollection");

      if (toggle.style.display == "block" || toggle.style.display == "") {
        toggle.style.display = "none";
      } else {
        toggle.style.display = "block";
      }
    },
  },
};
</script>

<style>
.avatarCollection {
  position: absolute;
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
}
@media screen and (max-width: 700px) {
  .avatarCollection {
    display: none;
  }
  #avatarBtn {
    display: block;
  }
}
@media screen and (min-width: 700px) {
  .avatarCollection {

    height: calc(100% - 10px);
    top: 5px;
  }
  #avatarBtn {
    display: none;
  }
}
</style>
