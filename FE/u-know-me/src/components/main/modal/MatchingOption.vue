<template>
  <div class="sign-head">
    <div>매칭옵션</div>
  </div>
  <div class="option-content">
    옵션을 선택해 주세요
  </div>
  <form action="GET" id="matchingOptionForm" @submit.prevent="main.matchingOptionSelect(option)">
    <div>
      <div class="text-left"><label for="optionSmoking">흡연여부</label></div>
      <div>
        <select name="optionSmoking" id="optionSmoking" v-model="option.matchingSmoke">
          <option
            v-for="(smoke, idx) in smokes"
            :key="idx"
            :value="smokes_val[idx]"
          >{{ smoke }}</option>
        </select>
      </div>
    </div>
    <div class="option-input">
      <div class="text-left"><label for="optionMinAge">연하 나이</label></div>
      <div><input type="number" name="optionMinAge" id="optionMinAge" v-model="option.minAge" min="0" max="10"/></div>
    </div>
    <div class="option-input">
      <div class="text-left"><label for="optionMaxAge">연상 나이</label></div>
      <div><input type="number" name="optionMaxAge" id="optionMaxAge" v-model="option.maxAge" min="0" max="10"/></div>
    </div>
    <div class="option-input">
      <div class="text-left"><label for="optionMatching">매칭</label></div>
      <div>
        <select name="optionMatching" id="optionMatching" v-model="option.matchingRoom">
          <option
            v-for="(matching, idx) in matchings"
            :key="idx"
            :value="matchings_val[idx]"
          >{{ matching }}</option>
        </select>
      </div>
    </div>
    <button type="submit" class="option-btn">확인</button>
  </form>
</template>

<script>
import { useMainStore } from "@/stores/main/main";
import { useAccountStore } from "@/stores/land/account";
import { ref } from 'vue'

export default {
  name: "MatchingOption",
  components: {},
  data() {
    return {
      smokes: ['상관없음', '흡연', '비흡연'],
      smokes_val: ['0', '1', '2'],
      matchings: ['1대1', '2대2'],
      matchings_val: ['1', '2']
    }
  },
  setup() {
    const main = useMainStore();
    const account = useAccountStore();
    const option = ref({
      minAge: 3,
      maxAge: 3,
      matchingRoom: '1',
      matchingSmoke: '0',
    })
    return {
      main,
      account,
      option
    };
  },
};
</script>

<style>
#matchingOptionForm div label {
  width: 44px;
  height: 20px;
  font-weight: 600;
  font-size: 16px;
  line-height: 20px;
  color: #000000;
}
#matchingOptionForm div input, select {
  box-sizing: border-box;
  width: 220px;
  height: 36px;
  background: #FFFFFF;
  border: 1px solid #C1BBBB;
  border-radius: 8px;
  padding: 10px;
}
.option-content {
  font-weight: 600;
  margin-bottom: 20px;
}
.option-input {
  margin-top: 12px;
}
.option-btn {
  width: 220px;
  margin-top: 32px;
}
</style>
