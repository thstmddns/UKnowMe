<template>
  <h1>버튼 리스트</h1>
  <div class="chat-content-left">
    <div class="chat-guide">
      <div>
        <p>채팅 가이드</p>
      </div>
    </div>

    <div class="love-btn">

    </div>

    <div class="chat-content-right">
      <div class="sketch"></div>
      <div class="chat-btn-list">
        <div class="top-btn">
          <button class="chat-btn sketch-btn"><i class="fa-solid fa-pencil"></i></button>
          <button class="chat-btn camera-btn"><i class="fa-solid fa-camera"></i></button>
          <button class="chat-btn-lg set-btn">옵션 <i class="fa-solid fa-gear"></i></button>
        </div>
        <div class="bottom-btn">
          <button class="chat-btn accuse-btn"><i class="fa-solid fa-light-emergency-on"></i></button>
          <button class="chat-btn game-btn"><i class="fa-solid fa-gamepad-modern"></i></button>
          <button class="chat-btn-lg out-btn">나가기 <i class="fa-solid fa-arrow-right-from-bracket"></i></button>
        </div>
      </div>
      
    </div>
  </div>
  
</template>

<script>
export default {

}
</script>

<style>
.chat-content-left {
  display: flex;
}

.chat-guide {
  width: 40%;
  height: 200px;
  background-color: blanchedalmond;
}
.love-btn {
  width: 20%;
  height: 200px;
  background-color: rgb(255, 205, 249);
  
}
.chat-content-right {
  width: 40%;
  height: 200px;
  background-color: blanchedalmond;
  display: flex;
}

.sketch {
  width: 40%;
  height: 70%;
  margin: auto 20px;
  background-color: white;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
}

.chat-btn-list {
  margin: auto 20px;
}

.chat-btn {
  width: 60px;
  height: 60px;
  margin: 5px;
  background-color: white;
  border: 0;
  outline: 0;
  box-shadow: 0px 2.72109px 2.72109px rgba(0, 0, 0, 0.25);
  border-radius: 20.4082px;
  cursor: pointer;
}
.chat-btn-lg {
  width: 120px;
  height: 60px;
  margin: 5px;
  background-color: white;
  border: 0;
  outline: 0;
  box-shadow: 0px 2.72109px 2.72109px rgba(0, 0, 0, 0.25);
  border-radius: 20.4082px;
  cursor: pointer;
}
</style>