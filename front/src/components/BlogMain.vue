<template>
  <div>
    <div></div>
    <nav class="level">
      <div class="level-left">
        <p class="title is-3">Blogggg</p>
      </div>
      <div class="level-right">
        <p class="level-item">{{ versionInfo }}</p>
      </div>
    </nav>
    <ul>
      <li v-for="item in postData" :key="item.id">
        <postCard :content="item.content" :post-title="item.title"></postCard>
      </li>
    </ul>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'

import PostCard from "@/components/PostCard";

import {getPosts} from "@/service";

export default {
  name: 'BlogMain',
  components: {
    'postCard': PostCard
  },
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
    console.log(this.getToken)
    this.postData = (await getPosts(this.getToken)).data
    console.log(this.postData)
  },
}
</script>

<style>
li{
  list-style:none;
  padding-left:0;
  margin-bottom: 20px;
  margin-top: 20px;
}
</style>