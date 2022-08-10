import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import { createPinia } from 'pinia'
import VueCookies from 'vue3-cookies'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
.use(router)
.use(VueCookies, {
  expireTimes: "1d",
})
.mount('#app')
