import React from 'react';
import { Link } from 'react-router-dom';
import dogWithGlass from '@/assets/dog with glass.jpg'

const AuthorCard = () => {
  return (
    <div className="card shadow-sm p-3 mb-5 bg-white border-solid border-black rounded-md border-2 h-72">
      <div className="card-image">
        <img
          style={{
            height: 100,
            width: '100%',
            maxWidth: 100,
          }}
          src={`${dogWithGlass}`}
          alt="주인장"
        />
      </div>
      <div className="card-content">
        <div className="media">
          <div className="media-content">
            <Link href="/">
                <h3> KKY's BLOG</h3>
            </Link>
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
