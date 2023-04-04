import React from 'react';
import { Badge, Card } from 'react-bootstrap';
import { useNavigate } from 'react-router';
import './style.css';

interface Props {
  id: string;
  title: string;
  content: string;
  createdAt: string;
}

const PostCard = ({ id, title, content, createdAt }: Props) => {
  const navigate = useNavigate();

  return (
    <Card border="secondary" style={{ margin: '10px' }} onClick={() => navigate(`/post/${id}`)}>
      <Card.Header>
        <h1>{title}</h1>
      </Card.Header>
      <Card.Body>
        <p>{content.substring(0, 100)}...</p>
        <Badge>Posted at {createdAt}</Badge>
      </Card.Body>
    </Card>
  );
};

export default PostCard;
