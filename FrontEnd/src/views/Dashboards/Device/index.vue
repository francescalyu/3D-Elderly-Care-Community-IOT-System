<!-- 设备 -->
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
              :key="item.id"
              :label="item.alias"
              :value="item.id"
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
      <el-table
        :data="tableData"
        row-key="id"
        :expand-row-keys="expandedRowKeys"
        style="width: 100%"
        height="100%"
        ref="tableRef"
        @expand-change="tableChange"
        preserve-expanded-content="true"
      >
        <el-table-column type="expand" width="35">
          <template #default="{ row }">
            <div class="list_L" style="width: calc(70% - 10px); margin-right: 10px">
              <el-table
                :data="tableDatas"
                :border="childBorder"
                style="width: 100%"
                height="100%"
              >
                <el-table-column type="index" label="序号" align="center" width="55" />
                <el-table-column property="alias" label="点位名称" align="center" />
                <el-table-column property="type" label="点位类型" align="center" />
                <el-table-column property="value" label="当前采集值" align="center" />
                <el-table-column align="center" label="操作" prop="topScreen">
                  <template #default="{ row }">
                    <div>
                      <!-- <el-link class="blue-link" @click="edit(row)"> 查看点位 </el-link> | -->
                      <el-link class="blue-link" @click="del(row.id)"> 下发命令 </el-link>
                    </div>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div class="list_R" style="width: calc(30% - 10px); margin-left: 10px">
              <div class="list_R_top">下发命令</div>
              <div class="list_R_content">
                <div style="margin-bottom: 10px">
                  <span>参数值</span>
                  <el-input
                    v-model="input1"
                    style="width: 9vw"
                    placeholder="Pick a date"
                    :suffix-icon="Calendar"
                  />
                  <span>注: 1-开 &nbsp;&nbsp;0-关</span>
                </div>
                <div>
                  <span>参数值</span>
                  <el-input
                    v-model="input1"
                    style="width: 9vw"
                    placeholder="Pick a date"
                    :suffix-icon="Calendar"
                  />
                  <span>注: 参数范围0~30°C</span>
                </div>
              </div>
              <div class="list_R_btn"><el-button @click="query"> 下 发 </el-button></div>
            </div>
          </template>
        </el-table-column>
        <el-table-column type="index" label="序号" align="center" width="55" />
        <el-table-column property="product" label="设备名称" align="center" width="255" />
        <el-table-column property="id" label="设备ID" align="center" width="75" />
        <el-table-column property="sys" label="子系统" align="center" width="75" />
        <el-table-column property="area" label="空间" align="center" width="75" />
        <el-table-column property="manu" label="厂商" align="center" />
        <el-table-column property="manuId" label="厂商编码" align="center" />
        <el-table-column property="ip" label="设备IP" align="center" />
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
import { getall, getSubsysAll, getdevpoint } from "../api/index";
const tableRef = ref();
const queryForm = ref({
  dateType: "",
});
const expandedRowKeys = ref([]);
const childBorder = ref(false);
const dateTypeOptions = ref([]);
const tableDatas = ref([
  
]);
const tableData = ref([

]);
const allData = ref([]);
const tableChange = (val) => {
  console.log(val);
  // const index = expandedRowKeys.value.indexOf(val.id);
  expandedRowKeys.value = [val.id];
  if (expandedRowKeys.value.length > 0) {
    // 如果有行被展开，则收起之前展开的行
    expandedRowKeys.value = [val.id];
  } else {
    // 如果没有行被展开，则清空expandedRowKeys
    expandedRowKeys.value = [];
  }
  getdevpoint(val.id).then((res) => {
    console.log(res)
    tableDatas.value=res.status
  })
};
const getallData = () => {
  getall().then((res) => {
    tableData.value = res;
    tableChange(tableData.value[0])
  });
};
const getSubsysAllData = () => {
  getSubsysAll().then((res) => {
    dateTypeOptions.value = res;
    queryForm.value.dateType = dateTypeOptions.value[0].id;
  });
};
function edit(id) {
  console.log(tableRef.value);
  childBorder.value = true;
}
function del(id) {}
const query = async () => {};
const reset = () => {};
onMounted(() => {
  getallData();
  getSubsysAllData();
});
</script>
<style lang="scss" scoped>
:deep(.el-select__wrapper) {
  border-radius: 20px;
  border: 1px solid rgba(2, 136, 136, 0.1);
  box-shadow: 1px 1px 2px 0px rgba(2, 136, 136, 0.22);
}
:deep(.el-table td.el-table__cell) {
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
      .list_R_top {
        border-bottom: 1px solid #eee;
        padding: 5px;
      }
      .list_R_content {
        padding: 0.5vw;
        font-size: 1.4vh;
        div {
          span {
            margin-right: 0.5vw;
            margin-left: 0.5vw;
          }
        }
      }
      .list_R_btn {
        text-align: right;
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
