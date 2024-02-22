import React from 'react';
import {Link} from 'react-router-dom';

const PostCard = ({id, title, summary, createdAt}) => {
    return (
        <div className="block focus:outline-none focus:ring-4 h-40 rounded-md border-2">
            <Link to={`/posts/${id}`}>
                    {createdAt && (
                        <p className="uppercase mb-3 font-bold opacity-60">
                            {createdAt}
                        </p>
                    )}
                    <h2 className="text-2xl md:text-3xl">{title}</h2>
                    {summary && (
                        <div className='mx-auto p-4 h-20 truncate'>
                            {summary}
                        </div>
                    )}
            </Link>
        </div>
    );
};

export default PostCard;
