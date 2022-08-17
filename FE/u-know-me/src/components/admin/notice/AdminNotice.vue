<template>
  <div class="admin-table">
    <div class="notice">공지사항</div>
    <div style="text-align: left">
      <button
        class="admin-btn admin-notice-btn"
        @click="admin.noticeFormBtn = true"
      >
        글쓰기
      </button>
    </div>
    <div class="notice-table">
      <div class="notice-table-title">
        <table>
          <thead>
            <tr>
              <th class="num">번호</th>
              <th class="notice-name">생성자 이름</th>
              <th>공지 제목</th>
              <th>생성 날짜</th>
            </tr>
          </thead>
        </table>
      </div>
      <div class="notice-table-content">
        <table>
          <tbody>
            <tr v-for="(a, num) in admin.notices" :key="num" class="notice-detail-active" @click="aaa(num)">
              <td class="num">{{ num }}</td>
              <td class="notice-name">{{ a.member.name }}</td>
              <td>{{ a.title }}</td>
              <td class="notice-delete" @click="admin.deleteNotice(a.seq)">
                {{ a.createDate.slice(0, 10) }}&#160;&#160;&#160;<button
                  class="delete-btn"
                >
                  삭제
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

  </div>
</template>

<script>
import { useAdminStore } from "@/stores/admin/admin";

export default {
  name: "AdminNotice",
  components: {},
  setup() {
    const admin = useAdminStore();
    admin.fetchNotices();
    return {
      admin,
    };
  },
  methods: {
    aaa(num) {
      this.admin.noticeBtn = 1;
      this.admin.notice = this.admin.notices[num];
      console.log(this.admin.notice);
    },
  },
};
</script>

<style>
.num {
  margin: auto;
  text-align: center;
}
.notice-name {
  width: 15%;
}
.notice-delete {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.notice {
  font-size: 30px;
  font-weight: 550;
}
.notice-table-title {
  background-color: #f0f1ff;
}
.notice-table-content {
  height: 450px;
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
.admin-notice-btn {
  position: relative;
  top: 1%;
  left: 90%;
  width: 100px;
  height: 40px;
  margin: 0 0 15px 0;
}
.notice-detail-active:hover {
  background-color: #f0f1ff;
  cursor: pointer;
  font-weight: 600;
}
.delete-btn {
  width: 60px;
  height: 40px;
  padding: 10px;
  border-radius: 10px;
  border: 2px solid #c1c3fc;
  background: transparent;
  cursor: pointer;
}
.delete-btn:hover {
  background: red;
  border: none;
}
th {
  padding: 20px 15px;
  text-align: left;
  font-weight: 550;
  font-size: 15px;
  color: rgb(0, 0, 0);
  text-transform: uppercase;
}
td {
  padding: 15px;
  text-align: left;
  vertical-align: middle;
  font-weight: 300;
  font-size: 15px;
  color: rgb(0, 0, 0);
  border-bottom: solid 1px #f0f1ff;
}
</style>
