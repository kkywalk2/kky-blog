import accountService from '@/services/userService';
import commentService from '@/services/commentService';
import imageService from '@/services/imageService';
import postService from '@/services/postService';

const login = async (accountName, password) => {
  try {
    const res = await accountService.login(accountName, password);
    return res.status === 200 ? res.data.token : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const signUp = async (accountName, password, email) => {
  try {
    const res = await accountService.signUp(accountName, password, email);
    return res.status === 201 ? res.data : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const createPosts = async (token, title, content, category) => {
  try {
    const res = await postService.createPost(token, title, content, category);
    return res.status === 201 ? res.data : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const getPosts = async (limit, cursor = null) => {
  try {
    const res = await postService.getPosts(limit, cursor);
    return res.status === 200 ? res.data : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const uploadImage = async (blob) => {
  try {
    const res = await imageService.uploadImage(blob);
    return res.status === 201 ? res.data : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const getImage = async (path) => {
  try {
    const res = await imageService.getImage(path);
    return res.status === 200 ? res.data : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const getPost = async (id) => {
  try {
    const res = await postService.getPost(id);
    return res.status === 200 ? res.data : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const getCategories = async () => {
  try {
    const res = await postService.getCategories();
    return res.status === 200 ? res.data : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const addComment = async (token, postId, comment) => {
  try {
    const res = await commentService.addComment(token, postId, comment);
    return res.status === 201 ? res.data : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const checkAuthentication = async (token) => {
  try {
    const res = await accountService.checkAuthentication(token);
    return res.status === 200 ? res.data : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const updatePost = async (token, postId, title, content, category) => {
  try {
    const res = await postService.updatePost(token, postId, title, content, category);
    return res.status === 200 ? res.data : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const deletePost = async (token, postId) => {
  try {
    const res = await postService.deletePost(token, postId);
    return res.status === 200 ? res.data : null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

export {
  login,
  signUp,
  createPosts,
  getPosts,
  uploadImage,
  getImage,
  getPost,
  getCategories,
  addComment,
  checkAuthentication,
  updatePost,
  deletePost,
};
