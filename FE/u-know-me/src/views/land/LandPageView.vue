<template>
  <SignModal
    v-if="btnCh !== 0"
  />
  <LandingPage/>
  <SideBar/>
</template>

<script>
import LandingPage from '@/components/land/LandingPage.vue'
import SideBar from '@/components/land/SideBar.vue'
import SignModal from '@/components/land/SignModal.vue'
import { storeToRefs } from 'pinia'
import { useLandStore } from '@/stores/land/land'

export default {
  name: 'LandPage',
  components: {
    LandingPage,
    SideBar,
    SignModal,
  },
  setup() {
    const land = useLandStore()
    const { btnCh } = storeToRefs(land)
    return {
      land,
      btnCh
    }
  },
}
</script>

<style>
body {
  margin: 0;
}
</style>