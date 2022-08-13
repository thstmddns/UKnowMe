<template>
  <div class="admin-table">
    <div class="balance">밸런스 게임</div>
    <div class="balance-container">
      <div class="balance-ex">
        <div class="balance-table">
          <table>
            <thead>
              <tr>
                <th class="num">번호</th>
                <th class="title">밸런스 게임 제목</th>
                <th class="answer1">답1</th>
                <th class="answer2">답2</th>
                <th class="UD">수정</th>
                <th class="UD">삭제</th>
              </tr>
            </thead>
          </table>
        </div>
        <div class="balance-table-content">
          <table>
            <tbody>
              <tr v-for="(list, i) in admin.balanceList" :key="i">
                <td class="num">{{ i }}</td>
                <td class="title">{{ list.question }}</td>
                <td class="answer1">{{ list.answer1 }}</td>
                <td class="answer2">{{ list.answer2 }}</td>
                <td class="UD">
                  <button type="button" class="btn btn--modify" @click="aaa(i)">수정</button>
                </td>
                <td class="UD">
                  <button type="button" class="btn btn--modify" @click="admin.deleteBalance(list.seq)">삭제</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <balance-form class="balance-add"/>
    </div>
  </div>
    
</template>

<script>
import BalanceForm from "@/components/admin/balance/BalanceForm.vue"
import {useAdminStore} from "@/stores/admin/admin"

export default {
  name: "AdminBalance",
  setup() { 
    const admin = useAdminStore()
    admin.getBalances()
    return{admin}
  },
  components: {
    BalanceForm,
  },
  methods: {
    aaa(i) {
      this.admin.noticeBtn = 4;
      this.admin.balance = this.admin.balanceList[i]
      console.log(this.admin.balance)
    }
  },
}
</script>

<style>
.title { 
  width: 40%;
}
.answer1 {
  width: 20%;
}
.answer2 {
  width: 20%;
}
.content {
  width: 97%;
}
.btn--modify {
  width: 90%;
  border: 1px solid #c1c3fc;
  border-radius: 10px;
}
.btn--modify:hover {
  background-color: #c1c3fc;
}

.UD {
  width: 20%;
  padding: 0;
}
.balance-delete {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.deleteBalance {
  width: 50;
}
.updateBalance {
  width: 50;
}
.balance {
  font-size: 30px;
  font-weight: 550;
}
.balance-container {
  margin: 55px 0 0 0;
  display: flex;
}
.balance-ex {
  width: 70%;
}
.balance-add {
  width: 20%;
  margin: 0 5%;
}
.balance-table{
  background-color: #f0f1ff;
 }
.balance-table-content{
  height:480px;
  overflow-x:auto;
  margin-top: 0px;
  border: 1px solid #f0f1ff;
}
.balance-table-content::-webkit-scrollbar {
  width: 10px;
}
.balance-table-content::-webkit-scrollbar-thumb {
  background: #c1c3fc;
  border-radius: 10px;
}
th{
  padding: 20px 15px;
  text-align: left;
  font-weight: 550;
  font-size: 15px;
  color: rgb(0, 0, 0);
  text-transform: uppercase;
}
td{
  padding: 15px;
  text-align: left;
  vertical-align:middle;
  font-weight: 300;
  font-size: 15px;
  color: rgb(0, 0, 0);
  border-bottom: solid 1px #f0f1ff;
}
</style>