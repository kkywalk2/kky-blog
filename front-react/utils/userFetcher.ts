import useSWR from 'swr';

import { checkAuthentication } from '@services/index';
import { useEffect, useState } from 'react';

const userFetcher = async (token: string): Promise<boolean> => {
  return await checkAuthentication(token);
};

export default function useAuth() {
  const [token, setToken] = useState<null | string>(localStorage.getItem('token'));

  const setAuth = (token: string) => {
    setToken(`Bearer ${token}`);
  };

  const {
    data: isLogin,
    mutate,
    error,
  } = useSWR(token, userFetcher, {
    dedupingInterval: 1000000,
  });

  // TODO: checkAuthentication에서 refresh token을 통해 access token을 받을 수 있도록 수정하기
  useEffect(() => {
    if (isLogin === false) {
      localStorage.removeItem('token');
    }
  }, [isLogin, error]);

  // 일단은 access token만 사용..., token을 localstorage에 저장하는 것은 보안적으로 좋지 않음!
  useEffect(() => {
    if (token !== null) {
      localStorage.setItem('token', token);
    } else {
      localStorage.removeItem('token');
    }
  }, [token]);

  useEffect(() => {
    // Sync all tabs on login or logout
    window.addEventListener('storage', (e) => {
      if (e.key === 'token') {
        setToken(e.newValue);
      }
    });
  });

  return {
    token,
    setAuth,
    isLogin,
  };
}
