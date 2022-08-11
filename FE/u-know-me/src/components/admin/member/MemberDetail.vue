<template>
  <!-- <div class="member-info-container"> -->
    <div class="member-info-modal-title">
      <h2 class="member-info-title">{{admin.member.name}}</h2>
    </div>
    <div class="member-info-content">
      <p class="member-info-p">id:&#160;&#160;{{admin.member.id}}</p>
      <p class="member-info-p">닉네임:&#160;&#160;{{admin.member.nickname}}</p>
      <p class="member-info-p">성별:&#160;&#160;{{admin.member.gender}}</p>
      <p class="member-info-p">생년월일:&#160;&#160;{{admin.member.birth}}</p>
      <p class="member-info-p">휴대폰 번호:&#160;&#160;{{admin.member.tel}}</p>
      <p class="member-info-p">주소:&#160;&#160;{{admin.member.address}}</p>
      <p class="member-info-p">신고 누적 횟수:&#160;&#160;{{admin.member.accusedCount}}</p>
      <p class="member-info-p">가입일:&#160;&#160;{{admin.member.createDate.slice(0, 10)}}</p>
    </div>
  <!-- </div> -->
</template>

<script>
import { storeToRefs } from "pinia"
import { useAdminStore } from "@/stores/admin/admin"

export default {
  name: "MemberDetail",
  components: {},
  setup() {
    const admin = useAdminStore()
    const { noticeBtn } = storeToRefs(admin)

    return {
      admin,
      noticeBtn,
    };
  },
}
</script>

<style>
.member-info-container {
  width: 80%;
  height: 80%;
}
.member-info-modal-title {
  padding: 2% 11%;
}
.member-info-title {
  margin-bottom: 0;
}
.member-info-content {
  margin: 6% 7% 7% 12%;
  height: 70%;
  font-size: 17px;
}
.member-info-p {
  margin: 10px 0;
  font-size: 18px;
}
</style>