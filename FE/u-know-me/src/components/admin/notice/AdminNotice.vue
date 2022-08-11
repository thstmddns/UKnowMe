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

    <table class="notice-table">
      <thead class="notice-table-title">
        <tr>
          <th class="num"></th>
          <th class="notice-name">생성자 이름</th>
          <th>공지 제목</th>
          <th>생성 날짜</th>
        </tr>
      </thead>
      <tbody class="notice-table-content">
        <tr v-for="(a, num) in admin.notices" :key="num">
          <td class="num">{{ num }}</td>
          <td class="notice-name">{{ a.member.name }}</td>
          <td class="notice-detail-active" @click="aaa(num)">{{ a.title }}</td>
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

    <div class="page_wrap">
      <div class="page_nation">
        <a href="#" class="active">1</a>
        <a href="#">2</a>
        <a href="#">3</a>
        <a href="#">4</a>
        <a href="#">5</a>
        <a href="#">6</a>
        <a href="#">7</a>
        <a href="#">8</a>
        <a href="#">9</a>
        <a href="#">10</a>
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
.admin-notice-btn {
  position: relative;
  top: 1%;
  left: 90%;
  width: 100px;
  height: 40px;
  margin: 0 0 15px 0;
}
.notice-table {
  /* border: none; */
}
.notice-table-title {
  background-color: #f0f1ff;
}
.notice-table-content {
  height: 80%;
  overflow-x: auto;
  margin-top: 0px;
  border: 1px solid #f0f1ff;
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
.notice-table-content::-webkit-scrollbar {
  width: 10px;
}
.notice-table-content::-webkit-scrollbar-thumb {
  background: #c1c3fc;
  border-radius: 10px;
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

.page_wrap {
  position: absolute;
  text-align: center;
  font-size: 0;
  bottom: 5%;
  left: 50%;
  transform: translate(-50%, 0%);
}
.page_nation {
  display: inline-block;
}
.page_nation .none {
  display: none;
}
.page_nation a {
  display: block;
  margin: 0 3px;
  float: left;
  border: 1px solid #c1c3fc;
  width: 28px;
  height: 28px;
  line-height: 28px;
  text-align: center;
  background-color: #fff;
  font-size: 13px;
  color: #999999;
  text-decoration: none;
}
.page_nation a.active {
  background-color: #c1c3fc;
  color: #fff;
  border: 1px solid #c1c3fc;
}
</style>
