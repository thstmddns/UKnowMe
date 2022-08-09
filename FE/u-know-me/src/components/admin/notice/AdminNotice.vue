<template>
  <div class="admin-table">
    <div class="notice">공지사항</div>
    <div style="text-align:left;">
      <button class="admin-btn admin-notice-btn" @click="admin.noticeFormBtn = true">글쓰기</button>
    </div>
    
    <table class="notice-table">
          <thead class="notice-table-title">
            <tr>
              <th></th>
              <th>생성자 이름</th>
              <th>공지 제목</th>
              <th>생성 시간</th>
              <th>삭제 버튼</th>
            </tr>
          </thead>
          <tbody class="notice-table-content">
            <tr v-for="(a, i) in admin.notices" :key="i" >
              <td>{{i}}</td>
              <td>{{a.name}}</td>
              <td @click="aaa(i)">{{a.title}}</td>
              <td>{{a.time}}</td>
              <td>다중 선택 박스로 삭제하는 법 알아보기</td>
            </tr>
          </tbody>
    </table>
    
  </div>
</template>

<script>
import { useAdminStore } from "@/stores/admin/admin";

export default {
  name: "AdminNotice",
  components: {  },
  setup() {
    const admin = useAdminStore();
    return {
      admin,
    }
  },
  methods: {
    aaa(i) {
      this.admin.noticeBtn = 1
      this.admin.notice = this.admin.notices[i]
      console.log(this.admin.notice)
    }
  }
}
</script>

<style>
.notice {
  font-size: 30px;
  font-weight: 550;
}
.admin-notice-btn {
  position: relative;
  top: 1%;
  left: 90%;
  width: 100px;
  height: 40px;
  margin: 0  0 15px 0;
}
.notice-table{
  /* border: none; */
 }
 .notice-table-title{
  background-color: #f0f1ff;
 }
.notice-table-content{
  height: 80%;
  overflow-x:auto;
  margin-top: 0px;
  border: 1px solid #f0f1ff;
}
.notice-table-content::-webkit-scrollbar {
  width: 10px;
}
.notice-table-content::-webkit-scrollbar-thumb {
  background: #c1c3fc;
  border-radius: 10px;
}
th{
  padding: 20px 15px;
  text-align: left;
  font-weight: 550;
  font-size: 15px;
  color: rgb(0, 0, 0);
  text-transform: uppercase;
}
td{
  padding: 15px;
  text-align: left;
  vertical-align:middle;
  font-weight: 300;
  font-size: 15px;
  color: rgb(0, 0, 0);
  border-bottom: solid 1px #f0f1ff;
}
</style>