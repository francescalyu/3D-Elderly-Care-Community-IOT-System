<!-- 设备告警记录 -->
<template>
  <div class="content_warp h-full">
    <el-form ref="queryForm" :inline="true" :model="formData" class="flex flex-wrap">
      <el-form-item label="设备名称">
        <el-input v-model="formData.equipmentName" placeholder="设备名称" style="width: 150px" />
      </el-form-item>
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
      <el-form-item label="所在建筑">
        <el-select v-model="formData.building" style="width: 150px">
          <el-option v-for="item in buildList" :key="item.value" :label="item.itemText" :value="item.itemValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="所在楼层">
        <el-select v-model="formData.floor" style="width: 150px">
          <el-option v-for="item in floorList" :key="item.value" :label="item.itemText" :value="item.itemValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="所在区域">
        <el-select v-model="formData.equipmentLocation" style="width: 150px">
          <el-option v-for="item in areaList" :key="item.value" :label="item.itemText" :value="item.itemValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="告警级别">
        <el-select v-model="formData.alarmLevelId" style="width: 150px">
          <el-option v-for="item in alarmLevelList" :key="item.id" :label="item.name" :value="item.id" />
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
      <el-form-item label="恢复时间">
        <el-date-picker
          v-model="formData.recoverTime"
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
        <!-- <XButton icon="Document" @click="toWork">
          批量转工单
        </XButton> -->
        <!-- <XButton icon="delete" @click="del">
          批量删除
        </XButton> -->
      </el-form-item>
    </el-form>
    <el-table
      ref="tableRef"
      :data="tableData"
      class="mb-[32px] customTable mt-[16px]"
      @selection-change="handleSelectionChange"
      :row-key="getRowKeys"
    >
      <el-table-column align="center" type="selection" width="55" :reserve-selection="true" />
      <el-table-column label="序号" align="center" type="index" width="70" />
      <el-table-column prop="alarmContent" align="center" label="告警内容" />
      <el-table-column prop="alarmLevelName" align="center" label="告警级别" />
      <el-table-column prop="alarmTypeStr" align="center" label="告警类型" />
      <el-table-column prop="equipmentName" align="center" label="告警设备" min-width="100px" />
      <el-table-column prop="building" align="center" label="所在建筑" />
      <el-table-column prop="floor" align="center" label="所在楼层" />
      <el-table-column prop="equipmentLocation" align="center" label="所在区域" />
      <el-table-column prop="installLocation" align="center" label="安装位置" />
      <el-table-column prop="alarmTime" align="center" label="告警时间" width="150px" />
      <el-table-column prop="alarmStatusStr" align="center" label="告警状态" />
      <el-table-column prop="recoverTime" align="center" label="恢复时间" width="150px" />
      <el-table-column prop="duration" align="center" label="持续时间" />
      <!-- <el-table-column prop="transferOrderNo" align="center" label="工单号" min-width="100px" />
      <el-table-column prop="transferOrderNominee" align="center" label="工单指派人" min-width="100px" /> -->
      <el-table-column align="center" prop="topScreen" label="操作" width="180px">
        <template #default="{ row }">
          <div>
            <el-link class="blue-link" @click="view(row)"> 查看 </el-link>
            <!-- <el-link class="yellow-link" @click="doDeal(row.alarmType, row.id)" :disabled="row.alarmStatus!==0">
              处理
            </el-link> -->
            <el-link v-if="row.alarmStatus == 0" class="yellow-link" @click="doDeal(row.alarmType, row.id)"> 处理 </el-link>
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

    <DealSystemAlarmFault ref="DealSystemAlarmFaultRef" @submit="submit"></DealSystemAlarmFault>
    <ToWorkOrderBatch ref="ToWorkOrderBatchRef" @submit="submit"></ToWorkOrderBatch>
    <DealSystemAlarmAll ref="DealSystemAlarmAllRef" @submit="submit"></DealSystemAlarmAll>
    <DetailSystemAlarmFault ref="DetailSystemAlarmFaultRef" @toDealAlarm="toDealAlarm"></DetailSystemAlarmFault>
    <DetailSystemAlarmThreshold ref="DetailSystemAlarmThresholdRef" @toDealAlarm="toDealAlarm"> </DetailSystemAlarmThreshold>
  </div>
</template>

<script lang="ts" setup>
import { getAlarmStrategyRecordPage, getAlarmLevelList, delAlarmStrategyRecord,delAlarmStrategyRecordAll } from "../ts/index";
import { getDict } from "@/api/common.ts";
import dayjs from "dayjs";
import DealSystemAlarmFault from "./components/DealSystemAlarmFault.vue";
import DealSystemAlarmAll from "./components/DealSystemAlarmAll.vue";
import DetailSystemAlarmFault from "./components/DetailSystemAlarmFault.vue";
import DetailSystemAlarmThreshold from "./components/DetailSystemAlarmThreshold.vue";
import ToWorkOrderBatch from "./components/ToWorkOrderBatch.vue";
import cloneDeep from "lodash/cloneDeep";

