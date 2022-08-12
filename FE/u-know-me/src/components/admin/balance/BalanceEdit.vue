<template>
  <div class="balance-edit-container">
    <form action="POST" @submit.prevent="admin.updateBalance(i, admin.balance.seq)">
      <div class="balance-edit-group">
        <input type="text" class="balance-edit-title" v-model="i.question">
      </div>
      <div class="balance-edit-group">
        <textarea class="balance-edit-content" v-model="i.answer1"></textarea>
      </div>
      <div class="balance-edit-group">
        <textarea class="balance-edit-content" v-model="i.answer2"></textarea>
      </div>
      <div class="save-btn">
        <button class="admin-btn balance-save-btn">저장</button>
      </div>
      <!-- <button class="admin-btn " @click="admin.balanceBtn = 2">수정</button> -->

    </form>
  </div>
</template>


<script>
import { storeToRefs } from "pinia";
import { useAdminStore } from "@/stores/admin/admin";
import { ref } from "vue"

export default {
  name: "balanceForm",
  components: {},
  setup() {
    const admin = useAdminStore()
    const { balanceBtn } = storeToRefs(admin)
    const i = ref({
      question : admin.balance.question,
      answer1 : admin.balance.answer1,
      answer2 : admin.balance.answer2
    })
    return {
      admin,
      balanceBtn,
      i
    };
  },
}
</script>

<style>
.balance-edit-container {
  vertical-align: middle;
}
.balance-edit-group {
  display: flex;
  justify-content: center;
  margin: 7%;
}
.balance-edit-title {
  margin-top: 70px;
  width: 100%;
  height: 80px;
  border: 1px solid #c1c3fc;
  padding-left: 14px; padding-right: 14px;
}
.balance-edit-modal-content {
  border: 2px solid #c1c3fc;
  margin: 7%;
  height: 220px;
}
.balance-edit-content {
  width: 100%;
  height: 20px;
  border: 1px solid #c1c3fc;
  padding: 16px 14px;
  background: #fff;
}
</style>