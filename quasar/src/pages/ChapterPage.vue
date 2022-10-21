<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router';
import { getChapter } from '../api'
const route = useRoute()
const id = route.params.id
const chapter = ref({})

const getData = () => {
  getChapter(id).then(res => {
    chapter.value = res.data
  })
}
getData()
</script>

<template>
  <div class="chapter">
    <div class="main-wrapper">
      <div class="main">
        <h4>{{chapter.title}}</h4>
        <div v-html="chapter.content"></div>
      </div>
    </div>
  </div>
</template>

<style>
.chapter {
  background-color: #f9f9f9;
  width: 100vw;
  font: 12px "宋体", "Arial Narrow", HELVETICA;
}

.chapter .main-wrapper {
  background-color: #Fff;
  width: 1200px;
  margin: 0 auto;
}

.chapter .main {
  width: 960px;
  min-height: 90vh;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  font-size: 20px;
  line-height: 40px;
  word-wrap: break-word;
  font-weight: 200;
}

.chapter .main,
.chapter h4 {
  color: #333;
  margin: 15px auto;
  border-bottom: 1px dashed #CECECE;
}

.chapter p {
  margin: 30px auto;
  text-indent: 2em;
}
</style>
