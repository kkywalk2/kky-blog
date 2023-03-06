import React, { CSSProperties } from 'react';
import loadable from '@loadable/component';
import { Route, Routes, Navigate } from 'react-router-dom';
import { Container, Row, Col, Button, Navbar, Nav } from 'react-bootstrap';
import AuthorCard from '@components/AuthorCard';
import Post from '@pages/Post';
import PostEditor from '@pages/PostEditor';

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
          <Navbar bg="dark" variant="dark" expand="lg">
            <Nav className="me-auto">
              <Nav.Link href="/post-editor">글쓰기</Nav.Link>
              <Nav.Link href="/signup">회원가입</Nav.Link>
            </Nav>
          </Navbar>
        </Row>
        <Row>
          <Col md="auto">
            <AuthorCard></AuthorCard>
          </Col>
          <Col md>
            <Routes>
              <Route path="/" element={<Navigate replace to="/login" />} />
              <Route path="/login" element={<Login />} />
              <Route path="/signup" element={<SignUp />} />
              <Route path="/list" element={<PostList />} />
              <Route path="/post/:id" element={<Post />} />
              <Route path="/post-editor" element={<PostEditor />} />
            </Routes>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default App;
