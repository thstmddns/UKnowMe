<template>
  <div class="landing-page">
    <video class="landing-video" muted autoplay loop>
      <source src="~@/assets/land/landing-background.mp4" type="video/mp4">
    </video>
    <img class="landing-text" src="@/assets/land/landing-text.png" alt="landing-text">
    <div class="avatar">
      <img class="me" src="@/assets/land/character_woman.png" alt="me">
      <img class="you-know" src="@/assets/land/character_man.png" alt="you-know">
    </div>
  </div>
</template>

<script>
export default {
  name: 'LandingPage',
}
</script>

<style>
.landing-page {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  /* background-size: cover; */
  /* background-position: center; */
  /* background-image: url("@/assets/land/landing_background.png"); */
}
.landing-video {
  width: 100vw;
  height: 100vh;
  object-fit: cover;
}
.landing-text {
  position: absolute;
  /* width: 210px; */
  /* height: 271px; */
  height: 30%;
  left: 42%;
  top: 0px;
  filter: drop-shadow(0px 5.25522px 5.25522px rgba(0, 0, 0, 0.25));
  pointer-events: none;
}
.you-know {
  position: absolute;
  /* width: 520px; */
  height: 78%;
  left: 42%;
  bottom: 0px;
  pointer-events: none;
}
.me {
  position: absolute;
  /* width: 600px; */
  height: 74%;
  left: calc(42% - 36vh);
  bottom: 0px;
  pointer-events: none;
}
</style>