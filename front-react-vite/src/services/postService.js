import axios from 'axios';

const createPost = (token, title, content, category) => {
  let headers = {
    'Content-type': 'application/json',
    Authorization: `Bearer ${token}`,
  };

  let postInfo = {
    title: title,
    content: content,
    category: category,
  };

  return axios.post(`/posts`, postInfo, { headers });
};

const getPosts = (limit, cursor = null) => {
  let headers = {
    'Content-type': 'application/json',
  };

  const url = cursor 
    ? `/posts?cursor=${cursor}&limit=${limit}`
    : `/posts?limit=${limit}`;

  return axios.get(url, { headers });
};

const getPost = (id) => {
  let headers = {
    'Content-type': 'application/json',
  };

  return axios.get(`/posts/${id}`, { headers });
};

const getCategories = () => {
  let headers = {
    'Content-type': 'application/json',
  };

  return axios.get(`/posts/category`, { headers });
};

const updatePost = (token, postId, title, content, category) => {
  let headers = {
    'Content-type': 'application/json',
    Authorization: `Bearer ${token}`,
  };

  let postInfo = {
    title: title,
    content: content,
    category: category,
  };

  return axios.put(`/posts/${postId}`, postInfo, { headers });
};

const deletePost = (token, postId) => {
  let headers = {
    'Content-type': 'application/json',
    Authorization: `${token}`,
  };

  return axios.delete(`/posts/${postId}`, { headers });
};

export default { createPost, getPosts, getPost, getCategories, updatePost, deletePost };
