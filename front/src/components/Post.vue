<template>
  <div class="container is-widescreen section">
    <div class="notification is-primary">
      <b-field :label="postTitle">
        <div v-html="content"></div>
      </b-field>
    </div>
    <ul>
      <li class="list-view" v-for="item in comments" :key="item.id">
        <label>{{item.content}}</label>
      </li>
    </ul>
    <b-field label="Comment">
      <b-input placeholder="Enter your Comment" v-model="comment"/>
    </b-field>
    <b-button label="Add Comment" v-on:click="addComment"/>
  </div>
</template>

<script>
import 'codemirror/lib/codemirror.css'
import '@toast-ui/editor/dist/toastui-editor.css'
import {mapGetters} from 'vuex'

import {getPost, addComment} from "@/service";

export default {
  name: 'Post',
  /*components: {
    'viewer': Viewer
  },
  props:{
    postTitle : {
      type:String,
      default:""
    },
    content : {
      type:String,
      default:""
    }
  },*/
  data: function () {
    return {
      postTitle: '',
      content: '',
      comments: [],
      comment : ''
    };
  },
  computed: {
    ...mapGetters({
      getToken: 'getToken'
    })
  },
  created: async function() {
    let data = (await getPost(this.getToken, this.$route.params.id)).data
    console.log(data)
    this.postTitle = data.title
    this.content = data.content
    this.comments = data.comments
  },
  methods: {
    async addComment() {
      await addComment(this.getToken, this.$route.params.id, this.comment)
      this.comments = (await getPost(this.getToken, this.$route.params.id)).data.comments
    }
  }
}
</script>

<style>
.is-primary{
  background-color: bisque;
}
</style>