import React, { useState, useCallback, useRef, useEffect } from 'react';
import { getPosts } from '@/services';
import PostCard from '@/components/PostCard';

const PostList = () => {
  const [data, setData] = useState([]);
  const [cursor, setCursor] = useState(null);
  const [hasMore, setHasMore] = useState(true);
  const limit = 10;
  const observerRef = useRef(null);

  const fetchData = async () => {
    try {
      const newData = await getPosts(limit, cursor);
      
      setData((prevData) => uniqueArrayById([...prevData, ...newData.posts]));
      setCursor(newData.cursor);
      setHasMore(newData.cursor !== null);
    } catch (error) {
      console.error('Error fetching data: ', error);
    }
  };

  // React Strict 모드에서 2번 렌더링하는 것을 고려한 임시코드, 제대로 된 해결책 찾아야함
  const uniqueArrayById = (array) => {
    return array.reduce((acc, current) => {
      const existingItem = acc.find(item => item.id === current.id);
      if (!existingItem) {
        acc.push(current);
      }
      return acc;
    }, []);
  };

  const observer = useCallback((node) => {
    if (observerRef.current) {
      observerRef.current.disconnect();
    }

    observerRef.current = new IntersectionObserver(async ([entry]) => {
      if (entry.isIntersecting && hasMore) {
        await fetchData();
      }
    });

    node && observerRef.current.observe(node);
  }, [cursor, hasMore]);

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
          ></PostCard>
        ))}
      </ul>
      <div ref={observer} style={{ textAlign: 'center' }}>
        {hasMore && <p>Loading...</p>}
      </div>
    </div>
  );
};

export default PostList;
