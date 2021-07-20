<template>
  <section>
    <b-field label="Title">
      <b-input placeholder="Enter your Title" v-model="title"/>
    </b-field>
    <editor :options="options" :initialValue="editorText" ref="editor" align="left"/>
    <viewer v-if="toggle" :initialValue="editorText"/>
    <b-button label="save" v-on:click="onCreatePost"/>
  </section>
</template>

<script>
import 'codemirror/lib/codemirror.css'
import '@toast-ui/editor/dist/toastui-editor.css'
import {Editor, Viewer} from '@toast-ui/vue-editor'

import {createPosts} from "@/service";
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
    onAddImage(blob) {
      console.log(blob)
    },
    onCreatePost() {
      createPosts(this.getToken, this.title, this.$refs.editor.invoke('getMarkdown'), 'all')
    }
  }
}
</script>