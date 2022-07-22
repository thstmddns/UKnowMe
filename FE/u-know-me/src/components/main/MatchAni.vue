<template>
  <div class="match-circle">
    <div id="container">
      <div class="item">
        <img src="@/assets/main/heart.png" alt="">
      </div>
      <div class="circle" style="animation-delay: 0s"></div>
      <div class="circle" style="animation-delay: 1s"></div>
      <div class="circle" style="animation-delay: 2s"></div>
      <div class="circle" style="animation-delay: 3s"></div>

    </div>
    <button>매칭</button>
  </div>
  
</template>

<script>
export default {

}
</script>

<style>
.match-circle {
  position: absolute;
  left: 1568px;
  top: 530px;
  display: grid;
  place-content: center;
}
#container {
  width: 200px;
  height: 200px;
  display: grid;
  place-items: center;
  overflow: hidden;
  position: relative;
}
.circle {
  border-radius: 50%;
  width: 80px;
  height: 80px;
  background-color: #A056FF;
  position: absolute;
  opacity: 0;

  animation: scaleIn 4s infinite cubic-bezier(.36, .11, .89, .32);
}

@keyframes scaleIn {
  from {
    transform: scale(.5, .5);
    opacity: .5;
  }
  to {
    transform: scale(2.5, 2.5);
    opacity: 0;
  }
}

.item {
  background-color: white;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  z-index: 100;
  padding: 5px;
}
.item img {
  width: 50px;
  height: 50px;
}
</style>