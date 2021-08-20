<template>
  <section>
      <b-field :label="postTitle">
        <b-button tag="router-link" :to="`/editor/${this.$route.params.id}`">수정</b-button>
        <b-button v-on:click="deletePost">삭제</b-button>
      </b-field>
      <viewer v-if="content!=null" :initialValue="content"/>
    <ul>
      <li class="list-view" v-for="item in comments" :key="item.id">
        <label>{{item.accountName}}:{{item.content}}</label>
      </li>
    </ul>
    <b-field label="Comment">
      <b-input placeholder="Enter your Comment" v-model="comment"/>
    </b-field>
    <b-button label="Add Comment" v-on:click="addComment"/>
  <section>
</template>

<script>
import 'codemirror/lib/codemirror.css'
import '@toast-ui/editor/dist/toastui-editor.css'
import { Viewer } from '@toast-ui/vue-editor'

import {getPost, addComment, deletePost} from "@/service";

export default {
  name: 'Post',
  components: {
    'viewer': Viewer
  },
  /*props:{
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
      content: null,
      comments: [],
      comment : ''
    };
  },
  created: async function() {
    let data = (await getPost(this.$route.params.id)).data
    console.log(data)
    this.postTitle = data.title
    this.content = data.content
    this.comments = data.comments
  },
  methods: {
    async addComment() {
      try {
        if(localStorage.getItem("token")) {
          await addComment(localStorage.getItem("token"), this.$route.params.id, this.comment)
          this.comments = (await getPost(this.$route.params.id)).data.comments
        } else { 
          alert("로그인 해주세요")
          this.$router.push({name: 'Login'})
        }
      } catch(ex) {
        alert("뭔가 잘못됨! : " + ex)
      }
    },
    async deletePost() {
      try {
        if(localStorage.getItem("token")) {
          let data = await deletePost(localStorage.getItem("token"), this.$route.params.id)
          if(data != null) {
            alert("삭제완료")
            this.$router.push({name: 'Blog'})
          }else {
            alert("삭제에 실패하였습니다")
          }
        } else { 
          alert("로그인 해주세요")
          this.$router.push({name: 'Login'})
        }
      } catch(ex) {
        alert("뭔가 잘못됨! : " + ex)
      }
    }
  }
}
</script>