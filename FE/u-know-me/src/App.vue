<template>
  <router-view></router-view>
</template>

<script>
export default {
  name: 'App',
}
</script>

<style>
html, body, #app {
  height: 100%;
  width: 100%;
  overflow: hidden;
  position: fixed;
}
div, p, span, button, label {
  font-family: 'NanumSquareRound';
}
button {
  width: 130px;
  height: 40px;
  padding: 10px 25px;
  border: 2px solid #A056FF;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 30px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  overflow: hidden;
  -webkit-tap-highlight-color : transparent;
}
button:hover {
  background: #C699FF;
  color: #fff;
}
button:before {
  position: absolute;
  display: inline-block;
  top: -180px;
  left: 0;
  width: 30px;
  height: 100%;
  background-color: #fff;
  animation: shiny-btn1 3s ease-in-out infinite;
}
button:active{
  background: #ceaff9;
  box-shadow:  4px 4px 6px 0 rgba(255,255,255,.3),
              -4px -4px 6px 0 rgba(116, 125, 136, .2), 
    inset -4px -4px 6px 0 rgba(255,255,255,.2),
    inset 4px 4px 6px 0 rgba(0, 0, 0, .2);
}
@font-face {
  font-family:'NanumSquareRound';
  src: url('assets/fonts/NanumSquareRoundL.ttf') format('truetype');
  font-weight: 300;
}
@font-face {
  font-family:'NanumSquareRound';
  src: url('assets/fonts/NanumSquareRoundR.ttf') format('truetype');
  font-weight: 400;
}
@font-face {
  font-family:'NanumSquareRound';
  src: url('assets/fonts/NanumSquareRoundEB.ttf') format('truetype');
  font-weight: 600;
}
@font-face {
  font-family:'NanumSquareRound';
  src: url('assets/fonts/NanumSquareRoundB.ttf') format('truetype');
  font-weight: 700;
}
</style>
