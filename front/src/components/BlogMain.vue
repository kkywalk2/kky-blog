<template>
  <div>
    <nav class="level">
      <div class="level-left">
        <p class="title is-3">Posts</p>
      </div>
    </nav>
    <div class="columns is-multiline is-desktop">
      <div class="column is-narrow" style="width: 300px;">
        <AuthorCard />
        <ul>
          <li v-for="item in categoryData" :key="item.category">
            <b-navbar-item tag="router-link" :to="{ path: `/` , query: {'category': item.category} }">{{item.category}}({{item.count}})</b-navbar-item>
          </li>
        </ul>
      </div>
      <div class="column">
        <b-field>
          <b-input placeholder="Search..."
                   type="search"
                   icon="magnify"
                   icon-clickable
                   v-model="searchText"
                   @keydown.enter.native="onSearch">
          </b-input>
        </b-field>
        <ul>
          <li v-for="item in postData" :key="item.id">
            <div class="columns is-multiline is-desktop">
              <b-navbar-item class="column" tag="router-link" :to="{ path: `/post/${item.id}` }">{{item.title}}</b-navbar-item>
              <label class="column is-one-quarter">{{new Date(item.createAt).toLocaleDateString()}}</label>
            </div>
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
                        :click="refreshPostList($route.query.search, $route.query.category, page)">
                        {{props.page.number}}
                    </b-pagination-button>
                </template>


                <template #previous="props">
                    <b-pagination-button
                        :page="props.page"
                        tag="button"
                        :click="refreshPostList($route.query.search, $route.query.category, page)">
                        Previous
                    </b-pagination-button>
                </template>

                <template #next="props">
                    <b-pagination-button
                        :page="props.page"
                        tag="button"
                        :click="refreshPostList($route.query.search, $route.query.category, page)">
                        Next
                    </b-pagination-button>
                </template>
          </b-pagination>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import AuthorCard from "../components/AuthorCard.vue";
import {getPosts, getCategories} from "@/service";

export default {
  name: 'BlogMain',
  components: {
    AuthorCard,
  },
  data: function () {
    return {
      versionInfo: '',
      postData: [],
      categoryData:[],
      page:1,
      totalPages:0,
      totalElements:0,
      searchText:''
    };
  },
  methods: {
    async refreshPostList(searchText, category, page) {
      let data = (await getPosts(page - 1, 10, category, searchText)).data
      this.postData = data.content
      this.totalElements = data.totalElements
      this.totalPages = data.totalPages
      this.categoryData = (await getCategories()).data
    },
    async onSearch() {
      await this.$router.push({ path: '/', query: { 'search': this.searchText , 'category' : this.$route.query.category } })
    }
  },
  watch: {
    $route() {
      this.refreshPostList(this.$route.query.search, this.$route.query.category, this.page)
    }
  }
}
</script>