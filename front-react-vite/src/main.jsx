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

axios.defaults.baseURL =
  process.env.NODE_ENV === 'production' ? 'https://cnt2020.hopto.org/blog/api' : 'http://localhost:9001/blog/api';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "lists",
        element: <PostList />,
      },
      {
        path: "posts/:postId",
        element: <Post />,
      },
    ],
  },
]);

window.addEventListener('vite:preloadError', (event) => {
  console.log(event);
})

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)
