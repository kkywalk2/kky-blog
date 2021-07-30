// src/components/Login.vue
<template>
  <section>
      <b-field label="Name">
        <b-input placeholder="Enter your ID" v-model="accountName"/>
      </b-field>
      <b-field label="Password">
        <b-input placeholder="Enter your password" v-model="password"/>
      </b-field>
      <b-button label="login" v-on:click="onSubmit"/>
  </section>
</template>

<script>
import {mapActions,mapGetters} from 'vuex'

export default {
  name: 'Login',
  data: () => ({
    accountName: '',
    password: ''
  }),
  computed: {
    ...mapGetters({
      getToken: 'getToken'
    })
  },
  methods: {
    ...mapActions(['loginAction']),
    async onSubmit () {
      try {
        let loginResult = await this.loginAction({accountName: this.accountName, password: this.password})
        if(loginResult) this.goToPages()
        else console.log("failed...")
      } catch (err) {
        console.error(err)
      }
    },
    goToPages () {
      this.$router.push({
        name: 'Blog'
      })
    }
  }
}
</script>

<style>

</style>