<template>
  <div class="admin">
    <div class="admin-toolbar">
      <div class="admin-toolbar-container">
        <div class="admin-toolbar-top">
          <p>관리자 박진경</p>
        </div>
        <hr>
        <div class="admin-toolbar-bottom">
          <div class="admin-btn admin-member" @click="adminBtn = 0">회원 관리</div>
          <div class="admin-btn admin-notice" @click="adminBtn = 1">공지사항</div>
          <div class="admin-btn admin-avatar" @click="adminBtn = 2">아바타 관리</div>
          <div class="admin-btn admin-balance" @click="adminBtn = 3">밸런스 게임</div>
          <div class="admin-btn amdin-helper" @click="adminBtn = 4">도우미 키워드</div>
        </div>
      </div>
      <button class="admin-logout-btn">로그아웃&#160;&#160;<i class="fa-solid fa-arrow-right-from-bracket"></i></button>
    </div>
    <div class="admin-content">
      <admin-member
        v-if="adminBtn == 0"
      />
      <admin-notice
        v-if="adminBtn == 1"
      />
      <admin-avatar
        v-if="adminBtn == 2"
      />
      <admin-balance
        v-if="adminBtn == 3"
      />
      <admin-helper
        v-if="adminBtn == 4"
      />

    </div>
  </div>
  
</template>

<script>
import { storeToRefs } from 'pinia'
import { useLandStore } from '@/stores/admin/admin'
import AdminMember from '@/components/admin/AdminMember.vue'
import AdminNotice from '@/components/admin/AdminNotice.vue'
import AdminAvatar from '@/components/admin/AdminAvatar.vue'
import AdminBalance from '@/components/admin/AdminBalance.vue'
import AdminHelper from '@/components/admin/AdminHelper.vue'


export default {
  name : "AdminPage",
  components : {
    AdminMember, AdminNotice, AdminAvatar, AdminBalance, AdminHelper
  },
  setup() {
    const admin = useLandStore()
    const { adminBtn } = storeToRefs(admin)
    return {
      admin,
      adminBtn
    }
  }

}
</script>

<style>
hr {
  border: 0;
  height: 5px;
  background-color: #c1c3fc;
}
.admin {
  display: flex;
  width: 100vw;
  height: 100vh;
}
.admin-toolbar {
  position: relative;
  width: 300px;
  height: 100%;
  background-color: #dcddfe;
}
.admin-toolbar-container {
  text-align: center;
}
.admin-toolbar-top {
  /* display: flex; */
  align-items: center;
  justify-content: center;
  font-size: 20px;
}
.admin-logout-btn {
  position: absolute;
  bottom: 1%;
  right: 1%;
  width: 100px;
  height: 40px;
  padding: 10px 10px;
  margin: 10px;
  border: none;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  background: #f1f1ff;
  cursor: pointer;
}
.admin-btn {
  margin: 10%;
  font-size: 20px;
  cursor: pointer;
}
.admin-content {
  position: relative;
  width: 100vw;
  height: 100vh;
  background-color:pink;
}
</style>