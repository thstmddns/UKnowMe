<template>
  <div class="accuse-modal-bg">
    <div class="accuse-modal">
      <div class="close-btn" @click="accuseBtn = 0">
        <i class="fa-solid fa-xmark x-btn"></i>
      </div>
      <div class="accuse-modal-container">
        <div class="accuse-modal-title">신고하기</div>
        <div class="accuse-modal-content">
          상대의 <span class="bad-manners">비매너행위*</span> 시 신고를 하실 수 있습니다. <br>
          허위 신고로 판단될 경우 적절히 조치됩니다. <br>
          신고하시겠습니까?
        </div>
        <div class="accuse-modal-content-sub">(비매너행위* : 욕설, 혐오 발언, 부적절한 행동)</div>


        <div class="accuse-btn-list" v-for="(people, i ) in otherPeople" :people="people" :key="i">
          <button v-on:click="createReport(account.currentUser.seq, people.userSeq), accuseBtn = 0"
            class="accuse-btn">{{ people.userName }}</button>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { storeToRefs } from "pinia"
import { useChatStore } from "@/stores/chat/chat"
import { useAccountStore } from '@/stores/land/account'
import axios from 'axios'


export default {
  name: "AccuseModal",
  components: {
  },
  setup() {
    const chat = useChatStore()
    const { accuseBtn, otherPeople } = storeToRefs(chat)
    const account = useAccountStore();

    return {
      chat,
      accuseBtn,
      account,
      otherPeople,
    };
  },
  methods: {
    createReport(accusedMemberSeq, reportingMemberSeq) {
      axios({
        url: `https://uknowme.mooo.com:8443/report/create`,
        method: 'post',
        data: {
          "accusedMemberSeq": accusedMemberSeq,
          "reportState": "REPORT",
          "reportingMemberSeq": reportingMemberSeq
        }
      })
        .then(res => {
          console.log(res);
          const modalOff = document.querySelector(".close-btn");
          console.log(modalOff)

          modalOff.accuseBtn = 0;

        })
        .catch(err => {
          console.error(err.response)
        })
    }
  }
}

</script>

<style>
.accuse-modal-bg {
  width: 100vw;
  height: 100vh;
  z-index: 9;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
}

.accuse-modal {
  position: relative;
  width: 440px;
  min-height: 350px;
  background: #ffffff;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 27px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.accuse-modal-container {
  padding: 32px 40px;
  /* display: inline; */
}

.accuse-modal-title {
  padding: 2% 11%;
  text-align: center;
  font-size: 32px;
  font-weight: 700;
  color: #8227fa;
}

.accuse-modal-content {
  margin: 6% 0;
  text-align: center;
  line-height: 30px;
}

.accuse-modal-content-sub {
  font-weight: 700;
  text-align: center;
   color: rgb(153, 84, 199);
}

.accuse-btn-list {
  display: flex;
  justify-content: center;
  margin-top: 6%;
}

.accuse-btn {
  width: 200px;
  font-size: 15px;
  font-weight: 700;
}

.accuse-btn:hover {
  background-color: rgb(255, 51, 0);
  border: none;
}

.bad-manners {
  color: rgb(153, 84, 199);

}
</style>