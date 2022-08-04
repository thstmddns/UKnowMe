const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy : 'http://uknowme.mooo.com:8000'
  }
})
