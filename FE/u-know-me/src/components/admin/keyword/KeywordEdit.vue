<template>
  <div class="keyword-edit-container">
    <form action="POST" @submit.prevent="admin.updateKeyword(j, admin.keyword.seq)">
      <div class="keyword-edit-group">
        <input type="text" class="keyword-edit-title" v-model="j.keyword">
      </div>
      <div class="save-btn">
        <button class="admin-btn keyword-save-btn">저장</button>
      </div>
    </form>
  </div>
</template>


<script>
import { storeToRefs } from "pinia";
import { useAdminStore } from "@/stores/admin/admin";
import { ref } from "vue"

export default {
  name: "keywordForm",
  components: {},
  setup() {
    const admin = useAdminStore()
    const { keywordBtn } = storeToRefs(admin)
    const j = ref({
      keyword : admin.keyword.keyword,
    })
    return {
      admin,
      keywordBtn,
      j
    };
  },
}
</script>

<style>
.keyword-edit-container {
  vertical-align: middle;
}
.keyword-edit-group {
  display: flex;
  justify-content: center;
  margin: 7%;
}
.keyword-edit-title {
  margin-top: 70px;
  width: 100%;
  height: 80px;
  border: 1px solid #c1c3fc;
  padding-left: 14px; padding-right: 14px;
}
.keyword-edit-modal-content {
  border: 2px solid #c1c3fc;
  margin: 7%;
  height: 220px;
}
.keyword-edit-content {
  width: 100%;
  height: 20px;
  border: 1px solid #c1c3fc;
  padding: 16px 14px;
  background: #fff;
}
</style>