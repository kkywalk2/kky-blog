import React from 'react'
import ReactDOM from 'react-dom/client'
import App from '@/layouts/App.jsx'
import axios from 'axios';
import '@/index.css'

import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";

import Post from '@/pages/Post';
import PostList from '@/pages/PostList';
import Login from '@/pages/Login';
import Editor from '@/components/Editor';
import {requireAuth} from "@/components/RequireAuth.jsx";

const mode = import.meta.env.MODE;
const contextPath = import.meta.env.VITE_CONTEXT_PATH || '';
const apiBasePath = contextPath ? `${contextPath}/api` : '/api';

axios.defaults.baseURL = mode === 'production' ? `https://cnt2020.hopto.org${apiBasePath}` : `http://localhost:9001${apiBasePath}`;

const router = createBrowserRouter(
  [
    {
      path: '/',
      element: <App />,
      children: [
        {
          path: '/',
          element: <PostList />,
        },
        {
          path: '/posts/:postId',
          element: <Post />,
        },
        {
          path: '/login',
          element: <Login />,
        },
        {
          path: '/write',
          element: <Editor />,
          loader: requireAuth,
        },
      ],
    },
  ],
  {
    basename: import.meta.env.VITE_BASE_PATH || '/'
  }
);

window.addEventListener('vite:preloadError', (event) => {
  console.log(event);
})

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)
