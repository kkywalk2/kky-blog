import { IPost, PostResponse } from '@typings/Post';
import axios, { AxiosResponse } from 'axios';

const createPost = (token: string, title: string, content: string, category: string) => {
  let headers = {
    'Content-type': 'application/json',
    Authorization: `${token}`,
  };

  let postInfo = {
    title: title,
    content: content,
    category: category,
  };

  return axios.post(`/post`, postInfo, { headers });
};

const getPosts = (
  page: number,
  perPage: number,
  category: string | null,
  title: string | null,
): Promise<AxiosResponse<PostResponse, any>> => {
  let headers = {
    'Content-type': 'application/json',
  };

  let query = '';
  if (category !== null) {
    query += `&category=${category}`;
  }
  if (title !== null) {
    query += `&title=${title}`;
  }

  return axios.get<PostResponse>(`/post?page=${page}&per_page=${perPage}${query}`, { headers });
};

const getPost = (id: string) => {
  let headers = {
    'Content-type': 'application/json',
  };

  return axios.get(`/post/${id}`, { headers });
};

const getCategories = () => {
  let headers = {
    'Content-type': 'application/json',
  };

  return axios.get(`/post/category`, { headers });
};

const updatePost = (token: string, postId: string, title: string, content: string, category: string) => {
  let headers = {
    'Content-type': 'application/json',
    Authorization: `${token}`,
  };

  let postInfo = {
    title: title,
    content: content,
    category: category,
  };

  return axios.put(`/post/${postId}`, postInfo, { headers });
};

const deletePost = (token: string, postId: string) => {
  let headers = {
    'Content-type': 'application/json',
    Authorization: `${token}`,
  };

  return axios.delete(`/post/${postId}`, { headers });
};

export default { createPost, getPosts, getPost, getCategories, updatePost, deletePost };
