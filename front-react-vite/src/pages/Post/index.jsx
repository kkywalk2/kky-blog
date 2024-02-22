import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import MDEditor from '@uiw/react-md-editor';
import { getPost } from '@/services';

const Post = () => {
  const { postId } = useParams();
  const [ data, setData ] = useState(null);

  const fetchData = async () => {
    const postData = await getPost(postId);
    setData(postData);
  };

  useEffect(() => {
    fetchData();
  }, [])

  return (
    <div className='text-4xl font-bold w-3/4'>
      <h1>{data?.title}</h1>
      <div>
        <span className='bg-blue-100 text-blue-800 text-xs font-medium me-2 px-2.5 py-0.5 rounded dark:bg-blue-900 dark:text-blue-300'>{data?.category}</span>
        <div className="float-md-end">
          <button className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'>수정</button>
          <button className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'>삭제</button>
        </div>
      </div>
      <div className="markdownDiv" data-color-mode="light" style={{ padding: 15 }}>
        <MDEditor.Markdown source={data?.content} />
      </div>
    </div>
  );
};

export default Post;
