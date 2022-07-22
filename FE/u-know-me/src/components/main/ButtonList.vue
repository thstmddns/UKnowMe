<template>
  <div class="buttonList">
    <a class="btn btn-1">Logout</a>
    <a class="btn btn-2">정보 수정</a>
    <a class="btn btn-3">Logout</a>
  </div>
</template>

<script>
export default {};
</script>

<style>
.buttonList {
  position: absolute;
  width: 294px;
  height: 300px;
  left: 1550px;
  top: 37px;
  background-color: #d9d9d9;
}
/* .btn {
  flex: 1 1 auto;
  margin: 10px;
  padding: 30px;
  text-align: center;
  text-transform: uppercase;
  transition: 0.5s;
  background-size: 200% auto;
  color: white;
  box-shadow: 0 0 20px #eee;
  border-radius: 10px;
 }
 .btn:hover {
  background-position: right center;
}
.btn-1 {
  background-image: linear-gradient(to right, #f6d365 0%, #fda085 51%, #f6d365 100%);
}
.btn-2 {
  background-image: linear-gradient(to right, #fbc2eb 0%, #a6c1ee 51%, #fbc2eb 100%);
} */
.btn {
  display: block;
  width: 200px;
  height: 40px;
  line-height: 40px;
  border: 1px #3399dd solid;
  margin: 15px auto;
  background-color: white;
  text-align: center;
  cursor: pointer;
  color: #333;
  transition: all 0.9s, color 0.3;
}
.btn:hover {
  color: #fff;
}
.btn-3:hover {
  box-shadow: 200px 0 0 0 rgba(0, 0, 0, 0.5) inset;
}
</style>
