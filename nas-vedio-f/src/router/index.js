import {createRouter, createWebHashHistory} from 'vue-router'
import vedio from '../views/vedio/Home.vue'
import doc from '../views/doc/Home.vue'
import read from '../views/doc/read.vue'
import file from '../views/doc/Home.vue'
import App from '../App.vue'
const routes = [
  {
    path: '/',
    redirect:'/vedio',
    component: App,
  },
  {
    path: '/file',
    name:'file',
    component: file,
  },
  {
    path: '/vedio',
    name:'vedio',
    component: vedio,
  },
  {
    path: '/doc',
    name:'doc',
    component: doc,
    meta:{
      keepAlive:true
    }
  },
  {
    path: '/read/:id',
    component: read,
    meta:{
      keepAlive:false
    }
  },
  {
    path: '/vedio',
    component: vedio,
    meta: {
      keepAlive: false
    }
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
