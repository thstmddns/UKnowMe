<template>
  <div class="avatarCollection">
    <div class="avatar-card-container">
      <avatar-card v-for="avatar in avatardata" :key="avatar" />
    </div>
  </div>
</template>

<script>
import AvatarCard from "@/components/main/AvatarCard.vue";
import avatardata from "@/assets/main/avatardata.js";

export default {
  name: "AvatarSelect",
  components: { AvatarCard },
  data() {
    return {
      avatardata: avatardata,
    };
  },
};
</script>

<style>
.avatarCollection {
  position: absolute;
  width: 370px;
  height: 100%;
  z-index: 2;
  background: rgba(217, 217, 217, 0.5);
  overflow-x: hidden;
  overflow-y: auto;
}
.avatarCollection::-webkit-scrollbar {
  width: 10px;
}
.avatarCollection::-webkit-scrollbar-thumb {
  height: 30%;
  background: #A056FF;
  border-radius: 10px;
}
.avatarCollection::-webkit-scrollbar-track {
  background: rgba(160, 86, 255, .1);
}
.avatar-card-container {
  width: 20em;
  padding: 0;
  margin: 50px auto 0;
  border: 10px solid #fff;
  box-shadow: 0px 2px 3px #eaeaea;
}
div > div.avatar-card-container {
  background: #f7f7f7;
  color: #ddd;
  list-style-type: none;
  padding: 1em;
  margin: 0;
  transition: background-color 0.5s;
}
div > div.avatar-card-container:nth-child(2) {
  color: #fff;
}
</style>
