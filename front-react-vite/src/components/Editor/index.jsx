import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createPosts } from '@/services';
import ReactMarkdown from 'react-markdown';

const Editor = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    title: '',
    content: '',
    category: '',
  });
  const [error, setError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isSubmitting) return;
    setError('');
    setIsSubmitting(true);

    try {
      const token = sessionStorage.getItem('token');
      if (!token) {
        setError('로그인이 필요합니다.');
        return;
      }

      const result = await createPosts(token, formData.title, formData.content, formData.category);
      if (result) {
        navigate('/'); // 글 작성 성공 후 메인 페이지로 이동
      } else {
        setError('글 작성에 실패했습니다.');
      }
    } catch (err) {
      setError('글 작성 중 오류가 발생했습니다.');
    }
    setIsSubmitting(false);
  };

  return (
    <div className="max-w-7xl mx-auto p-4">
      <div className="mb-4">
        <h2 className="text-2xl font-bold">새 글 작성</h2>
      </div>

      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="space-y-4">
          <div>
            <label htmlFor="title" className="block text-sm font-medium text-gray-700 mb-1">
              제목
            </label>
            <input
              id="title"
              type="text"
              name="title"
              placeholder="제목을 입력하세요"
              value={formData.title}
              onChange={handleChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
              required
            />
          </div>
          <div>
            <label htmlFor="category" className="block text-sm font-medium text-gray-700 mb-1">
              카테고리
            </label>
            <input
              id="category"
              type="text"
              name="category"
              placeholder="카테고리를 입력하세요"
              value={formData.category}
              onChange={handleChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
              required
            />
          </div>
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <textarea
              name="content"
              placeholder="내용을 입력하세요 (마크다운 지원)"
              value={formData.content}
              onChange={handleChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500 min-h-[600px] font-mono"
              required
            />
          </div>
          <div className="prose prose-sm sm:prose lg:prose-lg xl:prose-xl max-w-none p-4 border border-gray-300 rounded-md min-h-[600px] overflow-auto">
            <ReactMarkdown>{formData.content || '미리보기'}</ReactMarkdown>
          </div>
        </div>

        {error && (
          <div className="text-red-500 text-sm">
            {error}
          </div>
        )}

        <div className="flex justify-end">
          <button
            type="submit"
            disabled={isSubmitting}
            className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            {isSubmitting ? '작성 중…' : '작성하기'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default Editor; 
