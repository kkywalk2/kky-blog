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
import {mapGetters} from "vuex";

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
  computed: {
    ...mapGetters({
      getToken: 'getToken'
    })
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
      createPosts(this.getToken, this.title, this.$refs.editor.invoke('getHtml'), this.category)
    }
  }
}
</script>