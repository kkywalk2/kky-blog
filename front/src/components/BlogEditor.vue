<template>
  <section>
    <editor :options="options" :initialValue="editorText" ref="editor" align="left"/>
    <viewer v-if="toggle" :initialValue="editorText"/>
  </section>
</template>

<script>
import 'codemirror/lib/codemirror.css'
import '@toast-ui/editor/dist/toastui-editor.css'
import { Editor, Viewer } from '@toast-ui/vue-editor'

export default {
  name: 'Editor',
  components: {
    'editor': Editor,
    'viewer': Viewer
  },
  data () {
    return {
      editorText: '',
      toggle : false,
      options: {
        language: "ko",
        hooks: {
          addImageBlobHook: this.onAddImage
        }
      }
    }
  },
  methods: {
    onAddImage(blob) {
      console.log(blob)
    },
  }
}
</script>