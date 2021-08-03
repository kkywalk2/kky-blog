<template>
  <section>
    <b-field label="Title">
      <b-input placeholder="Enter your Title" v-model="title"/>
    </b-field>
    <b-field label="Category">
      <b-input placeholder="Enter Post's Category" v-model="category"/>
    </b-field>
    <editor :options="options" :initialValue="editorText" ref="editor"/>
    <viewer v-if="toggle" :initialValue="editorText"/>
    <b-button label="save" v-on:click="onCreatePost"/>
  </section>
</template>

<script>
import 'codemirror/lib/codemirror.css'
import '@toast-ui/editor/dist/toastui-editor.css'
import {Editor, Viewer} from '@toast-ui/vue-editor'

import {createPosts, uploadImage} from "@/service";

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
      options: {
        language: "ko",
        hooks: {
          addImageBlobHook: this.onAddImage
        }
      }
    }
  },
  created: async function () {
    if(!localStorage.getItem("token")) {
      alert("로그인 해주십시오")
      await this.$router.push({
        name: 'Login'
      })
    }
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
    onCreatePost() {
      try {
        createPosts(localStorage.getItem("token"), this.title, this.$refs.editor.invoke('getHtml'), this.category)
        alert("업로드 완료!")
        this.$router.push({name: 'Blog'})
      } catch (ex) {
        alert("업로드에 실패하였습니다!" + ex)
      }
    }
  }
}
</script>