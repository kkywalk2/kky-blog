// src/components/Login.vue
<template>
  <section>
    <form v-on:submit="onSubmit">
      <b-field label="Name">
        <b-input placeholder="Enter your ID" v-model="accountName" ref="idLabel"/>
      </b-field>
      <b-field label="Password">
        <b-input type="password" placeholder="Enter your password" v-model="password1"/>
      </b-field>
      <b-field label="Password 다시입력">
        <b-input type="password" placeholder="Enter your password" v-model="password2"/>
      </b-field>
      <b-field label="Email">
        <b-input type="email" placeholder="Enter your Email" v-model="email"/>
      </b-field>
      <b-button label="Singup" native-type="submit"/>
    </form>
  </section>
</template>

<script>
import {signUp} from '@/service'

export default {
  name: 'SignUp',
  data: () => ({
    accountName: '',
    password1: '',
    password2: '',
    email:'',
  }),
  methods: {
    async onSubmit (e) {
      e.preventDefault(); //SPA에선 자동으로 화면 refresh하는 것을 방지해야함
      if(this.password1 !== this.password2) {
          alert("비밀번호가 일치하지 않습니다")
          return
      }

      if(!this.accountName || !this.password1 || !this.password2 || !this.email) {
          alert("양식을 모두 채워주세요")
          return
      }

      try {
        let signUpResult = await signUp(this.accountName, this.password1, this.email)
        if(signUpResult) {
            alert("회원가입완료")
            this.$router.push({name: 'Blog'})
        }
        else alert("회원가입에 실패하였습니다")
      } catch (err) {
        alert("회원가입에 실패하였습니다")
        console.error(err)
        this.$refs.idLabel.focus()
      }
    }
  }
}
</script>