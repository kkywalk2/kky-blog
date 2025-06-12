import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import ReactMarkdown from 'react-markdown';
import { getPost, deletePost } from '@/services';

const Post = () => {
  const { postId } = useParams();
  const navigate = useNavigate();
  const [data, setData] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchData = async () => {
    try {
      setIsLoading(true);
      const postData = await getPost(postId);
      setData(postData);
      setError(null);
    } catch (err) {
      setError('게시물을 불러오는데 실패했습니다.');
      console.error('Error fetching post:', err);
    } finally {
      setIsLoading(false);
    }
  };

  const handleDelete = async () => {
    if (!window.confirm('정말로 이 게시물을 삭제하시겠습니까?')) {
      return;
    }

    try {
      const token = localStorage.getItem('token');
      if (!token) {
        navigate('/login');
        return;
      }

      const result = await deletePost(token, postId);
      if (result) {
        navigate('/');
      } else {
        setError('게시물 삭제에 실패했습니다.');
      }
    } catch (err) {
      setError('게시물 삭제 중 오류가 발생했습니다.');
    }
  };

  const handleEdit = () => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login');
      return;
    }
    navigate(`/edit/${postId}`);
  };

  useEffect(() => {
    fetchData();
  }, [postId]);

  if (isLoading) {
    return (
      <div className="flex justify-center items-center min-h-[400px]">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="text-center py-12">
        <p className="text-red-500">{error}</p>
        <button
          onClick={() => navigate('/')}
          className="mt-4 px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
        >
          홈으로 돌아가기
        </button>
      </div>
    );
  }

  if (!data) {
    return (
      <div className="text-center py-12">
        <p className="text-gray-500">게시물을 찾을 수 없습니다.</p>
        <button
          onClick={() => navigate('/')}
          className="mt-4 px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
        >
          홈으로 돌아가기
        </button>
      </div>
    );
  }

  return (
    <article className="bg-white rounded-lg shadow-sm">
      {/* Header */}
      <header className="border-b border-gray-200 p-6">
        <div className="flex justify-between items-start mb-4">
          <h1 className="text-3xl font-bold text-gray-900">{data.title}</h1>
          <div className="flex space-x-2">
            <button
              onClick={handleEdit}
              className="px-4 py-2 text-sm font-medium text-indigo-600 bg-indigo-50 rounded-md hover:bg-indigo-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              수정
            </button>
            <button
              onClick={handleDelete}
              className="px-4 py-2 text-sm font-medium text-red-600 bg-red-50 rounded-md hover:bg-red-100 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500"
            >
              삭제
            </button>
          </div>
        </div>
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-4">
            <span className="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-indigo-100 text-indigo-800">
              {data.category}
            </span>
            <span className="text-sm text-gray-500">
              {new Date(data.createdAt).toLocaleDateString('ko-KR', {
                year: 'numeric',
                month: 'long',
                day: 'numeric'
              })}
            </span>
          </div>
        </div>
      </header>

      {/* Content */}
      <div className="p-6 prose prose-sm sm:prose lg:prose-lg xl:prose-xl max-w-none">
        <ReactMarkdown>{data.content}</ReactMarkdown>
      </div>
    </article>
  );
};

export default Post;
