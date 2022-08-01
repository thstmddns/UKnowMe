import { defineStore } from 'pinia'

export const useAvatarStore = defineStore('avatar', {
  state: () => ({
    avatar: [
      {id: 1, name: '미', image : require('@/assets/main/girl1.png')},
      {id: 2, name: '마땡이', image : require('@/assets/main/girl2.png')},
      {id: 3, name: '도레미', image : require('@/assets/main/girl3.png')},
      {id: 4, name: '유노', image : require('@/assets/main/boy1.png')},
      {id: 5, name: '보디다르마', image : require('@/assets/main/boy2.png')},
      {id: 6, name: '금태양', image : require('@/assets/main/boy3.png')},
      {id: 7, name: '클라디우스', image : require('@/assets/main/boy4.png')},
      {id: 8, name: '키츠네', image : require('@/assets/main/boy5.png')}
    ]
  }),
  getters: {

  },
  actions: {

  },
})