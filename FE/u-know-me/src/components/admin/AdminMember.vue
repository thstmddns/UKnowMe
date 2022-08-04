<template>
  <div class="admin-table">
    <div class="member">회원 관리(((이게 신고자와 피신고자로 들어오는데 여기서는 누적 횟수를 보여줘야 하는 건가?, 계정 정지는 관리자가 직접?)))</div>
    <div class="member-table">
      <table>
        <thead>
          <tr>
            <th>신고자 이름</th>
            <th>피신고자 이름</th>
            <th>신고 시간</th>
            <th>계정 정지 버튼?</th>
          </tr>
        </thead>
      </table>
    </div>
    <div class="member-table-content">
      <table>
        <tbody>
          <tr>
            <td>박진경</td>
            <td>이동민</td>
            <td>지금은 새벽 두시.... 울고싶다..</td>
            <td>다중 선택 박스로 삭제하는 법 알아보기</td>
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