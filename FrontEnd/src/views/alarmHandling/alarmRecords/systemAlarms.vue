<!-- 告警记录-系统告警 -->
<template>
  <div class="content_warp h-full">
    <el-form ref="queryForm" :inline="true" :model="formData" class="flex flex-wrap">
      <el-form-item label="告警类型">
        <el-select v-model="formData.alarmType" style="width: 150px">
          <el-option v-for="item in alarmTypeList" :key="item.value" :label="item.name" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="告警状态">
        <el-select v-model="formData.alarmStatus" style="width: 150px">
          <el-option v-for="item in alarmStatusList" :key="item.value" :label="item.name" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="告警时间">
        <el-date-picker
          v-model="formData.alarmTime"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item style="flex: 1" class="flex query_Group">
        <XButton icon="Search" @click="query"> 查询 </XButton>
        <XButton icon="refresh" @click="reset"> 重置 </XButton>
        <XButton icon="" @click="doDeals"> 批量处理 </XButton>
        <XButton icon="" @click="dels"> 批量删除 </XButton>
      </el-form-item>
    </el-form>
    <el-table ref="tableRef" :data="tableData" class="mb-[32px] customTable mt-[5px]" @selection-change="handleSelectionChange" :row-key="getRowKeys">
      <el-table-column align="center" type="selection" width="55" :reserve-selection="true" />
      <el-table-column label="序号" align="center" type="index" width="70" />
      <el-table-column prop="alarmContent" align="center" label="告警内容" min-width="100px" />
      <el-table-column prop="alarmLevelName" align="center" label="告警级别" min-width="100px" />
      <el-table-column prop="alarmTypeStr" align="center" label="告警类型" min-width="100px" />
      <el-table-column prop="equipmentName" align="center" label="告警设备" min-width="100px" />
      <el-table-column prop="alarmTime" align="center" label="告警时间" width="200px" />
      <el-table-column prop="alarmStatusStr" align="center" label="告警状态" min-width="100px" />
      <el-table-column prop="recoverTime" align="center" label="恢复时间" width="200px" />
      <el-table-column prop="duration" align="center" label="持续时间" min-width="100px" />
      <!-- <el-table-column prop="transferOrderNo" align="center" label="工单号" min-width="100px" />
      <el-table-column prop="transferOrderNominee" align="center" label="工单指派人" min-width="100px" /> -->
      <el-table-column align="center" prop="topScreen" label="操作" width="180px">
        <template #default="{ row }">
          <div>
            <el-link class="blue-link" @click="view(row)"> 查看 </el-link>
            <el-link v-if="row.alarmStatus === 0" class="yellow-link" @click="doDeal(row.alarmType, row.id)"> 处理 </el-link>
            <el-link v-else class="yellow-link" @click="viewDoDeal(row.alarmType, row.id, row)"> 查看处理 </el-link>
            <el-link class="red-link" @click="del(row.id)"> 删除 </el-link>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:current-page="pageParam.pageNo"
      v-model:page-size="pageParam.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pageParam.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      style="margin-right: 50px"
    />
    <DealSystemAlarmDevice ref="DealSystemAlarmDeviceRef" @submit="submit"></DealSystemAlarmDevice>
    <DealSystemAlarmCollection ref="DealSystemAlarmCollectionRef" @submit="submit"></DealSystemAlarmCollection>
    <DealSystemAlarmAll ref="DealSystemAlarmAllRef" @submit="submit"></DealSystemAlarmAll>
    <DetailSystemAlarmDevice ref="DetailSystemAlarmDeviceRef" @toDealAlarm="toDealAlarm"></DetailSystemAlarmDevice>
    <DetailSystemAlarmCollection ref="DetailSystemAlarmCollectionRef" @toDealAlarm="toDealAlarm"></DetailSystemAlarmCollection>
  </div>
</template>

<script lang="ts" setup>
import { getAlarmStrategyRecordPage, delAlarmStrategyRecord,delAlarmStrategyRecordAll } from "../ts/index";
import DealSystemAlarmDevice from "./components/DealSystemAlarmDevice.vue";
import DealSystemAlarmAll from "./components/DealSystemAlarmAll.vue";
import DealSystemAlarmCollection from "./components/DealSystemAlarmCollection.vue";
import DetailSystemAlarmDevice from "./components/DetailSystemAlarmDevice.vue";
import DetailSystemAlarmCollection from "./components/DetailSystemAlarmCollection.vue";
import dayjs from "dayjs";
import cloneDeep from "lodash/cloneDeep";

