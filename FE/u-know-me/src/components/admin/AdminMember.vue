<template>
  <div class="admin-table">
    <div class="member">회원 관리</div>
      <table class="member-table">
        <thead class="member-table-title">
          <tr>
            <th class="num">번호</th>
            <th>유저 이름</th>
            <th>성별</th>
            <th>유저 아이디</th>
            <th>가입일</th>
            <th>신고 누적 횟수</th>
            <th>계정 상태</th>
          </tr>
        </thead>
        <tbody class="member-table-content">
          <tr v-for="(member, num) in admin.members" :key="num">
            <td class="num">{{num}}</td>
            <td>{{member.name}}</td>
            <td>{{member.gender}}</td>
            <td>{{member.id}}</td>
            <td>{{member.createDate.slice(0, 10)}}</td>
            <td>신고 누적 횟수</td>
            <td>계정 상태</td>
          </tr>
        </tbody>
      </table>
  </div>
</template>

<script>
import { useAdminStore } from "@/stores/admin/admin";

export default {
  name: "AdminMember",
  setup() {
    const admin = useAdminStore();
    admin.fetchMembers()
    return {
      admin,
    }
  },
}
</script>

<style>
.member {
  font-size: 30px;
  font-weight: 550;
}
.member-table{
  margin: 55px 0 0 0;
}
 .member-table-title{
  background-color: #f0f1ff;
}
.member-table-content{
  height: 80%;
  overflow-x: auto;
  margin-top: 0px;
  border: 1px solid #f0f1ff;
}
.member-table-content::-webkit-scrollbar {
  width: 10px;
}
.member-table-content::-webkit-scrollbar-thumb {
  background: #c1c3fc;
  border-radius: 10px;
}
</style>