<template>
  <div>
    <div></div>
    <nav class="level">
      <div class="level-left">
        <p class="title is-3">Blogggg 글목록</p>
      </div>
      <div class="level-right">
        <p class="level-item">{{ versionInfo }}</p>
      </div>
    </nav>
    <ul>
      <li class="list-view" v-for="item in postData" :key="item.id">
        <!--<postCard :content="item.content" :post-title="item.title"></postCard>-->
        <b-navbar-item v-bind:href="`/#/post/${item.id}`">{{item.title}}</b-navbar-item>
      </li>
    </ul>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'

//import PostCard from "@/components/PostCard";

import {getPosts} from "@/service";

export default {
  name: 'BlogMain',
  /*components: {
    'postCard': PostCard
  },*/
  data: function () {
    return {
      versionInfo: '',
      postData: []
    };
  },
  computed: {
    ...mapGetters({
      getToken: 'getToken'
    })
  },
  created: async function () {
    this.postData = (await getPosts(this.getToken)).data
  },
}
</script>

<style>
li{
  list-style:none;
  margin-bottom: 100px;
  margin-top: 100px;
  justify-content: space-between;
}
</style>