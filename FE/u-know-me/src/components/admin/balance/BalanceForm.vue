<template>
  <div class="balance-form">
    <h1>밸런스 게임 추가</h1>
    <form action="post" @submit.prevent="admin.addBalance(balanceValue)">
      <div>
        <input type="text" v-model="balanceValue.question" placeholder="question을 입력해주세요."/>
        <input type="text" v-model="balanceValue.answer1" placeholder="answer1을 입력해주세요." />
        <input type="text" v-model="balanceValue.answer2" placeholder="answer2를 입력해주세요."/>
      </div>
      <button class="admin-btn admin-balance-btn">추가</button>
    </form>
  </div>
</template>

<script>
import {useAdminStore} from "@/stores/admin/admin"
import { ref } from "vue"

  export default {
    name: "BalanceForm",
    setup() {
      const admin = useAdminStore();
      const balanceValue = ref({
        question: '',
        answer1: '',
        answer2: ''
      })
      return {
        admin,
        balanceValue
      }
    },
    methods: {
      submit() {
        const {question, answer1, answer2} = this;
        if(!question || !answer1 || !answer2){
          alert("모든 항목을 입력해주세요.")
        }
        this.$emit('submit', {question, answer1, answer2 })
      }
    }
  }

</script>


<style>
.balance-form {
  width: 100%;
  background-color: pink;
}
.admin-balance-btn {
  /* position: fixed; */
  bottom: 1%;
  right: 1%;
  width: 100px;
  height: 40px;
  margin: 10px;
}
</style>