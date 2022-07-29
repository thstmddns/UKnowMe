<template>
  <div class="avatarCollection">
    <div>아바타 선택</div>
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
  width: 300px;
  height: 100%;
  background: rgba(217, 217, 217, 0.5);
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
  background: #1998c4;
}
</style>
