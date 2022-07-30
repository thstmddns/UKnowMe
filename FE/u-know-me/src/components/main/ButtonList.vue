<template>
  <div class="match-all">
    <div class="button-list">
      <button class="main-btn" @click="main.logoutBtn = true">
        Logout <i class="fa-solid fa-arrow-right-from-bracket"></i>
      </button>
      <button class="main-btn" @click="main.informBtn = true">
        정보 수정 <i class="fa-solid fa-gear"></i>
      </button>
    </div>

    <div class="match-circle">
      <div id="container">
        <div class="heart-img" @click="this.$router.push({ name: 'chat' })">
          <img src="@/assets/main/heart.png" alt="" />
        </div>
        <div class="circle" style="animation-delay: 0s"></div>
        <div class="circle" style="animation-delay: 1s"></div>
        <div class="circle" style="animation-delay: 2s"></div>
        <div class="circle" style="animation-delay: 3s"></div>
      </div>

      <!-- 매칭이 눌렸을 때는 매칭 중이라고 띄우기-->
      <button class="matching-btn" v-if="matchBtn == false">
        매칭을 시작해주세요
      </button>
      <button class="matching-btn" v-if="matchBtn == true">매칭 중</button>
      <!--  -->
    </div>

    <div></div>
  </div>
</template>

<script>
import { useMainStore } from '@/stores/main/main'

export default {
  name: "ButtonList",
  components: {},
  data() {
    return {
      matchBtn : false,
    }
  },
  setup() {
    const main = useMainStore()
    return { main }
  }
};
</script>

<style>
.match-all {
  position: absolute;
  width: 294px;
  height: 100%;
  right: 3%;
  /* background-color: #D9D9D9; */
}
.button-list {
  position: absolute;
  left: 50%;
  top: 8%;
  transform: translate(-50%, 0%);
}

.main-btn {
  width: 150px;
  height: 60px;
  padding: 10px 25px;
  margin: 15px;
  border: 2px solid #a056ff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 30px;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  overflow: hidden;
  font-size: 17px;
}

.main-btn:hover {
  background: #c699ff;
  color: #fff;
}
.main-btn:before {
  position: absolute;
  display: inline-block;
  top: -180px;
  left: 0;
  width: 30px;
  height: 100%;
  background-color: #fff;
  animation: shiny-btn1 3s ease-in-out infinite;
}
.main-btn:active {
  box-shadow: 4px 4px 6px 0 rgba(255, 255, 255, 0.3),
    -4px -4px 6px 0 rgba(116, 125, 136, 0.2),
    inset -4px -4px 6px 0 rgba(255, 255, 255, 0.2),
    inset 4px 4px 6px 0 rgba(0, 0, 0, 0.2);
}

.match-circle {
  position: absolute;
  left: 50%;
  transform: translate(-50%, 0%);
  bottom: 10%;
  display: grid;
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
  background-color: #a056ff;
  position: absolute;
  opacity: 0;

  animation: scaleIn 4s infinite cubic-bezier(0.36, 0.11, 0.89, 0.32);
}

@keyframes scaleIn {
  from {
    transform: scale(0.5, 0.5);
    opacity: 0.5;
  }
  to {
    transform: scale(2.5, 2.5);
    opacity: 0;
  }
}

.heart-img {
  background-color: white;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  z-index: 100;
  padding: 5px;
  cursor: pointer;
}
.heart-img img {
  width: 50px;
  height: 50px;
}
.heart-img:hover {
  filter: brightness(90%);
}

.matching-btn {
  background-color: #dcddfe;
  width: 200px;
  height: 50px;
  border: none;
  margin: 0;
  border-radius: 30px;
  filter: drop-shadow(0px 1.92647px 1.92647px rgba(0, 0, 0, 0.25));
}
.matching-btn:hover {
  background: #dcddfe;
  color: black;
  cursor: default;
}
.matching-btn:active {
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
}
</style>
