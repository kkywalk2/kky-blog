import React from 'react';
import { Badge, Card } from 'react-bootstrap';
import { useNavigate } from 'react-router';
import './style.css';

interface Props {
  id: string;
  title: string;
  createdAt: string;
}

const PostCard = ({ id, title, createdAt }: Props) => {
  const navigate = useNavigate();

  return (
    <Card border="secondary" style={{ margin: '10px' }} onClick={() => navigate(`/post/${id}`)}>
      <Card.Header>
        <h1>{title}</h1>
      </Card.Header>
      <Card.Body>
        <p>summary is not avalable yet....</p>
        <div>
          <Badge>Posted at {createdAt}</Badge>
        </div>
      </Card.Body>
    </Card>
  );
};

export default PostCard;
