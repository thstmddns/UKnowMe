import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import { createPinia } from 'pinia'
import Vue3GoogleLogin from "vue3-google-login";

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
.use(router)
.use(Vue3GoogleLogin, {
  clientId: "126204385632-ibuafqcdp8og2o1qevlhrrcs6u7vvut1.apps.googleusercontent.com",
  scope: 'profile email https://www.googleapis.com/auth/plus.login'
})
.mount('#app')
