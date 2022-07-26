<template>
  <button @click="cherry" class="cherry-blossom">벚꽃</button>
</template>

<script>
import sakura from './CherryBlossom.js'

export default {
  name: 'CherryBlossom',
  methods: {
    cherry() {
      sakura
    }
  }
}
</script>

<style>
.cherry-blossom {

}
@import 'CherryBlossom.css'
</style>