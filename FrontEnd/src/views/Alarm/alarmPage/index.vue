<!-- 告警 -->
<template>
  <div class="item_body">
    <div class="item_body_top">
      <div class="body_top_items1">
        当前告警
        <div class="parent"><span>95</span></div>
      </div>
      <div class="body_top_items1">
        派单告警
        <div class="parent"><span>90</span></div>
      </div>
      <div class="body_top_items1">
        关闭告警
        <div class="parent"><span>15</span></div>
      </div>
      <div class="body_top_items2">
        当前系统告警
        <div class="parent">
          <div class="parent_ec">
            <Echarts_B></Echarts_B>
            <div class="ec_text"><span>28</span><br />空调</div>
          </div>
          <div class="parent_ec">
            <Echarts_Y></Echarts_Y>
            <div class="ec_text"><span>35</span><br />照明</div>
          </div>
          <div class="parent_ec">
            <Echarts_G></Echarts_G>
            <div class="ec_text"><span>32</span><br />厨电</div>
          </div>
        </div>
      </div>
    </div>
    <div class="item_body_bottom">
        <div class="topTitle">告警列表<br /><span></span></div>
      <div class="bottom_item">
        
        <div class="bottom_list">
          <el-table :data="tableData" style="width: 100%" height="100%">
            <el-table-column type="selection" width="40" align="center" />
            <el-table-column label="序号" width="60" type="index" align="center">
            </el-table-column>
            <el-table-column
              property="name"
              label="工单名称"
              width="180px"
              align="center"
            />
            <el-table-column property="name" label="告警名称" align="center" />
            <el-table-column property="procType" label="告警等级" align="center" />
            <el-table-column property="procType1" label="告警状态" align="center" />
            <el-table-column property="procType2" label="区域" align="center" />
            <el-table-column property="procType3" label="子系统" align="center" />
            <el-table-column property="procType4" label="设备" align="center" />
            <el-table-column property="date" label="生成时间" align="center" />
            <el-table-column property="date" label="告警时间" align="center" />
            <el-table-column property="procType5" label="告警次数" align="center" />
            <el-table-column property="procType6" label="持续时间" align="center" />
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import Echarts_B from "./echarts/echarts_B.vue";
import Echarts_Y from "./echarts/echarts_Y.vue";
import Echarts_G from "./echarts/echarts_G.vue";
const tableData = ref([
  {
    date: "2016-05-04",
    name: "温度过高，大于30℃",
    procType: "严重",
    procType1: "打开",
    procType2: "客厅",
    procType3: "空调系统",
    procType4: "11#空调",
    procType5: "3",
    procType6: "1h 10min",
  },
  {
    date: "2016-05-04",
    name: "温度过高，大于30℃",
    procType: "严重",
    procType1: "关闭",
    procType2: "客厅",
    procType3: "空调系统",
    procType4: "10#空调",
    procType5: "5",
    procType6: "1h 10min",
  },
  {
    date: "2016-05-04",
    name: "温度过高，大于30℃",
    procType: "严重",
    procType1: "关闭",
    procType2: "客厅",
    procType3: "空调系统",
    procType4: "8#空调",
    procType5: "1",
    procType6: "1h 10min",
  },
  {
    date: "2016-05-04",
    name: "温度过高，大于30℃",
    procType: "严重",
    procType1: "打开",
    procType2: "客厅",
    procType3: "空调系统",
    procType4: "9#空调",
    procType5: "2",
    procType6: "1h 10min",
  },
  {
    date: "2016-05-04",
    name: "温度过高，大于30℃",
    procType: "严重",
    procType1: "打开",
    procType2: "客厅",
    procType3: "空调系统",
    procType4: "3#空调",
    procType5: "4",
    procType6: "1h 10min",
  },
  {
    date: "2016-05-04",
    name: "温度过高，大于30℃",
    procType: "严重",
    procType1: "关闭",
    procType2: "客厅",
    procType3: "空调系统",
    procType4: "4#空调",
    procType5: "2",
    procType6: "1h 10min",
  },
  {
    date: "2016-05-04",
    name: "温度过高，大于30℃",
    procType: "严重",
    procType1: "打开",
    procType2: "客厅",
    procType3: "空调系统",
    procType4: "5#空调",
    procType5: "1",
    procType6: "1h 10min",
  },
  {
    date: "2016-05-04",
    name: "温度过高，大于30℃",
    procType: "严重",
    procType1: "打开",
    procType2: "客厅",
    procType3: "空调系统",
    procType4: "6#空调",
    procType5: "3",
    procType6: "1h 10min",
  },
]);
onMounted(() => {});
</script>
<style lang="scss" scoped>
::v-deep(.el-radio__input.is-checked .el-radio__inner) {
  border-color: #028888 !important;
  background-color: #028888 !important;
}
::v-deep(.el-radio-button__inner:hover) {
  color: #028888 !important;
}
::v-deep(.el-radio__input.is-checked + .el-radio__label) {
  font-weight: 700;
  color: #028888 !important;
}
.item_body {
  width: 100%;
  height: 100%;
  .item_body_top {
    height: 15%;
    background: none;
    width: 100%;
    .body_top_items1 {
      width: calc(18% - 15px);
      height: 100%;
      display: inline-block;
      vertical-align: top;
      font-size: 1.6vh;
      //   text-align: center;
      position: relative;
      padding: 0.8%;
      border-radius: 12px;
      background: linear-gradient(
        to bottom,
        rgba(2, 136, 136, 0.2),
        rgba(2, 136, 136, 0.1),
        rgba(2, 136, 136, 0.1),
        rgba(2, 136, 136, 0)
      );
      border: 1px solid rgba(2, 136, 136, 0.05);
      margin-right: 15px;
      .parent {
        position: absolute;
        // width: 100%;
        top: 40%;
        transform: translateY(-40%);
        left: 50%;
        transform: translateX(-50%);
        span {
          font-size: 3vh;
          font-weight: 600;
          color: #333;
        }
      }
    }
    .body_top_items2 {
      width: 46%;
      height: 100%;
      display: inline-block;
      vertical-align: top;
      font-size: 1.6vh;
      //   text-align: center;
      position: relative;
      padding: 0.8%;
      border-radius: 12px;
      background: linear-gradient(
        to bottom,
        rgba(2, 136, 136, 0.2),
        rgba(2, 136, 136, 0.1),
        rgba(2, 136, 136, 0.1),
        rgba(2, 136, 136, 0)
      );
      border: 1px solid rgba(2, 136, 136, 0.05);
      .parent {
        width: 100%;
        height: calc(100% - 2vh);
        span {
          font-size: 3vh;
          font-weight: 600;
          color: #333;
        }
        .parent_ec {
          position: relative;
          display: inline-block;
          width: 33.33%;
          height: 100%;
          .ec_text {
            position: absolute;
            right: 3vh;
            top: 1vh;
          }
        }
      }
    }
  }
  .item_body_bottom {
    height: calc(85% - 15px);
    background: #fff;
    width: 100%;
    border-radius: 12px;
    padding: 15px;
    margin-top: 15px;
    .bottom_item {
      width: calc(100% - 15px);
      height: calc(100% - 10px);
    //   background: #fff;
      padding: 15px 15px;
    //   border: 1px solid #eee;
      border-radius: 12px;
    //   box-shadow: 0 0 6px 1px #eee;
      .bottom_list {
        padding: 0px;
        overflow: auto;
        height: calc(100% - 0px);
        // border: 1px solid #eee;
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
}
</style>
