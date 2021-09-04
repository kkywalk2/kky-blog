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
        <b-navbar-item v-on:click="refreshPostList(item.category, page)">{{item.category}}({{item.count}})</b-navbar-item>
      </li>
    </ul>
    <ul class="list-view">
      <li v-for="item in postData" :key="item.id">
        <b-navbar-item tag="router-link" :to="{ path: `/post/${item.id}` }">{{item.title}}</b-navbar-item>
      </li>
      <b-pagination
            :total="totalElements"
            v-model="page"
            :per-page="10">
            <template #default="props">
                <b-pagination-button
                    :page="props.page"
                    :id="`page${props.page.number}`"
                    tag="button"
                    :click="refreshPostList(category, page)">
                    {{props.page.number}}
                </b-pagination-button>
            </template>


            <template #previous="props">
                <b-pagination-button
                    :page="props.page"
                    tag="button"
                    :click="refreshPostList(category, page)">
                    Previous
                </b-pagination-button>
            </template>

            <template #next="props">
                <b-pagination-button
                    :page="props.page"
                    tag="button"
                    :click="refreshPostList(category, page)">
                    Next
                </b-pagination-button>
            </template>
       </b-pagination>
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
      categoryData:[],
      page:1,
      totalPages:0,
      totalElements:0,
      category:""
    };
  },
  created: async function () {
    await this.refreshPostList("", this.page)
  },
  methods: {
    async refreshPostList(category, page) {
      this.category = category
      if(!category) {
        let data = (await getPosts(page - 1, 10)).data
        this.postData = data.content
        this.totalElements = data.totalElements
        this.totalPages = data.totalPages
      }
      else {
        let data = (await getPostByCategory(category, page - 1, 10)).data
        this.postData = data.content
        this.totalElements = data.totalElements
        this.totalPages = data.totalPages
      }
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
