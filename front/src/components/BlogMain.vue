<template>
  <div>
    <div></div>
    <nav class="level">
      <div class="level-left">
        <p class="title is-3">Blogggg</p>
      </div>
      <div class="level-right">
        <p class="level-item">{{ versionInfo }}</p>
      </div>
    </nav>
    <div class="content">
      <p>
        블로그 메인입니다.
        <br />
        어쩌구 저쩌구
      </p>
      <h3>기능되야함</h3>
      <ul>
        <li>Footer와 Nav?</li>
        <li>블로그 글방식은? MD?</li>
        <li>블라블라블라</li>
        <li> 니얼굴드록바 </li>
      </ul>
      <ul>
        <li v-for="item in postData" :key="item.id">
          <postCard :content="item.content" post-title="ASD"></postCard>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'

import PostCard from "@/components/PostCard";

export default {
  name: 'BlogMain',
  components: {
    'postCard': PostCard
  },
  data: function () {
    return {
      versionInfo: this.getToken,
      postData: [
        { content: 'Learn Vue' },
        { content: 'Create a Vue project with the CLI' },
        { content: 'Have fun' },
        { content: 'Create a to-do list' }
      ]
    };
  },
  computed:{
    ...mapGetters({
     getToken: 'getToken'
   }) 
  },
  created: function () {
    console.log(this.getToken)
  },
}
</script>