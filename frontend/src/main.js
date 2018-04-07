// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import iView from 'iview'
import './styles/base.less'

Vue.config.productionTip = false

Vue.use(iView)

window.$state = new Vue({
  data: {
    loginName: localStorage.loginName,
    loginType: localStorage.loginType,
    loggedIn: !!localStorage.loginType
  },
  methods: {
    login (token, name, type) {
      localStorage.token = token
      this.loginName = localStorage.loginName = name
      this.loginType = localStorage.loginType = type
      this.loggedIn = true
    },
    logout () {
      delete localStorage.token
      delete localStorage.loginName
      delete localStorage.loginType
      this.loginName = this.loginType = ''
      this.loggedIn = false
    }
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
