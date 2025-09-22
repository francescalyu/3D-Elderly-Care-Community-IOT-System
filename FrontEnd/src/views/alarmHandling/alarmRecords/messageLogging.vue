<!-- 消息记录 -->
<template>
  <div class="content_warp">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="全部消息" name="all" lazy>
        <Tab1Table></Tab1Table>
      </el-tab-pane>
      <el-tab-pane label="库存消息" name="inventory" lazy>
        <Tab2Table></Tab2Table>
      </el-tab-pane>
      <el-tab-pane label="工单消息" name="workOrder" lazy>
        <Tab3Table></Tab3Table>
      </el-tab-pane>
      <el-tab-pane label="告警消息" name="alarm" lazy>
        <Tab4Table></Tab4Table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script lang='ts' setup>
import Tab1Table from './messageTabs/Tab1Table.vue'
import Tab2Table from './messageTabs/Tab2Table.vue'
import Tab3Table from './messageTabs/Tab3Table.vue'
import Tab4Table from './messageTabs/Tab4Table.vue'
const activeName = ref('all')
//
const handleClick = (tab: string) => {
  console.log(tab)
}
onMounted(() => {

})
</script>
<style lang='scss' scoped>
.content_warp{
  margin-top: 20px;
  height: calc(100% - 20px);
}
</style>