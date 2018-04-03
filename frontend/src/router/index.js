import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import MyCenter from '@/components/MyCenter'
import StudentProfile from '@/components/student/Profile'
import StudentTerminate from '@/components/student/Terminate'
import CollegeProfile from '@/components/college/Profile'

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
          component: StudentProfile
        },
        {
          path: 'student/terminate',
          component: StudentTerminate
        },
        {
          path: 'college/profile',
          component: CollegeProfile
        },
        {
          path: ':type/:op',
          component: HelloWorld
        }
      ]
    }
  ]
})
