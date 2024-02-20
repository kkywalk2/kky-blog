import accountService from '@services/userService';
import commentService from '@services/commentService';
import imageService from '@services/imageService';
import postService from '@services/postService';

const login = async (accountName: string, password: string) => {
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

const signUp = async (accountName: string, password: string, email: string) => {
  try {
    const res = await accountService.signUp(accountName, password, email);
    if (res.status === 201) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const createPosts = async (token: string, title: string, content: string | undefined, category: string) => {
  try {
    const res = await postService.createPost(token, title, content, category);
    if (res.status === 201) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const getPosts = async (page: number, perPage: number, category: string | null = null, title: string | null = null) => {
  try {
    const res = await postService.getPosts(page, perPage, category, title);
    if (res.status === 200) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const uploadImage = async (blob: any) => {
  try {
    const res = await imageService.uploadImage(blob);
    if (res.status === 201) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const getImage = async (path: string) => {
  try {
    const res = await imageService.getImage(path);
    if (res.status === 200) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const getPost = async (id: string) => {
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

const addComment = async (token: string, postId: string, comment: string) => {
  try {
    const res = await commentService.addComment(token, postId, comment);
    if (res.status === 201) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const checkAuthentication = async (token: string | null) => {
  try {
    const res = await accountService.checkAuthentication(token);
    return res.status === 200;
  } catch (err) {
    return false;
  }
};

const updatePost = async (token: string, postId: string, title: string, content: string, category: string) => {
  try {
    const res = await postService.updatePost(token, postId, title, content, category);
    if (res.status === 200) return res.data;
    else return null;
  } catch (err) {
    console.error(err);
    return null;
  }
};

const deletePost = async (token: string, postId: string) => {
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
