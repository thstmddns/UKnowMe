import { createWebHistory, createRouter } from "vue-router";

// import List from './components/컴포넌트명.vue';
import LandPageView from '@/views/land/LandPageView.vue'
import TestView from '@/views/land/TestView.vue'
import ChatView from '@/views/chat/ChatView.vue'
import MainView from '@/views/main/MainView.vue'
import AdminView from '@/views/admin/AdminView.vue'
import Test2View from '@/views/land/Test2View.vue'

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
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router; 
