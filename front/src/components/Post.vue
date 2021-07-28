<template>
  <div class="container is-widescreen section">
    <div class="notification is-primary">
      <b-field :label="postTitle">
        <div v-html="content"></div>
      </b-field>
    </div>
  </div>
</template>

<script>
import 'codemirror/lib/codemirror.css'
import '@toast-ui/editor/dist/toastui-editor.css'
import {mapGetters} from 'vuex'

import {getPost} from "@/service";

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
      content: ''
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
    //this.$refs.viewer.invoke('setMarkdown',data.content)
  }
}
</script>

<style>
.is-primary{
  background-color: bisque;
}
</style>