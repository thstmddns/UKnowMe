<template>
  <div class="card">
    <div class="container">
      <img :src="avatar.image" alt="" />
    </div>
    <div class="avatar-inform">
      <h4 id="avatar-name">{{ avatar.name }}</h4>
      <button class="avatar-button" @click="avatarSelect(avatar)">선택</button>
    </div>
  </div>
</template>

<script>
import { useAvatarStore } from "@/stores/main/avatar";

export default {
  name: "AvatarCard",
  props: {
    avatar: Object,
  },
  setup() {
    const avatarFun = useAvatarStore();
    return { avatarFun };
  },
  methods: {
    avatarSelect(avatar) {
      console.log(avatar);
      document.getElementById("progress").style.display = "block";
      document.getElementById("nowAvatar").remove();
      this.avatarFun.load(avatar.id);
    },
  },
};
</script>

<style>
.container {
  width: 50%;
}
.container img {
  max-width: 100%;
  height: auto;
  display: block;
}
.card {
  width: 250px;
  height: 150px;
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 24px;
  display: flex;
  justify-content: center;
  margin: 20px 0;
  backdrop-filter: blur(5px);
}
.avatar-button {
  width: 100px;
  height: 40px;
  background: #a056ff;
  color: white;
  box-shadow: 0px 2.95056px 2.21292px rgba(0, 0, 0, 0.25);
  border-radius: 7.37639px;
  border: none;
  margin: 0px;
  cursor: pointer;
}
.avatar-button:hover {
  transition: all 150ms linear;
  opacity: 0.85;
}

#avatar-name {
  margin: 30px 0;
  display: flex;
  justify-content: center;
}
</style>