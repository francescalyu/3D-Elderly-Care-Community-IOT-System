<!-- 产品 -->
<template>
  <div class="item_body">
    <div class="item_body_top">
      <el-form ref="form" :model="queryForm" inline class="flex mt-5">
        <el-form-item label="子系统" style="margin-right: 10px">
          <el-select
            v-model="queryForm.dateType"
            style="width: 200px"
            placeholder=""
            class="timeSelect"
          >
            <el-option
              v-for="item in dateTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item style="flex: 1; margin-left: 60px" class="flex query_Group">
          <el-button icon="Search" @click="query"> 查 询 </el-button>
          <el-button icon="Refresh" @click="reset"> 重 置 </el-button>
        </el-form-item>
        <el-form-item style="text-align: right" class="flex query_Group">
          <el-button icon="Plus" @click="query"> 新 增 </el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="item_body_list">
      <el-table :data="tableData" style="width: 100%" height="100%" ref="tableRef">
        <el-table-column type="expand" width="35">
          <template #default="props">
            <div class="list_R" style="width: calc(25% - 10px); margin-right: 10px">
             <img src="@/assets/img/sbimg.png" alt="">
            </div>
            <div class="list_L" style="width: calc(75% - 10px); margin-left: 10px">
              <el-table
                :data="tableDatas"
                :border="childBorder"
                style="width: 100%"
                height="100%"
              >
                <el-table-column type="index" label="序号" align="center" width="55" />
                <el-table-column property="name" label="点位名称" align="center" />
                <el-table-column property="procType" label="单位" align="center" />
                <el-table-column property="date" label="点位类型" align="center" />
                <el-table-column property="date" label="别名" align="center" />
              </el-table>
            </div>
            
          </template>
        </el-table-column>
        <el-table-column type="index" label="序号" align="center" width="55" />
        <el-table-column property="name" label="产品编号" align="center" />
        <el-table-column property="procType" label="产品名称" align="center" />
        <el-table-column property="date" label="子系统" align="center" />
        <el-table-column property="date" label="厂商" align="center" />
        <el-table-column property="procType1" label="产品编码" align="center" />
        <el-table-column align="center" label="操作" prop="topScreen">
          <template #default="{ row }">
            <div>
              <!-- <el-link class="blue-link" @click="edit(row)"> 查看点位 </el-link> | -->
              <el-link class="red-link" @click="del(row.id)"> 删除 </el-link>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script lang="ts" setup>
const tableRef = ref();
const queryForm = ref({
  dateType: "year",
});
const childBorder = ref(false);
const dateTypeOptions = ref([
  { value: "year", label: "年" },
  { value: "month", label: "月" },
  // { value: "week", label: "周" },
  { value: "day", label: "日" },
]);
const tableDatas = ref([
  {
    date: "2016-05-04",
    name: "1离心泠机",
    procType: "维修工单",
    procType1: "维修工单",
    procType2: "待指派",
  },
  {
    date: "2016-05-03",
    name: "保养计划1的工单",
    procType: "保养工单",
    procType1: "保养计划",
    procType2: "待指派",
  },
  {
    date: "2016-05-02",
    name: "巡检计划1的工单",
    procType: "巡检工单",
    procType1: "人工申报",
    procType2: "待指派",
  },
  {
    date: "2016-05-01",
    name: "1离心泠机",
    procType: "维修工单",
    procType1: "人工申报",
    procType2: "待指派",
  },
  {
    date: "2016-05-04",
    name: "巡检计划1的工单",
    procType: "巡检工单",
    procType1: "人工申报",
    procType2: "待指派",
  },
  {
    date: "2016-05-03",
    name: "保养计划1的工单",
    procType: "保养工单",
    procType1: "保养计划",
    procType2: "待指派",
  },
  {
    date: "2016-05-02",
    name: "2离心泠机",
    procType: "维修工单",
    procType1: "维修工单",
    procType2: "待指派",
  },
  {
    date: "2016-05-01",
    name: "巡检计划2的工单",
    procType: "巡检工单",
    procType1: "人工申报",
    procType2: "待指派",
  },
]);
const tableData = ref([
  {
    date: "2016-05-04",
    name: "1离心泠机",
    procType: "维修工单",
    procType1: "维修工单",
    procType2: "待指派",
  },
  {
    date: "2016-05-03",
    name: "保养计划1的工单",
    procType: "保养工单",
    procType1: "保养计划",
    procType2: "待指派",
  },
  {
    date: "2016-05-02",
    name: "巡检计划1的工单",
    procType: "巡检工单",
    procType1: "人工申报",
    procType2: "待指派",
  },
  {
    date: "2016-05-01",
    name: "1离心泠机",
    procType: "维修工单",
    procType1: "人工申报",
    procType2: "待指派",
  },
  {
    date: "2016-05-04",
    name: "巡检计划1的工单",
    procType: "巡检工单",
    procType1: "人工申报",
    procType2: "待指派",
  },
  {
    date: "2016-05-03",
    name: "保养计划1的工单",
    procType: "保养工单",
    procType1: "保养计划",
    procType2: "待指派",
  },
  {
    date: "2016-05-02",
    name: "2离心泠机",
    procType: "维修工单",
    procType1: "维修工单",
    procType2: "待指派",
  },
  {
    date: "2016-05-01",
    name: "巡检计划2的工单",
    procType: "巡检工单",
    procType1: "人工申报",
    procType2: "待指派",
  },
]);
function edit(id) {
  console.log(tableRef.value);
  childBorder.value = true;
}
function del(id) {}
const query = async () => {};
const reset = () => {};
onMounted(() => {});
</script>
<style lang="scss" scoped>
:deep(.el-select__wrapper) {
  border-radius: 20px;
  border: 1px solid rgba(2, 136, 136, 0.1);
  box-shadow: 1px 1px 2px 0px rgba(2, 136, 136, 0.22);
}
:deep(.el-table td.el-table__cell){
    border-bottom: 1px solid rgba(2, 136, 136, 0.2);
}
.el-button {
  border-radius: 20px;
  //   border: 1px solid rgba(2,136,136, 0.1);
  box-shadow: 1px 1px 2px 0px rgba(2, 136, 136, 0.22);
  background: linear-gradient(to bottom, rgba(2, 136, 136, 0.3), rgba(2, 136, 136, 0.8));
  color: white;
}
.item_body {
  width: 100%;
  height: 100%;
  border: 1px solid rgba(2, 136, 136, 0.05);
  background: #fff;
  border-radius: 12px;
  padding: 15px;
  overflow: auto;
  .item_body_list {
    .list_L {
      display: inline-block;
      vertical-align: top;
    }
    .list_R {
      display: inline-block;
      vertical-align: top;
      border: 1px solid #eee;
      box-shadow: 1px 1px 2px 1px #eee;
      padding: 0.5vw;
     img {
       width: 100%;
       height: 100%;
     }
    }
  }
}
:deep(.el-table__header-wrapper) {
  .el-table__header {
    tr {
      th {
        background: rgba(2, 136, 136, 0.1);
        height: 30px;
        color: #444;
        border-bottom: 3px solid rgba(2, 136, 136, 1);
      }
    }
  }
}
</style>
