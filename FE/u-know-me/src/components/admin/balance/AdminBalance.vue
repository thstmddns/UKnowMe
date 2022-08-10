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
              </tr>
            </thead>
          </table>
        </div>
        <div class="balance-table-content">
          <table>
            <tbody>
              <tr>
                <td class="num">번호</td>
                <td>게임 제목</td>
                <td>답1</td>
                <td>답2</td>
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

export default {
  name: "AdminBalance",
  components: {
    BalanceForm
  }
}
</script>

<style>
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