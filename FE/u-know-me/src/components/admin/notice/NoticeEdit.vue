<template>
  <div class="notice-detail-modal-title">
    <h2 class="notice-detail-title">제목</h2>
  </div>
  <div class="notice-detail-modal-content">
    <h2 class="notice-detail-content">내용</h2>
  </div>
  <button class="admin-btn">저장</button>
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

</style>