//
const DealSystemAlarmDeviceRef = ref();
const DealSystemAlarmCollectionRef = ref();
const DetailSystemAlarmDeviceRef = ref();
const DetailSystemAlarmCollectionRef = ref();
const DealSystemAlarmAllRef = ref()
const tableRef = ref()
//
const formData = reactive({
  alarmType: "",
  alarmStatus: null,
  alarmTime: [],
  alarmStrategyType: 2,
});
const pageParam = ref<any>({
  pageSize: 10,
  total: 0,
  pageNo: 1,
});
const alarmTypeList = ref<any[]>([
  { name: "系统通讯设备告警", value: 6 },
  { name: "系统采集数据异常", value: 3 },
]);
const alarmStatusList = ref<any[]>([
  { name: "待处理", value: 0 },
  // { name: '处理中', value: 1 },
  { name: "已处理", value: 2 },
  // { name: '已解除', value: 3 },
  { name: "误报", value: 4 },
]);
//
const selection = ref<any[]>([]);
const selectionData = ref<any[]>([]);
const tableData = ref<any[]>([]);
//
function query() {
  getData();
}
function reset() {
  for (const k in formData) {
    formData[k] = "";
  }
  formData.alarmStrategyType = 2;
  formData.alarmTime = [];
  query();
}
const handleCurrentChange = (page: any) => {
  pageParam.value.pageNo = page;
  query();
};
const handleSizeChange = (pageSize: any) => {
  pageParam.value.pageSize = pageSize;
  query();
};
//
function view(row) {
  // 通讯设备
  if (row.alarmType === 6) {
    DetailSystemAlarmDeviceRef.value?.open(row);
  }
  // 采集异常
  if (row.alarmType === 3) {
    DetailSystemAlarmCollectionRef.value?.open(row);
  }
}
function viewDoDeal(type, id, data) {
  // 通讯设备
  if (type === 6) {
    DealSystemAlarmDeviceRef.value?.open(id, data);
  }
  // 采集异常
  if (type === 3) {
    DealSystemAlarmCollectionRef.value?.open(id, data);
  }
}
function doDeal(type, id) {
  // console.log('type: ', type);
  // 通讯设备
  if (type === 6) {
    DealSystemAlarmDeviceRef.value?.open(id, "edit");
  }
  // 采集异常
  if (type === 3) {
    DealSystemAlarmCollectionRef.value?.open(id, "edit");
  }
}
// 批量处理
function doDeals() {
  if (selection.value.length === 0) {
    ElMessage.warning("请勾选项");
  } else {
    DealSystemAlarmAllRef.value?.open(selection.value,'edit')
  }
}
function del(id) {
  ElMessageBox.confirm("确认删除？", "提示", { type: "warning" })
    .then(() => {
      delAlarmStrategyRecord(id).then((res: any) => {
        if (res.code === 200) {
          ElMessage.success("删除成功！");
          getData();
        } else {
          ElMessage.error(res.msg || "删除失败");
        }
      });
    })
    .catch(() => {});
}
// 批量删除
function dels() {
  if (selection.value.length === 0) {
    ElMessage.warning("请勾选项");
    return
  } else {
    // const ids = selection.value.join(',')
    ElMessageBox.confirm("确认删除？", "提示", { type: "warning" })
      .then(() => {
        delAlarmStrategyRecordAll(selection.value).then((res: any) => {
          if (res.code === 200) {
            ElMessage.success("删除成功！");
            tableRef.value!.clearSelection();
            getData();
          } else {
            ElMessage.error(res.msg || "删除失败");
          }
        });
      })
      .catch(() => {});
  }
}
const getRowKeys = (row) => {
  return row.id;
};
const handleSelectionChange = (val) => {
  selectionData.value = val;
  selection.value = val.map((item) => {
    return item.id;
  });
};

function submit() {
  getData();
  tableRef.value?.clearSelection()
}
function toDealAlarm(type, id) {
  console.log("type: ", type);
  doDeal(type, id);
}
function getData() {
  let pushData = cloneDeep(formData) as any;
  if (pushData.alarmTime.length > 0) {
    pushData.alarmTime[0] = dayjs(pushData.alarmTime[0]).valueOf();
    pushData.alarmTime[1] = dayjs(pushData.alarmTime[1]).valueOf();
  }
  getAlarmStrategyRecordPage(pushData, pageParam.value).then((res) => {
    if (res) {
      tableData.value = res.data.records || [];
      pageParam.value.total = res.data.total ? res.data.total : 0;
    }
  });
}
onMounted(() => {
  getData();
});
</script>

<style lang="scss" scoped>
.content_warp {
  margin-top: 20px;
  height: calc(100% - 20px);
}
:deep(.customTable) {
  height: calc(100% - 140px) !important;
  max-height: calc(100% - 140px) !important;

  img {
    display: inline-block;
  }

  > div.el-table__inner-wrapper {
    height: 100% !important;
  }
}

.des_div {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
</style>
