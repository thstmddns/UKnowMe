<template>
  <div class="notice-detail-modal-title">
    <h2 class="notice-detail-title">{{admin.notice.title}}</h2>
  </div>
  <div class="notice-detail-modal-content">
    <p class="notice-detail-content">{{admin.notice.content}}</p>
  </div>
  <div class="notice-btn-list">
    <!-- {{admin.notice}} -->
    <button class="admin-btn" @click="admin.noticeBtn = 2">수정</button>
    <button @click="admin.deleteNotice(admin.notice.seq)" class="admin-btn">삭제</button>
  </div>
</template>

<script>
import { storeToRefs } from "pinia";
import { useAdminStore } from "@/stores/admin/admin";

export default {
  name: "NoticForm",
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
.notice-detail-modal-title {
  /* margin: 8%; */
  padding: 2% 11%;
}
.notice-detail-title {
  margin-bottom: 0;
}
.notice-detail-modal-content {
  border: 1px solid #c1c3fc;
  margin: 7%;
  height: 220px;
}
.notice-detail-content {
  padding: 4%;
  margin: 0;
}
.notice-btn-list {
  margin: 3%;
  display: flex;
  justify-content: space-around;
}
</style>