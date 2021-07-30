<template>
  <b-navbar type="is-black" fixed-top shadow>
    <template slot="brand">
      <b-navbar-item>
        <!--<img src="./assets/img/logo.png" alt="KKY" />-->
      </b-navbar-item>
    </template>
    <template slot="start">
      <b-navbar-item v-if="this.$store.state.token != null" href="/#/blog">Home</b-navbar-item>
      <b-navbar-item v-if="this.$store.state.token != null" href="/#/editor">Create Post</b-navbar-item>
    </template>
  </b-navbar>
  <br/>
  <div class="container is-fluid">
    <router-view/>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {}
}
</script>
