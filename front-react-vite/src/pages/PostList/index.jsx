import React, { useState, useCallback, useRef, useEffect } from 'react';
import { getPosts } from '@/services';
import PostCard from '@/components/PostCard';

const PostList = () => {
  const [data, setData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const perPage = 10;
  const observerRef = useRef(null);

  const fetchData = async () => {
    try {
      const data = await getPosts(currentPage - 1, perPage);
      setTotalPages(data.totalPages);
      setData((prevData) => uniqueArrayById([...prevData, ...data.content]));
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
      if (entry.isIntersecting) {
        console.log(totalPages);
        if (currentPage < totalPages) {
          setCurrentPage((prevPage) => prevPage + 1);
        }
      }
    });

    node && observerRef.current.observe(node);
  }, [currentPage, totalPages]);

  useEffect(() => {
    fetchData();
  }, [currentPage])

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
        ))
        }
      </ul>
      <div ref={observer} style={{ textAlign: 'center' }}>
        {currentPage < totalPages && <p>Loading...</p>}
      </div>
    </div>
  );
};

export default PostList;
