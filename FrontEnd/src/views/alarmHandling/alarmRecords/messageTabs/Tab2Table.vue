<!-- 库存消息--table -->
<template>
  <div class="h-full">
    <div>
      <XButton icon="Delete" @click="delMessage">
        删除
      </XButton>
      <XButton icon="Reading" @click="read">
        标记已读
      </XButton>
    </div>
    <el-table ref="table" :data="tableData" class="mb-[32px] customTable mt-[5px]"
      @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55" />
      <el-table-column label="序号" align="center" type="index" width="70" />
      <el-table-column prop="content" align="center" label="消息内容" min-width="100px">
        <template #default="{ row }">
          <div class="flex justify-start">
            <span :class="row.reading===0 ? 'dot' : ''"></span>{{ row.content }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" align="center" label="时间" width="200px" />
      <el-table-column prop="messageTypeStr" align="center" label="类型" width="120px" />
    </el-table>
    <el-pagination v-model:current-page="pageParam.pageNo" v-model:page-size="pageParam.pageSize"
      layout="total, sizes, prev, pager, next, jumper" :total="pageParam.total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" />
  </div>
</template>

<script lang='ts' setup>
import { getMessagePage, delMessageBranch, getMessageRead } from '../../ts/index'
//
const tableData = ref<any[]>([])
const selection = ref<any[]>([])
const selectionData = ref<any[]>([])
const pageParam = ref<any>({
  pageSize: 10,
  total: 0,
  pageNo: 1,
})
const getData = () => {
  selection.value = []
  selectionData.value = []
  getMessagePage({ type: 2 }, pageParam.value).then(res => {
    tableData.value = res.data.records || []
    pageParam.value.total = res.data.total ? res.data.total : 0
  })
}

const delMessage = () => {
  if (selection.value.length === 0) {
    ElMessage.warning('请勾选表格项目')
  }
  else {
    ElMessageBox.confirm('确认删除？', '提示', { type: 'warning' }).then(() => {
      delMessageBranch({ids:selection.value.map(item=>item).join(',')}).then((res: any) => {
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
const read = () => {
  if (selection.value.length === 0) {
    ElMessage.warning('请勾选表格项目')
  }
  else {
    getMessageRead({ids:selection.value.map(item=>item).join(',')}).then((res: any) => {
      if (res.code === 200) {
        ElMessage.success('标记已读成功！')
        getData()
      }
      else {
        ElMessage.error(res.msg || '标记已读失败')
      }
    })
  }
}
const handleSelectionChange = (val) => {
  selectionData.value = val
  selection.value = val.map((item) => { return item.id })
}
const handleCurrentChange = (page: any) => {
  pageParam.value.pageNo = page
  getData()
}
const handleSizeChange = (pageSize: any) => {
  pageParam.value.pageSize = pageSize
  getData()
}
//
onMounted(() => {
  getData()
})
</script>
<style lang='scss' scoped>
:deep(.customTable) {
  height: calc(100% - 150px) !important;
  max-height: calc(100% - 150px) !important;
  min-height: 600px;

  >div.el-table__inner-wrapper {
    height: 100% !important;
  }
}

.dot {
  vertical-align: text-top;
  display: inline-block;
  margin-left: 4px;
  margin-right: 20px;
  background-color: red;
  border-radius: 50%;
  width: 8px;
  height: 8px;
  position: relative;
  top: 8px;
}
</style>