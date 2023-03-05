import React, { CSSProperties } from 'react';
import loadable from '@loadable/component';
import { Route, Routes, Navigate } from 'react-router-dom';
import { Container, Row, Col } from 'react-bootstrap';
import AuthorCard from '@components/AuthorCard';
import Post from '@pages/Post';

const Login = loadable(() => import('@pages/Login'));
const SignUp = loadable(() => import('@pages/SignUp'));
const PostList = loadable(() => import('@pages/PostList'));

const App = () => {
  const header: CSSProperties = {
    height: 100,
    backgroundColor: 'rgb(0, 0, 0)',
  };

  return (
    <div>
      <Container fluid>
        <Row>
          <div style={header} />
        </Row>
        <Row>
          <Col md={1}>
            <AuthorCard></AuthorCard>
          </Col>
          <Col md={2}>
            <Routes>
              <Route path="/" element={<Navigate replace to="/login" />} />
              <Route path="/login" element={<Login />} />
              <Route path="/signup" element={<SignUp />} />
              <Route path="/list" element={<PostList />} />
              <Route path="/post/:id" element={<Post />} />
            </Routes>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default App;
