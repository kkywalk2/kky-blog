import React from 'react'
import ReactDOM from 'react-dom/client'
import App from '@/layouts/App.jsx'
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
import { AuthProvider } from '@/contexts/AuthContext';

// contextPath는 axios.js에서 처리됨

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
        {
          path: '/edit/:postId',
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
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>,
)
