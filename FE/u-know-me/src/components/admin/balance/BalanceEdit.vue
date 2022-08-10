<template>
  <div class="balance-edit">
    <h1>밸런스 게임 수정</h1>
    <form action="POST" @submit.prevent="admin.updateBalance(j, admin.balance.seq)">
      <div>
        <input type="text" class="balance-edit" v-model="j.question">
        <input type="text" class="balance-edit" v-model="j.answer1">
        <input type="text" class="balance-edit" v-model="j.answer2">

        <!-- <input type="text" v-model="balanceValue.question" placeholder="question을 입력해주세요."/>
        <input type="text" v-model="balanceValue.answer1" placeholder="answer1을 입력해주세요." />
        <input type="text" v-model="balanceValue.answer2" placeholder="answer2를 입력해주세요."/> -->
      </div>

      <button class="admin-btn admin-balance-btn">저장</button>
    </form>
  </div>
</template>

<script>
import { useAdminStore } from '@/stores/admin/admin'
import { storeToRefs } from 'pinia'
import { ref } from "vue"

export default {
  name: "BalanceEdit",
  components: {},
  setup() {
    const admin = useAdminStore()
    const { balanceBtn } = storeToRefs(admin)
    const j = ref({
      question : admin.balance.question,
      answer1 : admin.balance.answer1,
      answer2 : admin.balance.answer2,
    })
    return {
      admin,
      balanceBtn,
      j
    }
  }

}
</script>

<style>
.balance-edit {
  width: 100%;
  background-color: pink;
}
.admin-balance-btn {
  /* position: fixed; */
  bottom: 1%;
  right: 1%;
  width: 50px;
  height: 40px;
  margin: 10px;
}
</style>