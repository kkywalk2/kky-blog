import React from 'react';
import { Link } from 'react-router-dom';

const PostCard = ({ id, title, summary, createdAt }) => {
  return (
    <Link 
      to={`/posts/${id}`}
      className="block bg-white rounded-lg shadow-sm hover:shadow-md transition-shadow duration-200 overflow-hidden border border-gray-100"
    >
      <div className="p-6">
        {createdAt && (
          <p className="text-sm text-gray-500 mb-2">
            {new Date(createdAt).toLocaleDateString('ko-KR', {
              year: 'numeric',
              month: 'long',
              day: 'numeric'
            })}
          </p>
        )}
        <h2 className="text-xl font-semibold text-gray-900 mb-3 line-clamp-2">
          {title}
        </h2>
        {summary && (
          <p className="text-gray-600 line-clamp-3">
            {summary}
          </p>
        )}
      </div>
    </Link>
  );
};

export default PostCard;
