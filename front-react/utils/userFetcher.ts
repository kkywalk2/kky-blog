import useSWR from 'swr';

import { checkAuthentication } from '@services/index';
import { useEffect, useState } from 'react';

const userFetcher = async (token: string): Promise<boolean> => {
  return await checkAuthentication(token);
};

export default function useAuth() {
  const [token, setToken] = useState<null | string>(null);
  const [isLogin, setIsLogin] = useState(localStorage.getItem('isLogin') === 'true');
  const setAuth = (token: string) => {
    localStorage.setItem('isLogin', String(true));
    setToken(`Bearer ${token}`);
    setIsLogin(true);
  };
  const { data, mutate, error } = useSWR(token, userFetcher, {
    dedupingInterval: 1000000,
  });

  // TODO: checkAuthentication에서 refresh token을 통해 access token을 받을 수 있도록 수정하기
  /*useEffect(() => {
    if (data === true) {
      setIsLogin(true);
    } else {
      setIsLogin(false);
    }
  }, [data, error]);*/

  useEffect(() => {
    // Sync all tabs on login or logout
    window.addEventListener('storage', (e) => {
      if (e.key === 'isLogin') {
        setIsLogin(e.newValue === 'true');
      }
    });
  });

  return {
    setAuth,
    isLogin,
  };
}
