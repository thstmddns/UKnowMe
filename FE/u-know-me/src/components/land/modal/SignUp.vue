<template>
  <div>
    계정 만들기
  </div>
  <form action="POST" @submit.prevent>
    <div>
      <div><label for="signInId">아이디</label></div>
      <div><input type="text" name="signInId" id="signInId"></div>
    </div>
    <div>
      <div><label for="signInId">닉네임</label></div>
      <div><input type="text" name="signInId" id="signInId"></div>
      <button>중복 확인</button>
    </div>
    <div>
      <div><label for="signInId">비밀번호</label></div>
      <div><input type="text" name="signInId" id="signInId"></div>
    </div>
    <div>
      <div><label for="signInId">비밀번호 확인</label></div>
      <div><input type="text" name="signInId" id="signInId"></div>
    </div>
    <div>
      <div><label for="signInId">이름</label></div>
      <div><input type="text" name="signInId" id="signInId"></div>
    </div>
    <div>
      <div><label for="signInId">생년월일</label></div>
      <div>
        <input type="text" name="signInId" id="signInId" placeholder="년(4자)">
        <select name="month" id="month">
          <option value="">월</option>
          <option value="1">1월</option>
          <option value="2">2월</option>
          <option value="3">3월</option>
          <option value="4">4월</option>
          <option value="5">5월</option>
          <option value="6">6월</option>
          <option value="7">7월</option>
          <option value="8">8월</option>
          <option value="9">9월</option>
          <option value="10">10월</option>
          <option value="11">11월</option>
          <option value="12">12월</option>
        </select>
        <input type="text" name="signInId" id="signInId" placeholder="일">
      </div>
    </div>
    <div>
      <div><label for="signInId">성별</label></div>
      <div>
        <input type="radio" name="signInId" id="signInId">
        <label for="signInId">남자</label>
        <input type="radio" name="signInId" id="signInId">
        <label for="signInId">여자</label>
      </div>
    </div>
    <div>
      <div><label for="signInId">흡연여부</label></div>
      <div>
        <input type="checkbox" name="signInId" id="signInId">
      </div>
    </div>
    <div>
      <div><label for="signInId">휴대폰 번호</label></div>
      <div><input type="text" name="signInId" id="signInId"></div>
      <button>인증</button>
    </div>
    <div>
      <div><label for="signInId">인증번호</label></div>
      <div><input type="text" name="signInId" id="signInId"></div>
    </div>
    <button>가입하기</button>
  </form>
</template>

<script>
export default {
 name: "SignUp"
}
</script>

<style>

</style>