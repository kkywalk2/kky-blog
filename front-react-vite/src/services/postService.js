import apiClient from '@/api/axios';

const createPost = (token, title, content, category) => {
  const postInfo = {
    title,
    content,
    category,
  };

  return apiClient.post('/posts', postInfo);
};

const getPosts = (limit, cursor = null) => {
  const url = cursor
    ? `/posts?cursor=${cursor}&limit=${limit}`
    : `/posts?limit=${limit}`;

  return apiClient.get(url);
};

const getPost = (id) => {
  return apiClient.get(`/posts/${id}`);
};

const getCategories = () => {
  return apiClient.get('/posts/category');
};

const updatePost = (token, postId, title, content, category) => {
  const postInfo = {
    title,
    content,
    category,
  };

  return apiClient.put(`/posts/${postId}`, postInfo);
};

const deletePost = (token, postId) => {
  return apiClient.delete(`/posts/${postId}`);
};

export default { createPost, getPosts, getPost, getCategories, updatePost, deletePost };
