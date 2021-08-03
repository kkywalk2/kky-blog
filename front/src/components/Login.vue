// src/components/Login.vue
<template>
  <section>
    <form v-on:submit="onSubmit">
      <b-field label="Name">
        <b-input placeholder="Enter your ID" v-model="accountName" ref="idLabel"/>
      </b-field>
      <b-field label="Password">
        <b-input type="password" placeholder="Enter your password" v-model="password"/>
      </b-field>
      <b-button label="login" native-type="submit"/>
    </form>
  </section>
</template>

<script>

import {login} from '@/service'

export default {
  name: 'Login',
  data: () => ({
    accountName: '',
    password: ''
  }),
  methods: {
    async onSubmit (e) {
      e.preventDefault(); //SPA에선 자동으로 화면 refresh하는 것을 방지해야함
      try {
        let loginResult = await login(this.accountName, this.password)
        if(loginResult) this.$router.push({name: 'Blog'})
        else alert("로그인에 실패하였습니다")
      } catch (err) {
        alert("로그인에 실패하였습니다")
        console.error(err)
        this.$refs.idLabel.focus()
      }
    }
  }
}
</script>