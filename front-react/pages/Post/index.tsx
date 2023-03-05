import { PostDetailResponse } from '@typings/Post';
import fetcher from '@utils/fetcher';
import React from 'react';
import { useParams } from 'react-router';
import useSWR from 'swr';
import MDEditor from '@uiw/react-md-editor';
import { Badge } from 'react-bootstrap';

const Post = () => {
  const { id } = useParams<{ id: string }>();
  const { data } = useSWR<PostDetailResponse>(`/post/${id}`, fetcher);

  return (
    <div className="markdownDiv" data-color-mode="light" style={{ padding: 15 }}>
      <h1>{data?.title}</h1>
      <Badge>{data?.category}</Badge>
      <MDEditor.Markdown source={data?.content} />
    </div>
  );
};

export default Post;
