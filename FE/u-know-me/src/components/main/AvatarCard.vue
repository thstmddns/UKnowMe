<template>
  <div class="card">
    <div class="container">
      <img :src="avatar.image" alt="">
    </div>
    <div class="avatar-inform">
      <h4 id="avatar-name">{{avatar.name}}</h4>
      <button class="avatar-button">선택</button>
    </div>
</div>
</template>

<script>
export default {
  name: 'AvatarCard',
  props: {
    avatar : Object,
  },

}
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
  background: #FFFFFF;
  border-radius: 24.9137px;
  display: flex;
  justify-content: center;
  margin: 20px 0;
}
.avatar-button {
  width: 100px;
  height: 40px;
  background: #A056FF;
  color: white;
  box-shadow: 0px 2.95056px 2.21292px rgba(0, 0, 0, 0.25);
  border-radius: 7.37639px;
  border: none;
  margin: 0px;
  cursor: pointer;
}
.avatar-button:hover {
  transition: all 150ms linear;
  opacity: .85;
}

#avatar-name {
  margin: 30px 0;
  display: flex;
  justify-content: center;
}



</style>