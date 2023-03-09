import useInput from '@hooks/userInput';
import { createPosts } from '@services/index';
import MDEditor from '@uiw/react-md-editor';
import useAuth from '@utils/userFetcher';
import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router';

const PostEditor = () => {
  const [markdownValue, setMarkdownValue] = useState<string | undefined>('');
  const [title, onChangeTitle] = useInput('');
  const [category, onChangeCategory] = useInput('');
  const { token } = useAuth();
  const navigate = useNavigate();

  const onSaveClick = async () => {
    if (token === null) return;
    await createPosts(token, title, markdownValue, category);
    navigate('/list');
  };

  return (
    <div>
      <div style={{ padding: 10 }}>
        <Form.Label>Title</Form.Label>
        <Form.Control value={title} onChange={onChangeTitle} placeholder="Enter Title.." />
        <Form.Label>Category</Form.Label>
        <Form.Control value={category} onChange={onChangeCategory} placeholder="Enter Category.." />
      </div>
      <div className="markarea" data-color-mode="light">
        <MDEditor height={865} value={markdownValue} onChange={setMarkdownValue} />
      </div>
      <Button onClick={onSaveClick}>저장</Button>
    </div>
  );
};

export default PostEditor;
