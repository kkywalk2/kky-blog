import React, { useState, useCallback, useRef, useEffect } from 'react';
import { getPosts } from '@/services';
import PostCard from '@/components/PostCard';

const PostList = () => {
  const [data, setData] = useState([]);
  const [cursor, setCursor] = useState(null);
  const [hasMore, setHasMore] = useState(true);
  const [isLoading, setIsLoading] = useState(false);
  const limit = 10;
  const observerRef = useRef(null);

  const fetchData = useCallback(async () => {
    if (isLoading) return;
    
    setIsLoading(true);
    try {
      const newData = await getPosts(limit, cursor);
      
      setData(prevData => [...prevData, ...newData.posts]);
      setCursor(newData.cursor);
      setHasMore(newData.cursor !== null);
    } catch (error) {
      console.error('Error fetching data: ', error);
    } finally {
      setIsLoading(false);
    }
  }, [cursor, isLoading]);

  const observer = useCallback((node) => {
    if (observerRef.current) {
      observerRef.current.disconnect();
    }

    observerRef.current = new IntersectionObserver(([entry]) => {
      if (entry.isIntersecting && hasMore && !isLoading) {
        fetchData();
      }
    });

    node && observerRef.current.observe(node);
  }, [hasMore, isLoading, fetchData]);

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div className='justify-center w-full'>
      <ul>
        {data.map((post) => (
          <PostCard
            key={post.id}
            id={String(post.id)}
            title={post.title}
            summary={post.summary}
            createdAt={post.createdAt}
          />
        ))}
      </ul>
      <div ref={observer} className="py-4">
        {isLoading && (
          <div className="flex justify-center items-center">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-gray-900"></div>
          </div>
        )}
      </div>
    </div>
  );
};

export default PostList;
