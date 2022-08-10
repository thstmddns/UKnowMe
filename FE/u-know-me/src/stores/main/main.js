import { defineStore } from 'pinia'

export const useMainStore = defineStore('main', {
  state: () => ({
    btnCh: 0,
    pBtnCh: 0,
    option: {
      minAge: 3,
      maxAge: 3,
      matchingRoom: '1',
      matchingSmoke: '0',
    }
  }),
  getters: {

  },
  actions: {
    matchingOptionSelect(option) {
      console.log({...option});
      this.options = option
      this.btnCh=0
    }
  },
})