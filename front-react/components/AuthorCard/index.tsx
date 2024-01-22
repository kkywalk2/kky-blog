import React from 'react';
import { UnstyledA } from './style';

const AuthorCard = () => {
  return (
    <div style={{ width: 300 }} className="card shadow-sm p-3 mb-5 bg-white rounded">
      <div className="card-image">
        <img
          style={{
            height: 100,
            width: '100%',
            maxWidth: 100,
          }}
          src="./assets/dog with glass.jpg"
          alt="주인장"
        />
      </div>
      <div className="card-content">
        <div className="media">
          <div className="media-content">
            <UnstyledA href="/">
                <h3> KKY's BLOG</h3>
            </UnstyledA>
            <a className="icon" href="https://www.linkedin.com/in/ki-young-kwon-168381193/">
              <i className="fab fa-linkedin fa-2x"></i>
            </a>
            &nbsp;
            <a className="icon" href="https://github.com/kkywalk2">
              <i className="fab fa-github fa-2x"></i>
            </a>
          </div>
        </div>

        <div className="content">
          안녕하세요 웹개발 관련 공부를 위해 만들고 있는 블로그 입니다.
          <br />
        </div>
      </div>
    </div>
  );
};

export default AuthorCard;
