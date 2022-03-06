<template>
  <section>
    <div class="columns is-multiline is-desktop">
      <div class="column is-one-quarter"/>
      <div class="column">
        <b-field :label="postTitle">
          <b-button tag="router-link" :to="`/editor/${this.$route.params.id}`">수정</b-button>
          <b-button v-on:click="deletePost">삭제</b-button>
        </b-field>
        <viewer v-if="content!=null" :initialValue="content" :options="options"/>
        <ul>
          <li class="list-view" v-for="item in comments" :key="item.id">
            <label>{{ item.accountName }}:{{ item.content }}</label>
          </li>
        </ul>
        <b-field label="Comment">
          <b-input placeholder="Enter your Comment" v-model="comment"/>
        </b-field>
        <b-button label="Add Comment" v-on:click="addComment"/>
      </div>
      <div class="column is-one-quarter"/>
    </div>
  </section>
</template>

<script>

import Prism from 'prismjs';
import 'prismjs/components/prism-java'
import 'prismjs/components/prism-kotlin'

import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';
import tableMergedCell from '@toast-ui/editor-plugin-table-merged-cell'
import {Viewer} from "@toast-ui/vue-editor";

import {getPost, addComment, deletePost, checkAuthentication} from "@/service";

export default {
  name: 'Post',
  components: {
    'viewer': Viewer
  },
  data: function () {
    return {
      postTitle: '',
      content: null,
      comments: [],
      comment: '',
      options: {plugins: [tableMergedCell, [codeSyntaxHighlight, {highlighter: Prism}]]}
    };
  },
  created: async function () {
    let data = (await getPost(this.$route.params.id)).data
    console.log(data)
    this.postTitle = data.title
    this.content = data.content
    this.comments = data.comments
  },
  methods: {
    async addComment() {
      try {
        if (localStorage.getItem("token") && (await checkAuthentication(localStorage.getItem("token")))) {
          await addComment(localStorage.getItem("token"), this.$route.params.id, this.comment)
          this.comments = (await getPost(this.$route.params.id)).data.comments
        } else {
          alert("로그인 해주세요")
          await this.$router.push({name: 'Login'})
        }
      } catch (ex) {
        alert("뭔가 잘못됨! : " + ex)
      }
    },
    async deletePost() {
      try {
        if (localStorage.getItem("token") && (await checkAuthentication(localStorage.getItem("token")))) {
          let data = await deletePost(localStorage.getItem("token"), this.$route.params.id)
          if (data != null) {
            alert("삭제완료")
            await this.$router.push({name: 'Blog'})
          } else {
            alert("삭제에 실패하였습니다")
          }
        } else {
          alert("로그인 해주세요")
          await this.$router.push({name: 'Login'})
        }
      } catch (ex) {
        alert("뭔가 잘못됨! : " + ex)
      }
    }
  }
}
</script>