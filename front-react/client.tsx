import React from 'react';
import { createRoot } from 'react-dom/client';

import App from '@layouts/App';
import { BrowserRouter } from 'react-router-dom';
import axios from 'axios';

axios.defaults.baseURL =
  process.env.NODE_ENV === 'production' ? 'https://cnt2020.hopto.org/blog/api' : 'http://localhost:9001/api';

const rootNode = document.querySelector('#app');

if (!rootNode) throw new Error('Failed to find the root element');

createRoot(rootNode).render(
  <BrowserRouter basename="/">
    <App />
  </BrowserRouter>,
);
