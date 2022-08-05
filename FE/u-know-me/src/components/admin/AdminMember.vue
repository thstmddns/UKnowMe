<template>
  <div class="admin-table">
    <div class="member">회원 관리(((이게 신고자와 피신고자로 들어오는데 여기서는 누적 횟수를 보여줘야 하는 건가?, 계정 정지는 관리자가 직접?)))</div>
    <div class="member-table">
      <table>
        <thead>
          <tr>
            <th>번호</th>
            <th>유저 아이디</th>
            <th>가입일</th>
            <th>신고 누적 횟수</th>
            <th>계정 상태</th>
          </tr>
        </thead>
      </table>
    </div>
    <div class="member-table-content">
      <table>
        <tbody>
          <tr>
            <td>번호</td>
            <td>아이디</td>
            <td>가입일</td>
            <td>신고 누적 횟수</td>
            <td>계정 상태</td>
          </tr>
          <tr>
            <td>번호</td>
            <td>아이디</td>
            <td>가입일</td>
            <td>신고 누적 횟수</td>
            <td>계정 상태</td>
          </tr>
          <tr>
            <td>번호</td>
            <td>아이디</td>
            <td>가입일</td>
            <td>신고 누적 횟수</td>
            <td>계정 상태</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
table{
  width:100%;
  table-layout: fixed;
}
.member {
  font-size: 30px;
  font-weight: 550;
}
.member-table{
  background-color: #f0f1ff;
  margin: 55px 0 0 0;
 }
.member-table-content{
  height:600px;
  overflow-x:auto;
  margin-top: 0px;
  border: 1px solid #f0f1ff;
}
.member-table-content::-webkit-scrollbar {
  width: 10px;
}
.member-table-content::-webkit-scrollbar-thumb {
  background: #c1c3fc;
  border-radius: 10px;
}
.admin-table {
  width: 90%;
  margin: 5%;
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