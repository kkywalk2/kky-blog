import React from 'react';
import { Link } from 'react-router-dom';
import ReactMarkdown from 'react-markdown';

const PostCard = ({ id, title, content, createdAt }) => {
  return (
    <Link to={`/posts/${id}`}>
      <div className="py-6 lg:py-10 px-6 lg:px-16 block focus:outline-none focus:ring-4 h-96 rounded-md border-2">
        {createdAt && (
          <p className="uppercase mb-3 font-bold opacity-60">
            {createdAt}
          </p>
        )}
        <h2 className="text-2xl md:text-3xl">{title}</h2>
        {content && (
          <div className='max-w-screen-md mx-auto p-4 h-60 truncate'>
            <ReactMarkdown
            children={content}
            components={{img:({node,...props})=><img style={{width:'0'}}{...props}/>}}
            />
          </div>
        )}
      </div>
    </Link>
  );
};

export default PostCard;
