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
                <th>밸런스 게임 제목</th>
                <th>답1</th>
                <th>답2</th>
                <th>수정</th>
                <th>삭제</th>
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
                <td class ="answer2">{{ list.answer2 }}</td>
                <td>
                  <button type="button" class="btn brn--modify" @click="admin.updateBalance(list.seq)">수정</button>
                </td>
                <td>
                  <button type="button" class="btn btn--modify" @click="admin.deleteBalance(list.seq)">삭제</button>
                </td>
                
                <!-- <td class="balance-delete" @click="admin.deleteBalance(list.seq)">{{ list.createDate.slice(0, 10) }}&#160;&#160;&#160;<button class="delete-btn">삭제</button></td> -->
              </tr>
            </tbody>
          </table>
            <div class="page_wrap">
              <div class="page_nation">
                <a href="#" class="active">1</a>
                <a href="#">2</a>
                <a href="#">3</a>
                <a href="#">4</a>
                <a href="#">5</a>
                <a href="#">6</a>
                <a href="#">7</a>
                <a href="#">8</a>
                <a href="#">9</a>
                <a href="#">10</a>
              </div>
            </div>
        </div>
      </div>
      <balance-form class="balance-add"/>
      <!-- <balance-edit class="balance-add"/> -->
    </div>
  </div>
    
</template>

<script>
import BalanceForm from "@/components/admin/balance/BalanceForm.vue"
import {useAdminStore} from "@/stores/admin/admin"
// import BalanceEdit from "@/components/admin/balance/BalanceEdit.vue"

export default {
  name: "AdminBalance",
  setup() { 
    const admin = useAdminStore()
    admin.getBalances()
    return{admin}
  },
  components: {
    BalanceForm
  },
}
</script>

<style>
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
.page_wrap {
  position: absolute;
	text-align:center;
	font-size:0;
  bottom: 5%;
  left: 50%;
  transform: translate(-50%, 0%);
 }
.page_nation {
	display:inline-block;
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
  height:600px;
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