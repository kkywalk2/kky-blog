import React from 'react';
import { Link } from 'react-router-dom';

const PostCard = ({ id, title, content, createdAt }) => {
  return (
    <Link to={`/posts/${id}`}>
      <div className="py-6 lg:py-10 px-6 lg:px-16 block focus:outline-none focus:ring-4">
        {createdAt && (
          <p className="uppercase mb-3 font-bold opacity-60">
            {createdAt}
          </p>
        )}
        <h2 className="text-2xl md:text-3xl">{title}</h2>
        {content && (
          <p className="mt-3 text-lg opacity-60">
            {content.substring(0, 100)}...
          </p>
        )}
      </div>
    </Link>
  );
};

export default PostCard;
