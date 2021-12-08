<template>
  <div>
    <nav class="level">
      <div class="level-left">
        <p class="title is-3">Posts</p>
      </div>
    </nav>
    <div class="columns is-multiline is-desktop">
      <div class="column is-narrow" style="width: 300px;">
        <AuthorCard/>
        <ul>
          <li v-for="item in categoryData" :key="item.category">
            <b-navbar-item tag="router-link" :to="{ path: `/` , query: {'category': item.category} }">
              {{ item.category }}({{ item.count }})
            </b-navbar-item>
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
            <b-navbar-item tag="router-link" :to="{ path: `/post/${item.id}` }"
                           class="date columns is-multiline is-desktop">
              <h1 class="column">{{ item.title }}</h1>
              <p class="column is-one-quarter">{{ new Date(item.createAt).toLocaleDateString() }}</p>
            </b-navbar-item>
            <br/>
          </li>
          <b-pagination v-model="page" :total="totalElements" :per-page="perPage" @change="pageClick">
            <template #previous="props">
              <b-pagination-button
                  :page="props.page"
                  tag="button">
                Previous
              </b-pagination-button>
            </template>

            <template #next="props">
              <b-pagination-button
                  :page="props.page"
                  tag="button">
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
      categoryData: [],
      page: 1,
      totalPages: 0,
      totalElements: 0,
      searchText: '',
      perPage: 10
    };
  },
  created() {
    this.refreshPostList()
  },
  methods: {
    async pageClick(page) {
      this.page = page
      await this.refreshPostList()
    },
    async refreshPostList() {
      const data = (await getPosts(this.page - 1, this.perPage, this.$route.query.category, this.$route.query.search)).data
      this.postData = data.content
      this.totalElements = data.totalElements
      this.totalPages = data.totalPages
      this.categoryData = (await getCategories()).data
    },
    async onSearch() {
      await this.$router.push({path: '/', query: {'search': this.searchText, 'category': this.$route.query.search}})
    }
  },
  watch: {
    $route() {
      this.refreshPostList()
    }
  }
}
</script>