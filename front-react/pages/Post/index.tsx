import { IPost } from '@typings/Post';
import fetcher from '@utils/fetcher';
import React from 'react';
import { useParams } from 'react-router';
import useSWR from 'swr';
import MDEditor from '@uiw/react-md-editor';
import { Badge, Button } from 'react-bootstrap';

const Post = () => {
  const { id } = useParams<{ id: string }>();
  const { data } = useSWR<IPost>(`/post/${id}`, fetcher);

  return (
    <div>
      <h1>{data?.title}</h1>
      <div>
        <Badge>{data?.category}</Badge>
        <div className="float-md-end">
          <Button style={{ marginRight: '10' }}>수정</Button>
          <Button>삭제</Button>
        </div>
      </div>
      <div className="markdownDiv" data-color-mode="light" style={{ padding: 15 }}>
        <MDEditor.Markdown source={data?.content} />
      </div>
    </div>
  );
};

export default Post;
