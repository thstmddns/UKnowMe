<template>
  <div class="match-all">
    <div class="button-list">
      <button class="btn">Logout <i class="fa-solid fa-arrow-right-from-bracket"></i></button>
      <button class="btn">정보 수정 <i class="fa-solid fa-gear"></i></button>
    </div>

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
      <!-- 매칭이 눌렸을 때는 매칭 중이라고 띄우기 -->
      <button class="matching-btn">매칭을 시작해주세요</button>
    </div>
  </div>
  
</template>

<script>
export default {};
</script>

<style>
.match-all {
  position: absolute;
  width: 294px;
  height: 100%;
  right: 1%;
  /* background-color: #D9D9D9; */
}
.button-list {
  position: absolute;
    left: 50%;
  transform: translate(-50%, 0%);
}

button {
  margin: 20px;
  outline: none;
  
}

.btn {
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
}

.btn:hover {
  background: #C699FF;
  color: #fff;
}
.btn:before {
  position: absolute;
  content: '';
  display: inline-block;
  top: -180px;
  left: 0;
  width: 30px;
  height: 100%;
  background-color: #fff;
  animation: shiny-btn1 3s ease-in-out infinite;
}
.btn:active{
  box-shadow:  4px 4px 6px 0 rgba(255,255,255,.3),
              -4px -4px 6px 0 rgba(116, 125, 136, .2), 
    inset -4px -4px 6px 0 rgba(255,255,255,.2),
    inset 4px 4px 6px 0 rgba(0, 0, 0, .2);
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

.matching-btn {
  background-color: #DCDDFE;
  width: 200px;
  height: 50px;
  border: none;
  margin: 0;
  border-radius: 30px;
  filter: drop-shadow(0px 1.92647px 1.92647px rgba(0, 0, 0, 0.25));
}
</style>
