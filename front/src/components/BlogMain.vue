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
      <li>Footer와 Nav?</li>
      <li>블로그 글방식은? MD?</li>
      <li>블라블라블라</li>
      <h3>Notice</h3>
      <li> 니얼굴드록바 </li>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'

export default {
  name: 'BlogMain',
  data: function () {
    return {
      versionInfo: this.getToken,
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