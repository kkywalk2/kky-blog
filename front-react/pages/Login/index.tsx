import useInput from '@hooks/userInput';
import { Button, Form, Header, Input, Label, LinkContainer, Success } from '@pages/SignUp/style';
import { login } from '@services/index';
import useAuth from '@utils/userFetcher';
import React, { useCallback, useState } from 'react';

const LogIn = () => {
    const [nickname, onChangeNickname] = useInput('');
    const [password, onChangePassword] = useInput('');
    const { setAuth, isLogin } = useAuth()
    const onSubmit = useCallback(
        async (e: any) => {
          e.preventDefault();
          if (!nickname || !nickname.trim()) {
            return;
          }
          
          const token = await login(nickname, password)

          if(token) {
            setAuth(token)
          }

        },
        [nickname, password],
      );

    if(isLogin) return <div>이미 로그인 됨</div>
    
    return (
    <div id="container">
    <Header>Sleact</Header>
    <Form onSubmit={onSubmit}>
      <Label id="nickname-label">
        <span>닉네임</span>
        <div>
          <Input type="text" id="nickname" name="nickname" value={nickname} onChange={onChangeNickname}/>
        </div>
      </Label>
      <Label id="password-label">
        <span>비밀번호</span>
        <div>
          <Input type="password" id="password" name="password" value={password} onChange={onChangePassword}/>
        </div>
      </Label>
      <Button type="submit">회원가입</Button>
    </Form>
    <LinkContainer>
      이미 회원이신가요?&nbsp;
      <a href="/login">로그인 하러가기</a>
    </LinkContainer>
  </div>
  )
}

export default LogIn;
