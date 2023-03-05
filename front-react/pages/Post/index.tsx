import React from 'react';
import { useParams } from 'react-router';

const Post = () => {
  const { id } = useParams<{ id: string }>();
  return <div>this is post page id is {id}</div>;
};

export default Post;
