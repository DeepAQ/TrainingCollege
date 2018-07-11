import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import CourseList from '@/components/CourseList'
import CourseDetail from '@/components/CourseDetail'
import MyCenter from '@/components/MyCenter'
import EmailVerify from '@/components/student/EmailVerify'
import StudentProfile from '@/components/student/Profile'
import StudentTerminate from '@/components/student/Terminate'
import StudentOrderList from '@/components/student/OrderList'
import StudentWallet from '@/components/student/Wallet'
import StudentRecords from '@/components/student/Records'
import CollegeProfile from '@/components/college/Profile'
import CollegeStats from '@/components/college/Stats'
import CollegeCourses from '@/components/college/Courses'
import CollegePendingList from '@/components/college/PendingList'
import CollegeSale from '@/components/college/Sale'
import CollegeRecords from '@/components/college/Records'
import ManagerPermit from '@/components/manager/Permit'
import ManagerSettlements from '@/components/manager/Settlements'
import StudentAnalConsumption from '@/components/student/AnalConsumption'
import StudentAnalStudy from '@/components/student/AnalStudy'
import CollegeAnalIncome from '@/components/college/AnalIncome'
import CollegeAnalTeaching from '@/components/college/AnalTeaching'
import ManagerAnalIncome from '@/components/manager/AnalIncome'
import ManagerAnalTeaching from '@/components/manager/AnalTeaching'
import ManagerAnalPlatform from '@/components/manager/AnalPlatform'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: CourseList
    },
    {
      path: '/activate/:token',
      component: EmailVerify
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
          path: 'student/wallet',
          component: StudentWallet
        },
        {
          path: 'student/records',
          component: StudentRecords
        },
        {
          path: 'college/profile',
          component: CollegeProfile
        },
        {
          path: 'college/stats',
          component: CollegeStats
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
          path: 'college/sale',
          component: CollegeSale
        },
        {
          path: 'college/records',
          component: CollegeRecords
        },
        {
          path: 'manager/permit',
          component: ManagerPermit
        },
        {
          path: 'manager/settlements',
          component: ManagerSettlements
        },
        {
          path: 'student/anal-consumption',
          component: StudentAnalConsumption
        },
        {
          path: 'student/anal-study',
          component: StudentAnalStudy
        },
        {
          path: 'college/anal-income',
          component: CollegeAnalIncome
        },
        {
          path: 'college/anal-teaching',
          component: CollegeAnalTeaching
        },
        {
          path: 'manager/anal-income',
          component: ManagerAnalIncome
        },
        {
          path: 'manager/anal-teaching',
          component: ManagerAnalTeaching
        },
        {
          path: 'manager/anal-platform',
          component: ManagerAnalPlatform
        },
        {
          path: ':type/:op',
          component: HelloWorld
        }
      ]
    }
  ]
})
