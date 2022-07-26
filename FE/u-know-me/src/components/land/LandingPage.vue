<template>
  <div class="landing-page">
    <CherryBlossom/>
    <img class="landing-text" src="@/assets/land/landing-text.png" alt="landing-text">
    <div class="avatar">
      <img class="me" src="@/assets/land/character_woman.png" alt="me">
      <img class="you-know" src="@/assets/land/character_man.png" alt="you-know">
    </div>
  </div>
</template>

<script>
import CherryBlossom from '@/components/land/sakura/CherryBlossom.vue'

export default {
  name: 'LandingPage',
  components: {
    CherryBlossom,
  },
}
</script>

<style>
.landing-page {
  position: relative;
  width: 100vw;
  height: 100vh;
  background-size: cover;
  background-position: center;
  background-image: url("@/assets/land/landing_background.png");
}
.landing-text {
  position: absolute;
  width: 210px;
  height: 271px;
  left: 43%;
  top: 0px;
  filter: drop-shadow(0px 5.25522px 5.25522px rgba(0, 0, 0, 0.25));
  pointer-events: none;
}
.you-know {
  position: absolute;
  width: 520px;
  left: calc(43%);
  bottom: 0px;
  pointer-events: none;
}
.me {
  position: absolute;
  width: 600px;
  left: calc(43% - 330px);
  bottom: 0px;
  pointer-events: none;
}
</style>