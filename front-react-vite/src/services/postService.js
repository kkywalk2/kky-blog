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

  return axios.post(`/post`, postInfo, { headers });
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

  return axios.get(`/post?page=${page}&per_page=${perPage}${query}`, { headers });
};

const getPost = (id) => {
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

  return axios.put(`/post/${postId}`, postInfo, { headers });
};

const deletePost = (token, postId) => {
  let headers = {
    'Content-type': 'application/json',
    Authorization: `${token}`,
  };

  return axios.delete(`/post/${postId}`, { headers });
};

export default { createPost, getPosts, getPost, getCategories, updatePost, deletePost };
