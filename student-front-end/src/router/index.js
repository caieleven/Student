import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView";
import MainView from "@/views/MainView";
import Counsellor from "@/components/Counsellor";
import Student from "@/components/Student";
import App from "../App"
import BaseTable from "@/components/BaseTable";
import AdditionalTable from "@/components/AdditionalTable";
import Assistant from "@/components/Assistant";
import Home from "@/components/Home";
import NoticeView from "@/views/NoticeView";

const routes = [
    // {
    //     path: '/',
    //     name: 'main',
    //     component: MainView
    // },
    // {
    //     path: '/about',
    //     name: 'about',
    //     // route level code-splitting
    //     // this generates a separate chunk (about.[hash].js) for this route
    //     // which is lazy-loaded when the route is visited.
    //     component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    // },
    {
        path: '/',
        name: '登录',
        meta: {
            roles: [],
            show: false
        },
        component: LoginView
    },
    {
        path: '/main',
        name: '主页',
        meta: {
            show: true,
            roles: ['admin', 'counsellor', 'assistant']
        },
        component: Home
    },
    {
        path: '/notice',
        name: '公告',
        meta: {
            show: true,
            roles: ['admin', 'counsellor', 'assistant']
        },
        component: NoticeView
    },
    {
        path: '/userManager',
        name: '用户管理',
        meta: {
            show: true,
            roles: ['admin', 'counsellor']
        },
        component: MainView,
        children:[
            {
                path: '/counsellor',
                name: '教师管理',
                meta: {
                    roles: ['admin']
                },
                component: Counsellor
            },
            {
                path: '/assistant',
                name: '助手管理',
                meta: {
                    roles: ['admin', 'counsellor']
                },
                component: Assistant
            }
        ]
    },
    {
        path: '/studentManager',
        name: '学生信息管理',
        meta: {
            show: true,
            roles: ['admin', 'counsellor']
        },
        component: MainView,
        children: [
            {
                path: '/student',
                meta: {
                    roles: ['admin', 'counsellor']
                },
                name: '学生信息管理',
                component: Student
            }
        ]
    },
    {
        path: '/tableManager',
        name: '表管理',
        meta: {
            show: true,
            roles: ['admin', 'counsellor', 'assistant']
        },
        component: MainView,
        children: [
            {
                path: '/baseTable',
                name: '基本表管理',
                meta: {
                    roles: ['admin', 'counsellor']
                },
                component: BaseTable
            },
            {
                path: '/additionalTable',
                name: '附加表管理',
                meta: {
                    roles: ['admin', 'counsellor', 'assistant']
                },
                component: AdditionalTable
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
