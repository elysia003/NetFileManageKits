import {createRouter, createWebHashHistory} from 'vue-router'
import Index from '../components/IndexPage.vue'
import List from '../components/ListPage.vue'
import Image from '../components/ImagePage.vue'
const routes = [
    {
        path: '/',
        name: 'Index',
        component: Index,
        meta: {
            title: '首页',
            keepAlive: true,
            key: 'index'
        }
    }, {
        path: '/ListPage',
        name: 'List',
        component: List,
        meta: {
            keepAlive: true,
            key:'list'
        }
    }, {
        path: '/ImagePage',
        name: 'Image',
        component: Image,
        meta: {
            keepAlive: false,
            key:'image'
        }
    }
]
const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
