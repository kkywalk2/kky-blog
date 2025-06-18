import React, { useState, useCallback, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getPosts } from '@/services';
import PostCard from '@/components/PostCard';

const PostList = () => {
  const navigate = useNavigate();
  const [data, setData] = useState([]);
  const [cursor, setCursor] = useState(null);
  const [hasMore, setHasMore] = useState(true);
  const [isLoading, setIsLoading] = useState(false);
  const limit = 10;
  const observerRef = useRef(null);
  const processedIds = useRef(new Set());

  const fetchData = useCallback(async () => {
    if (isLoading) return;
    
    setIsLoading(true);
    try {
      const newData = await getPosts(limit, cursor);
      
      // 중복 제거를 위해 새로운 데이터만 필터링
      const uniqueNewPosts = newData.posts.filter(post => !processedIds.current.has(post.id));
      
      // 처리된 ID들을 Set에 추가
      uniqueNewPosts.forEach(post => processedIds.current.add(post.id));
      
      setData(prevData => [...prevData, ...uniqueNewPosts]);
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

  const handleWriteClick = () => {
    const token = sessionStorage.getItem('token');
    if (!token) {
      navigate('/login');
    } else {
      navigate('/write');
    }
  };

  useEffect(() => {
    // 컴포넌트가 마운트될 때 데이터 초기화
    setData([]);
    setCursor(null);
    setHasMore(true);
    processedIds.current.clear();
    fetchData();
  }, []);

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold text-gray-900">최근 게시물</h1>
      </div>

      {/* Post List */}
      <div className="grid gap-6">
        {data.map((post) => (
          <PostCard
            key={post.id}
            id={String(post.id)}
            title={post.title}
            summary={post.summary}
            createdAt={post.createdAt}
          />
        ))}
      </div>

      {/* Loading Indicator */}
      <div ref={observer} className="py-4">
        {isLoading && (
          <div className="flex justify-center items-center">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600"></div>
          </div>
        )}
      </div>

      {/* FAB Button */}
      <button
        onClick={handleWriteClick}
        className="fixed bottom-8 right-8 w-14 h-14 bg-indigo-600 text-white rounded-full shadow-lg hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 flex items-center justify-center transition-transform hover:scale-110"
        aria-label="글 작성하기"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-6 w-6"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth={2}
            d="M12 4v16m8-8H4"
          />
        </svg>
      </button>
    </div>
  );
};

export default PostList;
