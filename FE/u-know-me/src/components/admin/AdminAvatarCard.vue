<template>
  <div class="admin-avatar-card">
    <img :src="avatar.image" alt="" style="width:100%">
    <div class="admin-avatar-name">
      <h4><b>{{avatar.name}}</b></h4>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AdminAvatarCard',
  props: {
    avatar : Object,
  },
}
</script>

<style>
.admin-avatar-card {
  width: 200px;
  height: auto;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
  transition: 0.3s;
  border-radius: 5px;
  margin: 3%;
}
.admin-avatar-card:hover {
  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
}
.admin-avatar-name {
  /* padding: 2px 16px; */
  background-color: #f0f1ff;
  display: flex;
  justify-content: center;
}
/* img {
  border-radius: 5px 5px 0 0;
} */
</style>