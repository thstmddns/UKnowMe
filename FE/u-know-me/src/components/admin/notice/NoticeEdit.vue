<template>
  <div class="notice-detail-modal-title">
    <input type="text" class="notice-detail-title" v-model="n.title">
  </div>
  <div class="notice-detail-modal-content">
    <textarea class="notice-detail-content" v-model="n.content"></textarea>
  </div>
  <button class="admin-btn">저장</button>
</template>

<script>
import { storeToRefs } from "pinia";
import { useAdminStore } from "@/stores/admin/admin";
import { ref } from "vue"
 
export default {
  name: "NoticForm",
  components: {},
  setup() {
    const admin = useAdminStore()
    const { noticeBtn } = storeToRefs(admin)
    const n = ref({
      title : admin.notice.title,
      content : admin.notice.content
    })

    return {
      admin,
      noticeBtn,
      n
    };
  },
}
</script>

<style>

</style>