<template>
  <div class="admin-table">
    <div class="keyword">키워드 도우미</div>
    <div class="keyword-container">
      <div class="keyword-ex">
        <div class="keyword-table">
          <table>
            <thead>
              <tr>
                <th class="numb">번호</th>
                <th class="title">키워드 도우미</th>
                <th class="UD">수정</th>
                <th class="UD">삭제</th>
              </tr>
            </thead>
          </table>
        </div>
        <div class="keyword-table-content">
          <table>
            <tbody>
              <tr v-for="(list, j) in admin.keywordList" :key="j">
                <td class="num">{{ j }}</td>
                <td class="title">{{ list.keyword }}</td>
                <td class="UD">
                  <button type="button" class="btn btn--modify" @click="bbb(j)">수정</button>
                </td>
                <td class="UD">
                  <button type="button" class="btn btn--modify" @click="admin.deleteKeyword(list.seq)">삭제</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <keyword-form class="keyword-add"/>
    </div>
  </div>
    
</template>

<script>
import KeywordForm from "@/components/admin/keyword/KeywordFrom.vue"
import {useAdminStore} from "@/stores/admin/admin"


export default {
  name: "AdminKeyword",
  setup() { 
    const admin = useAdminStore()
    admin.getKeywords()
    return{admin}
  },
  components: {
    KeywordForm,
  },
  methods: {
    bbb(j) {
      this.admin.noticeBtn = 5;
      this.admin.keyword = this.admin.keywordList[j]
      console.log(this.admin.keyword)
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
.keyword-delete {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.deleteKeyword {
  width: 50;
}
.updateKeyword {
  width: 50;
}
.keyword {
  font-size: 30px;
  font-weight: 550;
}
.keyword-container {
  margin: 55px 0 0 0;
  display: flex;
}
.keyword-ex {
  width: 70%;
}
.keyword-add {
  width: 20%;
  margin: 0 5%;
}
.keyword-table{
  background-color: #f0f1ff;
 }
.keyword-table-content{
  height:600px;
  overflow-x:auto;
  margin-top: 0px;
  border: 1px solid #f0f1ff;
}
.keyword-table-content::-webkit-scrollbar {
  width: 10px;
}
.keyword-table-content::-webkit-scrollbar-thumb {
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