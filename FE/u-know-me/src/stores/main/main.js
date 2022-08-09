import { defineStore } from 'pinia'

export const useMainStore = defineStore('main', {
  state: () => ({
    btnCh: 0,
    pBtnCh: 0,
    options: {
      minAge: 0,
      maxAge: 0,
      matchingRoom: '',
      matchingSmoke: '',
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