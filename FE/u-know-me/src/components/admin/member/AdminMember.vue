<template>
  <div class="admin-table">
    <div class="member">회원 관리</div>
      <table class="member-table">
        <div class="member-table-title">
          <table>
            <thead>
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
          </table>
        </div>
        <div class="member-table-content">
          <table>
            <tbody>
              <tr v-for="(member, num) in admin.members" :key="num"
                  class="member-info-active" @click="goDetail(num)">
                <td class="num">{{num}}</td>
                <td>{{member.name}}</td>
                <td>{{member.gender}}</td>
                <td>{{member.id}}</td>
                <td>{{member.createDate.slice(0, 10)}}</td>
                <td>{{member.accusedCount}}</td>
                <td v-if="member.accusedCount > 5"><img src="@/assets/admin/red-img.png" class="condition-img"></td>
                <td v-else-if="member.accusedCount > 3"><img src="@/assets/admin/yellow-img.png" class="condition-img"></td>
                <td v-else><img src="@/assets/admin/green-img.png" class="condition-img"></td>
              </tr>
            </tbody>
          </table>
        </div>
      </table>
  </div>
</template>

<script>
import { useAdminStore } from "@/stores/admin/admin";

export default {
  name: "AdminMember",
  data() {
    return{
      cnt: this.admin.members
    }
  },
  setup() {
    const admin = useAdminStore();
    admin.fetchMembers()
    return {
      admin,
    }
  },
  methods: {
    goDetail(num) {
      this.admin.noticeBtn = 3
      this.admin.member = this.admin.members[num]
    }
  }
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
.member-table-title {
  background-color: #f0f1ff;
}
.member-table-content {
  height: 550px;
  overflow-x:auto;
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
.member-info-active:hover {
  background-color: #f0f1ff;
  cursor: pointer;
  font-weight: 600;
}
.condition-img {
  width: 30px;
  height: 30px;
}
</style>