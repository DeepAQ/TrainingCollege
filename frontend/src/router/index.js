import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import MyCenter from '@/components/MyCenter'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/my',
      name: 'MyCenter',
      component: MyCenter,
      children: [
        {
          path: 'student/profile',
          component: HelloWorld
        },
        {
          path: ':type/:op',
          component: HelloWorld
        }
      ]
    }
  ]
})