//
const DetailSystemAlarmFaultRef = ref();
const DetailSystemAlarmThresholdRef = ref();
const DealSystemAlarmFaultRef = ref();
const ToWorkOrderBatchRef = ref();
const tableRef = ref();
const DealSystemAlarmAllRef = ref()
//
const formData = reactive({
  equipmentName: "",
  alarmType: "",
  alarmStatus: null,
  building: "",
  floor: "",
  equipmentLocation: "",
  alarmLevelId: "",
  alarmTime: [],
  recoverTime: [],
  alarmStrategyType: 0,
});
const pageParam = ref<any>({
  pageSize: 10,
  total: 0,
  pageNo: 1,
});
const alarmTypeList = ref<any[]>([
  { name: "故障告警", value: 1 },
  { name: "阈值告警", value: 2 },
]);
const alarmStatusList = ref<any[]>([
  { name: "待处理", value: 0 },
  // { name: "处理中", value: 1 },
  { name: "已处理", value: 2 },
  { name: "误报", value: 4 },
  // { name: "已解除", value: 3 },
]);
const buildList = ref<any[]>([]);
const floorList = ref<any[]>([]);
const areaList = ref<any[]>([]);
const alarmLevelList = ref<any[]>([]);
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
  formData.alarmStrategyType = 0;
  formData.alarmTime = [];
  formData.recoverTime = [];
  getData();
}
const handleCurrentChange = (page: any) => {
  pageParam.value.pageNo = page;
  query();
};
const handleSizeChange = (pageSize: any) => {
  pageParam.value.pageSize = pageSize;
  query();
};
// 批量转工单
function toWork() {
  if (selection.value.length === 0) {
    ElMessage.error("请在表格中勾选");
  } else {
    // toWorkOrderBatch
    ToWorkOrderBatchRef.value?.open(selection.value);
  }
}
function toDealAlarm(type, id) {
  doDeal(type, id);
}
// 批量处理
function doDeals() {
  if (selection.value.length === 0) {
    ElMessage.warning("请勾选项");
  } else {
    DealSystemAlarmAllRef.value?.open(selection.value,'edit')
  }
}

function view(row) {
  // console.log('row: ', row);
  // console.log('row.alarmType: ', row.alarmType);
  // 故障告警
  if (row.alarmType === 1) {
    DetailSystemAlarmFaultRef.value?.open(row);
  }
  // 阈值告警
  if (row.alarmType === 2) {
    DetailSystemAlarmThresholdRef.value?.open(row);
  }
}
function doDeal(type, id) {
  // console.log('id: ', id);
  // console.log('type: ', type);
  // 1.故障告警,2.阈值告警
  if (type === 1) {
    DealSystemAlarmFaultRef.value?.open(id, "故障告警");
  }
  // 阈值告警
  if (type === 2) {
    DealSystemAlarmFaultRef.value?.open(id, "阈值告警");
  }
}
function viewDoDeal(type, id, data) {
  // 通讯设备
  if (type === 1) {
    DealSystemAlarmFaultRef.value?.open(id, "故障告警", "edit", data);
  }
  // 阈值告警
  if (type === 2) {
    DealSystemAlarmFaultRef.value?.open(id, "阈值告警", "edit", data);
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
  tableRef.value!.clearSelection();
  getData();
}
function getData() {
  let pushData = cloneDeep(formData) as any;
  console.log("pushData: ", pushData);
  if (pushData.alarmTime.length > 0) {
    pushData.alarmTime[0] = dayjs(pushData.alarmTime[0]).valueOf();
    pushData.alarmTime[1] = dayjs(pushData.alarmTime[1]).valueOf();
  }
  if (pushData.recoverTime.length > 0) {
    pushData.recoverTime[0] = dayjs(pushData.recoverTime[0]).valueOf();
    pushData.recoverTime[1] = dayjs(pushData.recoverTime[1]).valueOf();
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
  getAlarmLevelList({}).then((res) => {
    console.log("res: ", res);
    alarmLevelList.value = res.data;
  });
  // 字典查询，
  getDict("equipment_building").then((res: any) => {
    if (res.code === 200 && res.data.length > 0) buildList.value = res.data;
  });
  getDict("equipment_floor").then((res: any) => {
    if (res.code === 200 && res.data.length > 0) floorList.value = res.data;
  });
  getDict("equipment_area").then((res: any) => {
    if (res.code === 200 && res.data.length > 0) areaList.value = res.data;
  });
});
</script>

<style lang="scss" scoped>
.content_warp {
  margin-top: 20px;
  height: calc(100% - 20px);
}
:deep(.customTable) {
  height: calc(100% - 200px) !important;
  max-height: calc(100% - 200px) !important;

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
