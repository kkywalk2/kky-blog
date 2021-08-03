<template>
  <body>
    <b-navbar type="is-black" fixed-top shadow>
      <template slot="brand">
        <b-navbar-item>
          <!--<img src="./assets/img/logo.png" alt="KKY" />-->
        </b-navbar-item>
      </template>
      <template slot="start">
        <b-navbar-item href="/#/">Home</b-navbar-item>
        <b-navbar-item href="/#/editor">Create Post</b-navbar-item>
        <b-navbar-item href="/#/signup">SignUp</b-navbar-item>
      </template>
    </b-navbar>
    <br />
    <title>Bloggggggggging</title>
    <div class="container is-fluid">
      <router-view />
    </div>
  </body>
</template>

<script>

export default {
  name: 'App',
  components: {}
}
</script>
