<template>
  <div class="card">
    <div class="container">
      <img src="@/assets/main/avatar1.png" alt="">
    </div>
    <div class="avatar-inform">
      <h4 id="avatar-name"><b>캐릭터 이름</b></h4>
      <button class="avatar-button">아바타 변경</button>
    </div>
</div>
</template>

<script>
export default {

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
  margin: 20px;
}
.avatar-button {
  width: 100px;
  height: 30px;
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
}



</style>