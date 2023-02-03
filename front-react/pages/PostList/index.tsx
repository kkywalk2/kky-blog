import PostCard from '@pages/Post';
import useAuth from '@utils/userFetcher';
import React from 'react';
import { Navigate } from 'react-router';

const PostList = () => {
    const { setAuth, isLogin } = useAuth()

    if(!isLogin) return <Navigate replace to="/login" />

    return(
        <div>
            <PostCard></PostCard>
            <PostCard></PostCard>
            <PostCard></PostCard>
            <PostCard></PostCard>
        </div>
    )
}

export default PostList;
