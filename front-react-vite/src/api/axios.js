import axios from 'axios';

const mode = import.meta.env.MODE;
const baseURL = mode === 'production'
  ? 'https://cnt2020.hopto.org/api'
  : 'http://localhost:9001/api';

const apiClient = axios.create({
  baseURL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor - add auth token
apiClient.interceptors.request.use(
  (config) => {
    const token = sessionStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor - handle errors globally
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    // Network error (no response)
    if (!error.response) {
      console.error('Network error:', error.message);
      return Promise.reject({ type: 'NETWORK_ERROR', message: 'Network connection failed' });
    }

    const { status, data } = error.response;

    // Handle specific status codes
    switch (status) {
      case 401:
        console.error('Unauthorized:', data);
        sessionStorage.removeItem('token');
        window.location.href = '/login';
        break;
      case 403:
        console.error('Forbidden:', data);
        break;
      case 404:
        console.error('Not found:', data);
        break;
      case 500:
        console.error('Server error:', data);
        break;
      default:
        console.error('API error:', status, data);
    }

    return Promise.reject({ type: 'HTTP_ERROR', status, data });
  }
);

export default apiClient;
