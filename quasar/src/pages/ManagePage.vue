<template>
  <div class="q-pa-md">
    <div class="add-btn">
      <q-btn color="primary" icon="add" label="新增章节" @click="handleAdd" />
    </div>
    <q-table :rows="rows" :columns="columns" row-key="id" v-model:pagination="pagination" hide-pagination>
      <template #body-cell-operator="scope">
        <q-td>
          <div style="text-align:center">
            <q-btn flat color="primary" label="编辑" @click="handleEdit(scope.row)" />
            <q-btn flat color="red" label="删除" @click="handleDelete(scope.row)" />
          </div>
        </q-td>
      </template>
    </q-table>

    <div class="paga">
      <div class="q-pa-lg flex flex-center">
        <q-pagination v-model="query.pageIndex" :max="pageTotal" boundary-links @update:model-value="changePageIndex" />
      </div>
    </div>

    <div class="dialog">
      <q-dialog v-model="dialogVisiable">
        <q-card style="min-width: 700px">
          <q-card-section>
            <div class="text-h6">Chapter Info</div>
          </q-card-section>

          <q-card-section class="q-pt-none">
            标题：
            <q-input dense v-model="form.title" autofocus @keyup.enter="dialogVisiable = false" />
            内容：
            <q-input autogrow dense v-model="form.content" autofocus @keyup.enter="dialogVisiable = false" />
          </q-card-section>

          <q-card-actions align="right" class="text-primary">
            <q-btn flat label="Cancel" v-close-popup />
            <q-btn flat label="Confirm" v-close-popup @click="submitForm" />
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { listChapters, saveChapter, deleteChapter } from '../api'
import { useQuasar } from 'quasar'

const $q = useQuasar()

$q.notify.setDefaults({
  timeout: 100,
})

const showNotif = () => {
  $q.notify({
    message: '操作成功',
    color: 'positive',
    position: 'top'
  })
}


const query = reactive({
  pageIndex: 1,
  pageSize: 10,
})

const columns = [
  {
    name: 'id', label: 'ID', align: 'center', field: 'id', style: 'max-width: 50px',
    headerStyle: 'max-width: 50px'
  },
  { name: 'title', label: 'Title', align: 'left', field: 'title' },
  {
    name: 'content', label: 'Content', align: 'left', field: 'content', classes: 'ellipsis',
    style: 'max-width: 600px',
    headerStyle: 'max-width: 600px'
  },
  { name: 'operator', label: 'Operator', align: 'center' }
]

const rows = ref([])

const pageTotal = ref(0)

const pagination = ref({
  rowsPerPage: 10
})

const getData = () => {
  listChapters(query).then(res => {
    rows.value = res.data.chapterList
    pageTotal.value = res.data.pageTotal
  })
}
getData()
const changePageIndex = (val) => {
  getData()
}

// 对话框
let form = reactive({
  id: "",
  title: "",
  content: ""
})
const dialogVisiable = ref(false)

const handleEdit = (row) => {
  Object.keys(form).forEach((item) => {
    form[item] = row[item];
  });
  dialogVisiable.value = true
}

const handleAdd = () => {
  Object.keys(form).forEach((item) => {
    form[item] = "";
  });
  dialogVisiable.value = true
}


const submitForm = () => {
  dialogVisiable.value = false
  saveChapter(form).then(res => {
    showNotif()
    getData()
  })
}

const handleDelete = (row) => {
  deleteChapter(row.id).then(res => {
    showNotif()
    getData()
  })
}


</script>
<style scoped>
.add-btn {
  position: fixed;
  bottom: 40px;
  right: 15px;
  z-index: 999;
  margin-bottom: 8px;
  display: flex;
  flex-direction: row-reverse;
}
</style>
