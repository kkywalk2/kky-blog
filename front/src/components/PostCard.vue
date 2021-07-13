<template>
  <div class="container is-widescreen">
    <div class="notification is-primary">
      <b-field :label="postTitle">
        <viewer :initialValue="content"></viewer>
      </b-field>
    </div>
  </div>
</template>

<script>
import 'codemirror/lib/codemirror.css'
import '@toast-ui/editor/dist/toastui-editor.css'
import {Viewer} from '@toast-ui/vue-editor'

export default {
  name: 'PostCard',
  components: {
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
  }
}
</script>

<style>
li{
  list-style:none;
  padding-left:0;
}
</style>