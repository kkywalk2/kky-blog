<template>
  <div>
    <div></div>
    <nav class="level">
      <div class="level-left">
        <p class="title is-3">Posts</p>
      </div>
      <div class="level-right">
        <p class="level-item">{{ versionInfo }}</p>
      </div>
    </nav>
    <ul class="left">
      <li v-for="item in categoryData" :key="item.category">
        <b-navbar-item v-on:click="refreshPostList(item.category)">{{item.category}}({{item.count}})</b-navbar-item>
      </li>
    </ul>
    <ul>
      <li class="list-view" v-for="item in postData" :key="item.id">
        <b-navbar-item v-bind:href="`/#/post/${item.id}`">{{item.title}}</b-navbar-item>
      </li>
    </ul>
  </div>
</template>

<script>
import {getPosts, getCategories, getPostByCategory} from "@/service";

export default {
  name: 'BlogMain',
  data: function () {
    return {
      versionInfo: '',
      postData: [],
      categoryData:[]
    };
  },
  created: async function () {
    await this.refreshPostList("")
  },
  methods: {
    async refreshPostList(category) {
      if(!category)
        this.postData = (await getPosts()).data
      else
        this.postData = (await getPostByCategory(category)).data
      this.categoryData = (await getCategories()).data
    }
  }
}
</script>

<style>
.left{
  position:fixed;
  top:20px;
  left:0px;
  width:250px;
  height:1000px;
  background:rgba(0,0,0,0);
  padding:40px 0;
  overflow: hidden
}
.list-view{
  position:relative;
  top:0px;
  left:250px;
}
.level-left{
  position:relative;
  top:0px;
  left:250px;
}
</style>