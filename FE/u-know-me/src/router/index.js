import { createWebHistory, createRouter } from "vue-router";
// import { useAccountStore } from "@/stores/land/account";

// account
// import List from './components/컴포넌트명.vue';
import LandPageView from '@/views/land/LandPageView.vue'
import TestView from '@/views/land/TestView.vue'
import ChatView from '@/views/chat/ChatView.vue'
import MainView from '@/views/main/MainView.vue'
import AdminView from '@/views/admin/AdminView.vue'
import Test2View from '@/views/land/Test2View.vue'

import NotFound404 from '@/views/NotFound404.vue'


const routes = [
//   {
//     path: "/경로",
//     name: "이름",
//     component: import해온 컴포넌트,
//   },
  {
    path: "/",
    name: 'home',
    component: LandPageView,
  },
  {
    path: "/test",
    name: 'test',
    component: TestView,
  },
  {
    path: "/chat",
    name: 'chat',
    component: ChatView,
  },
  {
    path: "/main",
    name: 'main',
    component: MainView,
  },
  {
    path: "/admin",
    name: 'admin',
    component: AdminView,
  },
  {
    path: "/test2",
    name: 'test2',
    component: Test2View,
  },
  {
    path: '/404',
    name: 'NotFound404',
    component: NotFound404
  },
  {
    path: '/:notPage(.*)',
    redirect: '/404',
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// router.beforeEach(async(to, from, next) => {
//   const account = useAccountStore()
//   // 이전 페이지에서 발생한 에러메시지 삭제
//   account.authError = {
//     login: 0,
//   },
//   const noAuthPages = ['home', 'NotFound404']

//   const isAuthRequired = !noAuthPages.includes(to.name)

//   if (isAuthRequired && !account.isLoggedIn) {
//     next({ name: 'home' })
//   } else {
//     next()
//   }

//   if (!isAuthRequired && account.isLoggedIn) {
//     next({ name: to.name })
//   }
// })

export default router; 
