<template>
  <div class="admin-table">
    <div class="notice">공지사항</div>
    {{ admin.noticeAddBtn}}
    <button class="admin-btn admin-notice-btn" @click="admin.noticeAddBtn = true">글쓰기</button>
    <div class="notice-table">
      <table>
        <thead>
          <tr>
            <th>생성자 이름</th>
            <th>공지 제목</th>
            <th>생성 시간</th>
            <th>삭제 버튼</th>
          </tr>
        </thead>
      </table>
    </div>
    <div class="notice-table-content">
      <table>
        <tbody>
          <tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr>
          <tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr>
          <tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr>
          <tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr>
          <tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr>
          <tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr>
          <tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr>
          <tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr>
          <tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr><tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr>
          <tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr>
          <tr>
            <td>박진경</td>
            <td>공지사항 끝</td>
            <td>지금 시간이다</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <notice-add v-if="admin.noticeAddBtn == true"/>
</template>

<script>
import { useAdminStore } from "@/stores/admin/admin";
import NoticeAdd from "@/components/admin/notice/NoticeAdd.vue";

export default {
  name: "AdminNotice",
  components: { NoticeAdd },
  setup() {
    const admin = useAdminStore();
    return {
      admin,
    }
  },
  
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
  background-color: #f0f1ff;
 }
.notice-table-content{
  height:600px;
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