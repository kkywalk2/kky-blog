// src/router/index.js
import Vue from 'vue'
import Router from 'vue-router'
import BlogMain from '@/components/BlogMain'
import Login from '@/components/Login' // 로그인 컴포넌트를 import 한다
import BlogEditor from "@/components/BlogEditor";
import Post from "@/components/Post";
import SignUp from "@/components/SignUp"

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/login', // 첫 화면을 로그인 화면으로 설정한다
      name: 'Login',
      component: Login
	},
    {
      path: '/', // 추가하는 path
      name: 'Blog',
      component: BlogMain // 추가하는 컴포넌트
    },
    {
      path: '/category/:name', // 추가하는 path
      name: 'BlogCategory',
      component: BlogMain // 추가하는 컴포넌트
    },
    {
      path: '/editor', // 추가하는 path
      name: 'Editor',
      component: BlogEditor // 추가하는 컴포넌트
    },
    {
      path: '/post/:id', // 추가하는 path
      name: 'Post',
      component: Post // 추가하는 컴포넌트
    },
    {
      path: '/signup', // 추가하는 path
      name: 'SignUp',
      component: SignUp // 추가하는 컴포넌트
    }
  ]
})