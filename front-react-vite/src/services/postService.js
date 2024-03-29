import axios from 'axios';

const createPost = (token, title, content, category) => {
  let headers = {
    'Content-type': 'application/json',
    Authorization: `${token}`,
  };

  let postInfo = {
    title: title,
    content: content,
    category: category,
  };

  return axios.post(`/posts`, postInfo, { headers });
};

const getPosts = (
  page,
  perPage,
  category,
  title,
) => {
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

  return axios.get(`/posts?page=${page}&per_page=${perPage}${query}`, { headers });
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
    Authorization: `${token}`,
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
