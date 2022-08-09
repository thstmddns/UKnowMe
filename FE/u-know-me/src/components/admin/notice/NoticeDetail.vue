<template>
  <div class="notice-detail-modal-title">
    <h2 class="notice-detail-title">{{admin.notice.title}}</h2>
  </div>
  <div class="notice-detail-modal-content">
    <h2 class="notice-detail-content">{{admin.notice.content}}</h2>
  </div>
  <button class="admin-btn" @click="admin.noticeBtn = 2">수정</button>
  <button class="admin-btn">삭제</button>
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

}

</style>