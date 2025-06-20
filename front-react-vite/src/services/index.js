import accountService from '@/services/userService';
import commentService from '@/services/commentService';
import imageService from '@/services/imageService';
import postService from '@/services/postService';

const login = async (accountName, password) => {
  try {
    const res = await accountService.login(accountName, password);
    if (res.status === 200) {
      // localStorage.setItem("token", "Bearer " + res.data.token)
      return res.data.token;
    } else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const signUp = async (accountName, password, email) => {
  try {
    const res = await accountService.signUp(accountName, password, email);
    if (res.status === 201) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const createPosts = async (token, title, content, category) => {
  try {
    const res = await postService.createPost(token, title, content, category);
    if (res.status === 201) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const getPosts = async (limit, cursor = null) => {
  try {
    const res = await postService.getPosts(limit, cursor);
    if (res.status === 200) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const uploadImage = async (blob) => {
  try {
    const res = await imageService.uploadImage(blob);
    if (res.status === 201) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const getImage = async (path) => {
  try {
    const res = await imageService.getImage(path);
    if (res.status === 200) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const getPost = async (id) => {
  try {
    const res = await postService.getPost(id);
    if (res.status === 200) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const getCategories = async () => {
  try {
    const res = await postService.getCategories();
    if (res.status === 200) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const addComment = async (token, postId, comment) => {
  try {
    const res = await commentService.addComment(token, postId, comment);
    if (res.status === 201) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const checkAuthentication = async (token) => {
  try {
    const res = await accountService.checkAuthentication(token);
    if (res.status === 200) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const updatePost = async (token, postId, title, content, category) => {
  try {
    const res = await postService.updatePost(token, postId, title, content, category);
    if (res.status === 200) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const deletePost = async (token, postId) => {
  try {
    const res = await postService.deletePost(token, postId);
    if (res.status === 200) return res.data;
    else return null;
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
