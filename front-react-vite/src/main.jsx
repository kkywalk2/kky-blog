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

const mode = import.meta.env.MODE;
axios.defaults.baseURL = mode === 'production' ? 'https://cnt2020.hopto.org/api' : 'http://localhost:9001/api';

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
        },
      ],
    },
  ],
);

window.addEventListener('vite:preloadError', (event) => {
  console.log(event);
})

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)
