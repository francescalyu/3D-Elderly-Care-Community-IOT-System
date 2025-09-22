<!-- 告警级别配置 -->
<template>
  <div class="content_warp h-full">
    <el-form ref="queryForm" :inline="true" :model="formData" class="flex flex-wrap">
      <el-form-item label="告警级别名称">
        <el-input v-model="formData.name" placeholder="告警级别名称" style="width: 150px" />
      </el-form-item>
      <el-form-item label="是否霸屏">
        <el-select v-model="formData.topScreen" style="width: 150px">
          <el-option v-for="item in yesOrNoList" :key="item.value" :label="item.name" :value="item.value" />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="是否音效提示">
        <el-select v-model="formData.soundCues" style="width: 150px">
          <el-option v-for="item in yesOrNoListStr" :key="item.value" :label="item.name" :value="item.value" />
        </el-select>
      </el-form-item> -->
      <el-form-item style="flex:1;" class="flex query_Group">
        <XButton icon="Search" @click="query">
          查询
        </XButton>
        <XButton icon="refresh" @click="reset">
          重置
        </XButton>
        <XButton icon="Plus" @click="add">
          新增级别
        </XButton>
        <XButton icon="delete" @click="delBranch">
          批量删除
        </XButton>
      </el-form-item>
    </el-form>
    <el-table ref="table" :data="tableData" class="mb-16px customTable mt-16px"
      @selection-change="handleSelectionChange" :row-key="getRowKeys">
      <el-table-column align="center" type="selection" width="55" :reserve-selection="true" />
      <el-table-column label="序号" align="center" type="index" width="70" />
      <el-table-column prop="name" align="center" label="告警级别名称" min-width="100px" />
      <el-table-column prop="topScreen" align="center" label="是否霸屏" min-width="100px">
        <template #default="{ row }">
          <div>
            {{ row.topScreen ? '是' : '否' }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="alarmLevelDesc" align="center" label="关闭霸屏权限用户" min-width="120px">
        <template #default="{ row }">
          <div class="des_div">
            <el-popover placement="top-start" trigger="hover" :content="getUserName(row.closeUsers)">
              <template #reference>
                {{ getUserName(row.closeUsers) }}
              </template>
            </el-popover>
          </div>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="soundCues" align="center" label="是否音效提示" min-width="100px">
        <template #default="{ row }">
          <div>
            {{ row.soundCues ? '是' : '否' }}
          </div>
        </template>
      </el-table-column> -->
      <el-table-column prop="vedioName" align="center" label="音效文件" width="350px">
        <template #default="{ row }">
          <div class="des_div">
            <el-popover placement="top-start" trigger="hover" :content="row.vedioName">
              <template #reference>
                {{ row.vedioName }}
              </template>
            </el-popover>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="试听" width="420px">
        <template #default="{ row }">
          <div class="playSoundIcon ml-15px">
          <!-- {{getPreviewUrl() + row.vedioUrlgetPreviewUrl() + row.vedioUrl}} -->
            <audio :src="getPreviewUrl() + row.vedioUrl" controls style="width: 395px;height:30px" />
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="topScreen" label="操作" width="100px">
        <template #default="{ row }">
          <div>
            <el-link class="blue-link" @click="edit(row)">
              编辑
            </el-link>
            <el-link class="red-link" @click="del(row.id)">
              删除
            </el-link>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination v-model:current-page="pageParam.pageNo" v-model:page-size="pageParam.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="pageParam.total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" style="margin-right:50px" />
    <AddDialog ref="AddDialogRef" :type="type_btn" @submit="submit" />
  </div>
</template>

<script lang="ts" setup>
// import { Howl } from 'howler'
import { getAlarmLevelPage, alarmLevelDelete } from '../ts/index'
import { getPreviewUrl } from '@/utils'
import AddDialog from './components/AddAlarmLevelDialog.vue'
//
const AddDialogRef = ref()
//
const formData = reactive({
  name: '',
  topScreen: '',
  soundCues: ''
})
const pageParam = ref<any>({
  pageSize: 10,
  total: 0,
  pageNo: 1,
})
const yesOrNoList = ref<any[]>([
  { name: '是', value: true },
  { name: '否', value: false },
])
// const yesOrNoListStr = ref<any[]>([
//   { name: '是', value: 1 },
//   { name: '否', value: 0 },
// ])
//
const selection = ref<any[]>([])
const selectionData = ref<any[]>([])
const tableData = ref<any[]>([])
//
const type_btn = ref('')
//
function query() {
  getData()
}
function reset() {
  for (const k in formData) {
    formData[k] = ''
  }
  query()
}
const handleCurrentChange = (page: any) => {
  pageParam.value.pageNo = page
  query()
}
const handleSizeChange = (pageSize: any) => {
  pageParam.value.pageSize = pageSize
  query()
}
function add() {
  type_btn.value = 'add'
  AddDialogRef.value?.open()
}
function edit(row) {
  type_btn.value = 'edit'
  AddDialogRef.value?.open(row)
}
function delBranch() {
  if (selection.value.length === 0) {
    ElMessage.warning('请勾选表格项目')
  }
  else {
    ElMessageBox.confirm('确认删除勾选项？', '提示', { type: 'warning' }).then(() => {
      alarmLevelDelete(selection.value).then((res: any) => {
        if (res.code === 200) {
          ElMessage.success('删除成功！')
          getData()
        }
        else {
          ElMessage.error(res.msg || '删除失败')
        }
      })
    }).catch(() => { })
  }
}
function del(id) {
  ElMessageBox.confirm('确认删除？', '提示', { type: 'warning' }).then(() => {
    alarmLevelDelete([id]).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success('删除成功！')
        getData()
      }
      else {
        ElMessage.error(res.msg || '删除失败')
      }
    })
  }).catch(() => { })
}
const getRowKeys = (row) => {
  return row.id
}
const handleSelectionChange = (val) => {
  selectionData.value = val
  selection.value = val.map((item) => { return item.id })
}
function submit() {
  getData()
}
function getData() {
  getAlarmLevelPage(formData, pageParam.value).then((res) => {
    if (res) {
      console.log('res.data.records: ', res.data.records);
      tableData.value = res.data.records || [] || []
      pageParam.value.total = res.data.total ? res.data.total : 0
    }
  })
}
function getUserName(arr) {
  let exportArr = []
  if (arr && arr.length > 0) {
    for (let i = 0; i < arr.length; i++) {
      exportArr.push(arr[i].realName)
    }
    return exportArr.join('，')
  } else {
    return ''
  }

}
onMounted(() => {
  getData()
})
</script>

<style lang="scss" scoped>
.content_warp{
  margin-top: 20px;
  height: calc(100% - 20px);
}
:deep(.customTable) {
  height: calc(100% - 100px) !important;
  max-height: calc(100% - 100px) !important;

  img {
    display: inline-block;
  }

  >div.el-table__inner-wrapper {
    height: 100% !important;
  }
}

.des_div {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
</style>
