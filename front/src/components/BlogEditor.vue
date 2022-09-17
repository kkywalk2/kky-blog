<template>
  <section>
    <b-field label="Title">
      <b-input placeholder="Enter your Title" v-model="title"/>
    </b-field>
    <b-field label="Category">
      <b-input placeholder="Enter Post's Category" v-model="category"/>
    </b-field>
    <editor v-if="loadComplete" :options="options" :initialValue="editorText" ref="editor"/>
    <viewer v-if="toggle" :initialValue="editorText"/>
    <b-button label="save" v-on:click="onClickSaveButton"/>
  </section>
</template>

<script>
import 'codemirror/lib/codemirror.css'
import '@toast-ui/editor/dist/toastui-editor.css'
import {Editor, Viewer} from '@toast-ui/vue-editor'

import {createPosts, updatePost, uploadImage, getPost, checkAuthentication} from "@/service";

export default {
  name: 'Editor',
  components: {
    'editor': Editor,
    'viewer': Viewer
  },
  data() {
    return {
      title: '',
      editorText: '',
      category: '',
      toggle: false,
      loadComplete : false,
      options: {
        language: "ko",
        hooks: {
          addImageBlobHook: this.onAddImage
        }
      }
    }
  },
  created: async function () {
    if(!localStorage.getItem("token") || !(await checkAuthentication(localStorage.getItem("token")))) {
      alert("로그인 해주십시오")
      await this.$router.push({
        name: 'Login'
      })
    }

    if(this.$route.params.id) {
      let data = (await getPost(this.$route.params.id))
      console.log(data)
      this.title = data.title
      this.editorText = data.content
      this.category = data.category
    }

    this.loadComplete = true
  },
  methods: {
    async onAddImage(blob, callback) {
      let data = await uploadImage(blob)
      if (data != null) {
        callback(`${process.env.VUE_APP_SERVER_ADDRESS}/image/${data.fileName}`, "image");
        return false;
      } else {
        alert("이미지를 업로드 하는것에 실패하였습니다!")
        return false;
      }
    },
    async onClickSaveButton() {
      if(this.$route.params.id)
        await this.onUpdatePost(this.$route.params.id)
      else
        await this.onCreatePost()
    },
    async onCreatePost() {
      let data = await createPosts(localStorage.getItem("token"), this.title, this.$refs.editor.invoke('getMarkdown'), this.category)
      if (data == null) {
        alert("업로드에 실패하였습니다!")
      } else {
        alert("업로드 완료!")
        this.$router.push({name: 'Blog'})
      }
    },
    async onUpdatePost(id) {
      let data = await updatePost(localStorage.getItem("token"), id, this.title, this.$refs.editor.invoke('getMarkdown'), this.category)
      if (data == null) {
        alert("업데이트에 실패하였습니다!")
      } else {
        alert("업데이트 완료!")
        this.$router.push({name: 'Blog'})
      }
    }
  }
}
</script>