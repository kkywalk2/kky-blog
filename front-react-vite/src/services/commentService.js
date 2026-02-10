import apiClient from '@/api/axios';

const addComment = (token, postId, content) => {
  const postInfo = {
    postId,
    content,
  };

  return apiClient.post('/comment', postInfo);
};

export default { addComment };
