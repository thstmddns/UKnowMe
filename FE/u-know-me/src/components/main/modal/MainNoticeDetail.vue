<template>
  <div class="main-notice-detail-title">
    {{ admin.notice.title }}
  </div>
  <div class="notice-detail-date"> 
    <div class="notice-date">{{ admin.notice.createDate.slice(0, 10) }}</div>
    <button class="back-btn" @click="btnCh=5">목록</button>
  </div>
  <hr>
  <div class="main-notice-detail-content">
    <pre>{{ admin.notice.content }}</pre>
  </div>
</template>

<script>
import { storeToRefs } from "pinia";
import { useAdminStore } from "@/stores/admin/admin";
import { useMainStore } from '@/stores/main/main'

export default {
  name: "NoticForm",
  components: {},
  setup() {
    const admin = useAdminStore()
    const main = useMainStore()
    const { btnCh } = storeToRefs(main);
    return {
      admin,
      btnCh
    };
  },
}
</script>

<style>
.notice-detail-date {
  display: flex;
  justify-content: space-between;
}
.main-notice-detail-title {
  width: 500px;
  font-size: 30px;
  font-weight: 700;
  margin-bottom: 5px;
}
.main-notice-detail-content {
  padding: 10px 0;
  font-size: 18px;
}
.notice-date {
  line-height: 30px;
}
.back-btn {
  width: 50px;
  height: 30px;
  padding: 5px;
  border: 2px solid #c1c3fc;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  background: #ffffff;
}
.back-btn:hover {
  background: #c1c3fc;
  color: #fff;
}
</style>