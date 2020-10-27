import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/home/Home.vue'
import { getToken } from '@/utils/auth' // 验权
import StudentList from '../views/home/components/StudentList.vue'
import DormList from '../views/home/components/DormList.vue'
import Census from '../views/home/components/Census.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/',
    name: 'Home',
    redirect:'/home/census',
  },
  {
    path:'/home',
    component:Home,
    children:[
      {
        path:'/home/census',
        component:Census
    
      },
      {
        path:'/home/student',
        component:StudentList
      },
      {
        path:'/home/dorm',
        component:DormList
      }
    ]
  },
  
  // {
  //   path: '/about',
  //   name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
 
]

const router = new VueRouter({
  routes
})
const whiteList = ['/login'] 
router.beforeEach((to, from, next) => {
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
    } else {
      next()
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next('/login')
    }
  }
})

export default router
