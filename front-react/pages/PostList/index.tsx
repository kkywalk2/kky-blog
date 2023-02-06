import PostCard from '@pages/Post';
import React from 'react';
import useSWRInfinite from 'swr/infinite';
import { IPost, PostResponse } from '@typings/Post';
import InfiniteScroll from 'react-swr-infinite-scroll';
import fetcher from '@utils/fetcher';

const PAGE_SIZE = 5;

const PostList = () => {
    // TODO: 아직 완벽하게 이해하지 못함.., 페이지 로딩 시 마다 첫번째 페이지 호출하는 현상도 존재
    const swr = useSWRInfinite<IPost[]>(
        (index) => `/post?page=${index}&per_page=${PAGE_SIZE}`,
        async(url) => (await fetcher<PostResponse>(url)).content,
    );

    return(
        <div>
            <InfiniteScroll
            swr={swr}
            loadingIndicator="Loading..."
            endingIndicator="No more issues! 🎉"
            isReachingEnd={(swr) =>swr.data?.[0]?.length === 0 || swr.data?.[swr.data?.length - 1]?.length < PAGE_SIZE}>
            {(data: IPost[]) => data.map((post) => <PostCard title={post.title} createdAt={post.createdAt}></PostCard>)}
            </InfiniteScroll>
        </div>
    )
}

export default PostList;
