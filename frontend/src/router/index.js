import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import CourseList from '@/components/CourseList'
import CourseDetail from '@/components/CourseDetail'
import MyCenter from '@/components/MyCenter'
import StudentProfile from '@/components/student/Profile'
import StudentTerminate from '@/components/student/Terminate'
import StudentOrderList from '@/components/student/OrderList'
import CollegeProfile from '@/components/college/Profile'
import CollegeCourses from '@/components/college/Courses'
import CollegePendingList from '@/components/college/PendingList'
import ManagerPermit from '@/components/manager/Permit'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: CourseList
    },
    {
      path: '/courses',
      component: CourseList
    },
    {
      path: '/detail/:id',
      component: CourseDetail
    },
    {
      path: '/my',
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
          path: 'student/orders',
          component: StudentOrderList
        },
        {
          path: 'college/profile',
          component: CollegeProfile
        },
        {
          path: 'college/courses',
          component: CollegeCourses
        },
        {
          path: 'college/orders',
          component: CollegePendingList
        },
        {
          path: 'manager/permit',
          component: ManagerPermit
        },
        {
          path: ':type/:op',
          component: HelloWorld
        }
      ]
    }
  ]
})
