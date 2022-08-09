<template>
  <div class="notice-form-modal-bg">
    <div class="notice-form-modal">
      <div class="notice-form-modal-title">
        <h2 class="notice-form-title">공지사항 등록</h2>
        <div class="close-btn" @click="noticeFormBtn = false">
          <i class="fa-solid fa-xmark x-btn"></i>
        </div>
      </div>
      <div class="form">
        <form id="noticeForm" action="POST" @submit.prevent="admin.addNotice(notice)">
          <div class="form-group">
            <input class="title-input" type="text" name="noticeTitle" id="noticeTitle" placeholder="제목" v-model="notice.title">
          </div>
          <div class="form-group">
            <textarea class="content-input" type="text" name="noticeContent" id="noticeContent" placeholder="내용을 입력하세요" v-model="notice.content"></textarea>
          </div>
          <div class="save-btn">
            <button class="admin-btn notice-save-btn" type="submit">저장</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { storeToRefs } from "pinia";
import { useAdminStore } from "@/stores/admin/admin";
import { ref } from 'vue'

export default {
  name: "NoticForm",
  components: {},
  setup() {
    const admin = useAdminStore()
    const { noticeFormBtn } = storeToRefs(admin)
    const notice = ref({
      title: '',
      content: '',
    })

    return {
      admin,
      noticeFormBtn,
      notice,
    };
  },
};
</script>

<style>
.notice-form-modal-bg {
  width: 100vw;
  height: 100vh;
  z-index: 9;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
}

.notice-form-modal {
  position: relative;
  width: 25%;
  height: 400px;
  background: #ffffff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.notice-form-modal-title {
  margin: 0 8%;
  display: flex;
  justify-content: space-between;
}

.notice-form-title {
  float: left;
  color: #c1c3fc;
}

.notice-form-modal-content {
  padding: 10px 62px 25px ;
  text-align: center;
}

.notice-form-btn-list {
  display: flex;
  justify-content: center;
}

.notice-form-btn {
  width: 100px;
  height: 40px;
  background: #A056FF;
  color: white;
  box-shadow: 0px 2.95056px 2.21292px rgba(0, 0, 0, 0.25);
  border-radius: 7.37639px;
  border: none;
  margin: 0 20px;
  cursor: pointer;
}

.notice-form-btn:hover {
  transition: all 150ms linear;
  opacity: .85;
}
.form {
  /* width: 100px; */
  display: inline;
  justify-content: center;
}
.form-group {
  display: flex;
  justify-content: center;
  padding: 2%;
}
.title-input {
  width: 80%;
  height: 30px;
  border: 1px solid #c1c3fc;
  padding-left: 14px; padding-right: 14px;
}
.content-input {
  width: 80%;
  height: 170px;
  border: 1px solid #c1c3fc;
  padding: 16px 14px;
  background: #fff;
}
.save-btn {
  display: flex;
  justify-content: center;
}
.notice-save-btn {
  width: 100px;
  height: 40px;
}
textarea {
  resize: none;
}
</style>