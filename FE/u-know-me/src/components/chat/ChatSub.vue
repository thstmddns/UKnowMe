<template>
  <div class="chat-sub">
    <div class="chat-keyword-container">
      <div class="option-btn-list">
        <div class="option">
          <button class="chat-btn-lg">
            <img src="@/assets/chat/option-img.png" alt="" />
          </button>
        </div>
        <div class="option">
          <button
            @click="chat.leaveSession(), $router.replace({ name: 'main' })"
            class="chat-btn-lg">
            <img src="@/assets/chat/exit-img.png" alt="">
          </button>
        </div>
      </div>
      <div class="keyword">
        <div class="keyword-img-container">
          <img class="keyword-img" src="@/assets/chat/youknowme-img.png" alt="">
        </div>
        <div class="keyword-content">
          <p>"유노"님께서 입장하셨습니다.</p>
          <p>"유노"님께서 입장하셨습니다.</p>
          <p>"유노"님께서 입장하셨습니다.</p>
          <p>이것은 더미 데이터입니다. 길게 한 번 작성해보겠습니다.</p>
          <p>예쁘게 만들고 싶은데 디자인 솜씨가 부족한 걸까요.. 어렵네요.</p>
          <p>예쁘게 만들고 싶은데 디자인 솜씨가 부족한 걸까요.. 어렵네요. 더더더더더더 긴 문장입니다.</p>
        </div>
      </div>
    </div>

    <div class="chat-love-container">
      <div id="love-container">
        <div class="heart-img" @click="chat.heartClick()" :class="chat-success">
          <img src="@/assets/main/heart.png" alt="" />
        </div>
        <div class="circle" style="animation-delay: 0s"></div>
        <div class="circle" style="animation-delay: 1s"></div>
        <div class="circle" style="animation-delay: 2s"></div>
        <div class="circle" style="animation-delay: 3s"></div>
      </div>
    </div>

    <div class="chat-icon-container">
      <div class="icon-list">

      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
.chat-sub {
  display: flex;
  height: max-content;
}
.chat-keyword-container {
  display: flex;
  min-width: 400px;
  width: 80%;
  height: 200px;
}
.chat-love-container {
  
}
.chat-icon-container {
  min-width: 400px;
  height: 200px;
  background-color: red;
}
.option-btn-list {
  width: 15%;
  height: 70%;
  margin: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-between
}
.option {
  display: flex;
  justify-content: right;
}
.chat-btn-lg {
  min-width: 70px;
  width: 120px;
  height: 60px;
  background-color: white;
  border: 0;
  outline: 0;
  box-shadow: 0px 2.72109px 2.72109px rgba(0, 0, 0, 0.25);
  border-radius: 20.4082px;
  cursor: pointer;
  display: flex;
  justify-content: center;
}
.chat-btn-lg:hover {
  background-color: white;
}
.chat-btn-lg img {
  width: 40px;
  height: 40px;
}
.keyword {
  display: flex;
  width: 80%;
  height: 70%;
  margin: auto;
  background-color: white;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
}
.keyword-img-container {
  display: flex;
  width: 25%;
  text-align: center;
  justify-content: center;
}
.keyword-img {
  max-width: 100%;
  margin: 10%;
}
.keyword-content {
  width: 75%;
  height: 140px;
  font-weight: 600;
  overflow: auto;
}
.keyword-content::-webkit-scrollbar {
  display: none;
}
.icon-list {
}
</style>