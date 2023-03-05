import React, { CSSProperties } from 'react';

const AuthorCard = () => {
  const imgSize: CSSProperties = {
    height: 100,
    width: '100%',
    maxWidth: 100,
  };

  return (
    <div className="card">
      <div className="card-image">
        <img style={imgSize} src="../assets/dog with glass.jpg" alt="주인장" />
      </div>
      <div className="card-content">
        <div className="media">
          <div className="media-content">
            <a className="plain" href="/">
              <p className="title is-4"> KKY's BLOG</p>
            </a>
            <br />
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
