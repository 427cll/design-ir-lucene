<template>
  <header>
    <div class="q-gutter-y-md column" style="max-width: 650px">
      <q-input bottom-slots v-model="query.question" counter maxlength="12" rounded outlined
        @keyup.enter="handleSearch">
        <template v-slot:append>
          <q-icon v-if="query.question !== ''" name="close" @click="query.question = ''" class="cursor-pointer" />
          <q-icon name="search" @click="handleSearch" />
        </template>
      </q-input>
    </div>
  </header>
  <main class="main">
    <template v-for="chapter in results" :key="chapter.id">
      <div class="result-item">
        <!-- 最多显示一行 -->
        <router-link :to="getChapterUrl(chapter.id)" class="m-link">
          <span v-html="chapter.title" class="m-title"></span>
        </router-link>
        <!-- 显示两三行 -->
        <p v-html="chapter.summary" class="m-summary"></p>
      </div>
    </template>
  </main>
  <div class="paga">
    <div class="q-pa-lg flex flex-center">
      <q-pagination v-model="query.pageIndex" :max="pageTotal" boundary-links @update:model-value="changePageIndex"
        v-show="pageTotal" gutter="8px" />
    </div>
  </div>

</template>

<script setup>
import { reactive, ref, watch } from 'vue'
import { listResults } from '../api'

const query = reactive({
  pageIndex: 1,
  pageSize: 10,

  question: ""
})
const pageTotal = ref(0)
watch(() => query.question, (newVal, oldVal) => {
  query.pageIndex = 1
})
const results = ref([])
const handleSearch = () => {
  listResults(query).then(res => {
    results.value = res.data.itemVOList
    pageTotal.value = res.data.pageTotal
  })
}

const changePageIndex = () => {
  handleSearch()
}

const getChapterUrl = (id) => {
  return "/chapter/" + id
}
</script>
<style scoped>
.q-gutter-y-md {
  margin: 8px auto;
}

.main {
  margin: 0 auto;
  max-width: 650px;
  display: flex;
  flex-direction: column;
}

.m-link {
  text-decoration: none;
}

.m-link:hover {
  text-decoration: underline;
}

.m-title {
  font: 13px/1.2em 'Microsoft YaHei', Arial, Helvetica, Sans-Serif;
  font-size: 20px;
  line-height: 24px;
  font-weight: 400;
  color: #001ba0;
}

.m-summary {
  color: #666;
  padding-left: 1em;
  line-height: 1.5;
}

.paga {
  align-self: flex-end;
}


p {
  margin-top: 2px;
}

.result-item {
  margin: 1px 2px;
}
</style>
