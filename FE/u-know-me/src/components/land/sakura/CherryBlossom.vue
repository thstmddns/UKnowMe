<template>
  <button @click="cherry">벚꽃</button>
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
@import 'CherryBlossom.css'
</style>