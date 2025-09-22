<!-- 告警策略 -->
<template>
  <div class="item_body">
    <div class="item_body_top">
      <el-form ref="form" :model="queryForm" inline class="flex mt-5">
        <el-form-item style="text-align: right" class="flex query_Group">
          <el-button icon="Plus" @click="query"> 新 增 </el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="item_body_list">
      <el-table :data="tableData" style="width: 100%" height="100%" ref="tableRef">
        <el-table-column type="expand" width="35">
          <template #default="props">
            <div class="list_L" style="width: calc(35% - 10px); margin-right: 10px">
              <el-table
                :data="tableDatas"
                :border="childBorder"
                style="width: 100%"
                height="100%"
              >
                <el-table-column label="" align="center">
                  <template #default="{ row }">
                    <div>
                      <div class="list_red_link">告警</div>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column property="name" label="告警条件" align="center" />
                <el-table-column property="procType" label="告警关系" align="center" />
              </el-table>
            </div>
            <div class="list_R" style="width: calc(65% - 10px); margin-left: 10px">
              <div class="list_Rs">
                <div class="list_R_top">规则名称</div>
                <div class="list_R_content">
                  <div style="margin-bottom: 10px">
                    <span>客厅空调规则1</span>
                  </div>
                  <div>
                    <span>规则2</span>
                  </div>
                </div>
              </div>
              <div class="list_Rs">
                <div class="list_R_top">启用</div>
                <div class="list_R_content">
                  <div style="margin-bottom: 10px">
                    <el-switch
                      size="small"
                      active-color="#028888"
                      v-model="value1"
                      class="ml-2"
                    />
                  </div>
                  <div>
                    <el-switch
                      size="small"
                      active-color="#028888"
                      v-model="value2"
                      class="ml-2"
                    />
                  </div>
                </div>
              </div>
              <div class="list_Rs">
                <div class="list_R_top">触发次数</div>
                <div class="list_R_content">
                  <div style="margin-bottom: 10px">
                    <span>5</span>
                  </div>
                  <div>
                    <span>-</span>
                  </div>
                </div>
              </div>
              <div class="list_Rs">
                <div class="list_R_top">持续时间</div>
                <div class="list_R_content">
                  <div style="margin-bottom: 10px">
                    <span>-</span>
                  </div>
                  <div>
                    <span>60s</span>
                  </div>
                </div>
              </div>
              <div class="list_Rs">
                <div class="list_R_top">告警对象</div>
                <div class="list_R_content">
                  <div style="margin-bottom: 10px">
                    <span>所有空调</span>
                  </div>
                  <div>
                    <span>空调#1、空调#2</span>
                  </div>
                </div>
              </div>
              <div class="list_Rs">
                <div class="list_R_top">区域</div>
                <div class="list_R_content">
                  <div style="margin-bottom: 10px">
                    <span>客厅</span>
                  </div>
                  <div>
                    <span>-</span>
                  </div>
                </div>
              </div>
              <!-- <div class="list_R_btn"><el-button @click="query"> 下 发 </el-button></div> -->
            </div>
          </template>
        </el-table-column>

        <el-table-column type="index" label="序号" align="center" width="55" />
        <el-table-column property="name" label="告警名称" align="center" />
        <el-table-column property="procType" label="规则数量" align="center" />
        <el-table-column property="procType1" label="等级" align="center" />
        <el-table-column property="procType2" label="子系统" align="center" />
        <el-table-column align="center" label="操作" prop="topScreen">
          <template #default="{ row }">
            <div>
              <el-link class="blue-link" @click="edit(row)"> 编辑 </el-link> |
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
    name: "空调温度>30℃",
    procType: "或",
    procType1: "维修工单",
    procType2: "待指派",
  },
  {
    date: "2016-05-03",
    name: "空调温度>30℃",
    procType: "-",
    procType1: "保养计划",
    procType2: "待指派",
  },
  {
    date: "2016-05-02",
    name: "空调温度>30℃",
    procType: "或",
    procType1: "人工申报",
    procType2: "待指派",
  },
]);
const tableData = ref([
  {
    date: "2016-05-04",
    name: "温度异常告警",
    procType: "3",
    procType1: "告警",
    procType2: "通风系统",
  },
  {
    date: "2016-05-03",
    name: "温度异常告警",
    procType: "2",
    procType1: "告警",
    procType2: "通风系统",
  },
  {
    date: "2016-05-02",
    name: "温度异常告警",
    procType: "1",
    procType1: "告警",
    procType2: "通风系统",
  },
  {
    date: "2016-05-01",
    name: "温度异常告警",
    procType: "3",
    procType1: "告警",
    procType2: "通风系统",
  },
  {
    date: "2016-05-04",
    name: "温度异常告警",
    procType: "5",
    procType1: "告警",
    procType2: "通风系统",
  },
  {
    date: "2016-05-03",
    name: "温度异常告警",
    procType: "1",
    procType1: "告警",
    procType2: "通风系统",
  },
  {
    date: "2016-05-02",
    name: "温度异常告警",
    procType: "2",
    procType1: "告警",
    procType2: "通风系统",
  },
  {
    date: "2016-05-01",
    name: "温度异常告警",
    procType: "3",
    procType1: "告警",
    procType2: "通风系统",
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
      .list_red_link{
            background: red;
            width: 5vh;
            height: 3vh;
            line-height: 3vh;
            color: #fff;
            border-radius: 6px;
        }
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
        height: 4vh;
        line-height: 2vh;
      }
      .list_R_content {
        padding: 0.5vw;
        font-size: 1.4vh;
        height: 10vh;
        div {
          height: 50%;
          line-height: 4vh;
          span {
            margin-right: 0.5vw;
            margin-left: 0.5vw;
          }
        }
      }
      .list_R_btn {
        text-align: right;
      }
      .list_Rs {
        display: inline-block;
        width: 16.6%;
        text-align: center;
        vertical-align: top;
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
