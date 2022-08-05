<template>
  <div class="admin-table">
    <div class="member">아바타 관리</div>
    <button class="admin-btn admin-avatar">아바타 추가</button>
    <div class="admin-avatar-collection">
      <div class="admin-avatar-card-container">
        <admin-avatar-card class="admin-avatar-card"
          v-for="(avatar, i) in avatars.avatar"
          :avatar="avatar"
          :key="i"
        />
      </div>
    </div>
  </div>
</template>

<script>
import AdminAvatarCard from "@/components/admin/AdminAvatarCard.vue";
import { useAvatarStore } from '@/stores/main/avatar'

export default {
  name: "AdminAvatar",
  components: { AdminAvatarCard },
  setup() {
    const avatars = useAvatarStore()
    return {
      avatars
    }
  }
};
</script>

<style>
.admin-table {
  width: 90%;
  margin: 5%;
}
.admin-avatar {
  position: relative;
  top: 1%;
  left: 90%;
  width: 100px;
  height: 40px;
  margin: 0  0 15px 0;
}
.admin-avatar-collection {
  height: 700px;
  overflow-x:auto;
  margin: auto;
  border: 1px solid #f0f1ff;
}
.admin-avatar-collection::-webkit-scrollbar {
  width: 10px;
}
.admin-avatar-collection::-webkit-scrollbar-thumb {
  background: #c1c3fc;
  border-radius: 10px;
}
.admin-avatar-card-container {
}
.admin-avatar-card {
  display: inline-block;
}
</style>