<template>
  <div class="notice-edit-container">
    <form action="POST" @submit.prevent="admin.updateNotice(n, admin.notice.seq)">
      <div class="notice-edit-group">
        <input type="text" class="notice-edit-title" v-model="n.title">
      </div>
      <div class="notice-edit-group">
        <textarea class="notice-edit-content" v-model="n.content"></textarea>
      </div>
      <div class="save-btn">
        <button class="admin-btn notice-save-btn">저장</button>
      </div>
    </form>
  </div>
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
.notice-edit-container {
  vertical-align: middle;
}
.notice-edit-group {
  display: flex;
  justify-content: center;
  margin: 7%;
}
.notice-edit-title {
  width: 100%;
  height: 30px;
  border: 1px solid #c1c3fc;
  padding-left: 14px; padding-right: 14px;
}
.notice-edit-modal-content {
  /* border: 2px solid #c1c3fc; */
  /* margin: 7%;
  height: 220px; */
}
.notice-edit-content {
  width: 100%;
  height: 170px;
  border: 1px solid #c1c3fc;
  padding: 16px 14px;
  background: #fff;
}
</style>