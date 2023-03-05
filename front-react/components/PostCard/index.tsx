import React from 'react';
import { Badge, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router';

interface Props {
  id: string;
  title: string;
  createdAt: string;
}

const PostCard = ({ id, title, createdAt }: Props) => {
  const navigate = useNavigate();

  return (
    <Row onClick={() => navigate(`/post/${id}`)}>
      <h1>{title}</h1>
      <p>summary is not avalable yet....</p>
      <div>
        <Badge>Posted at {createdAt}</Badge>
      </div>
    </Row>
  );
};

export default PostCard;
