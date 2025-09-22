<!-- 健康 -->
<template>
  <div class="item_body">
    <div class="item_body1">
      <div class="item_body1_L">
        <div>户主信息</div>
        <div class="body1_L_text">
          <div>
            <span>{{ userData.name }}</span> <br />姓名
          </div>
          <div>
            <span>{{ userData.age }}</span> <br />年龄
          </div>
          <div>
            <span>{{ userData.sex == "1" ? "男" : "女" }}</span> <br />性别
          </div>
          <!-- <div><span>{{userData.sex=='1'?'男':'女'}}</span> <br /></div> -->
        </div>
        <img src="@/assets/img/yqqy.png" alt="" />
      </div>
      <div class="item_body1_R">
        <div>健康信息</div>
        <div class="body1_L_text">
          <div>
            <div class="body1_L_texts">
              智能手环 | <span> {{ HealthData.online == "1" ? "在线" : "离线" }}</span>
            </div>
            <div class="body1_L_texts">
              手环电量 | <span> {{ HealthData.battery_percentage }}</span>
            </div>
            <div class="body1_L_texts">
              手环佩戴信息 |
              <span> {{ HealthData.not_wearing_alert == "1" ? "正常" : "告警" }}</span>
            </div><br>
            <div class="body1_L_texts">
              心率 | <span> {{ HealthData.heart_rate }} bpm</span>
            </div>
            <div class="body1_L_texts">
              血压 | <span> {{ HealthData.blood_pressure }} mmHg</span>
            </div>
            <div class="body1_L_texts">
              体温 | <span> {{ HealthData.body_temperature }} °C</span>
            </div><br>
            <div class="body1_L_texts">
              当日总步数 | <span> {{ HealthData.total_steps }} 步</span>
            </div>
            <div class="body1_L_texts">
              当日消耗 | <span> {{ HealthData.total_calories }} Cal</span>
            </div>
            <div class="body1_L_texts">
              总睡眠时间 | <span> {{ HealthData.total_sleep }} min</span>
            </div>
          </div>

          <!-- <div>健康状态 | <span>最近一周频繁头晕，睡眠质量差，体力明星下降</span></div> -->
        </div>
        <img src="@/assets/img/yqsh.png" alt="" />
      </div>
    </div>
    <div class="item_body2">
      <div class="topTitle">健康监测<br /><span></span></div>
      <div class="item_body2_ecs">
        <div class="item_body2_ec">
          <EchartsItem1 :data-item="bloodpressureData"></EchartsItem1>
        </div>
        <div class="item_body2_ec">
          <EchartsItem2 :data-item="heartrateData"></EchartsItem2>
        </div>
      </div>
    </div>
    <div class="item_body3">
      <div class="item_body3_L">
        <div class="topTitle">运动信息<br /><span></span></div>
        <div class="item_body3_ec">
          <div class="ec_title">
            今日步数: <span>{{ HealthData.total_steps }}步</span> &nbsp; 消耗:
            <span>{{ HealthData.total_calories }}Cal</span>
          </div>
          <EchartsItem3 :data-item="stepData" :date-item="timeData"></EchartsItem3>
        </div>
      </div>
      <div class="item_body3_R">
        <div class="topTitle">睡眠信息<br /><span></span></div>
        <div class="item_body3_ec">
          <div class="ec_title">
            今日睡眠: <span>{{ HealthData.total_sleep }}min</span> &nbsp; 睡眠质量:
            <span>正常</span>
          </div>
          <EchartsItem4 :data-item="sleepData" :date-item="timeData"></EchartsItem4>
        </div>
      </div>
    </div>
    <div class="item_body4">
      <div class="topTitle">告警列表<br /><span></span></div>
      <div class="content_list">
        <el-table :data="tableData" style="width: 100%" height="100%">
          <el-table-column type="selection" width="30" />
          <el-table-column type="index" label="序号" align="center" width="55" />
          <el-table-column property="area" label="告警空间" align="center" />
          <el-table-column property="devName" label="设备名称" align="center" />
          <el-table-column property="devid" label="设备号" align="center" />
          <el-table-column property="count" label="设备告警数" align="center" />
          <el-table-column property="createTime" label="告警时间" align="center" />
          <el-table-column property="delay" label="告警时长(分钟)" align="center" />
          <el-table-column property="subsys" label="子系统" align="center" />
          <el-table-column property="ruleid" label="规则号" align="center" />
          <el-table-column property="level" label="级别" align="center" />
          <el-table-column property="status" label="状态" align="center" />
          <el-table-column property="date" label="操作" align="center" />
        </el-table>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import EchartsItem2 from "./echarts/echarts2.vue";
import EchartsItem1 from "./echarts/echarts1.vue";
import EchartsItem3 from "./echarts/echarts3.vue";
import EchartsItem4 from "./echarts/echarts4.vue";
import { getHealth, getHealthbyHour, getTenant,getTodayWarn } from "../api/index";
import dayjs from "dayjs";
const hourTime = dayjs(new Date()).format("YYYYMMDD");
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
const HealthData = ref({});
const HealthbyHourData = ref({});
const bloodpressureData = ref([]); //血压
const heartrateData = ref([]); //心率
const stepData = ref([]); //步数
const stepSum = ref(0);
const sleepSum = ref(0);
const sleepData = ref([]); //睡眠
const timeData = ref([]);
const userData = ref({});
const getHealthData = () => {
  getHealth().then((res) => {
    console.log(res);
    HealthData.value = res;
  });
};
const getHealthbyHourData = () => {
  getHealthbyHour(hourTime).then((res) => {
    console.log(res);
    HealthbyHourData.value = res;
    for (let i = 0; i < HealthbyHourData.value.length; i++) {
      bloodpressureData.value.push(HealthbyHourData.value[i].bloodpressure);
      heartrateData.value.push(HealthbyHourData.value[i].heartrate);
      stepData.value.push(HealthbyHourData.value[i].step);
      stepSum.value += Number(HealthbyHourData.value[i].step);
      sleepData.value.push(HealthbyHourData.value[i].sleep);
      sleepSum.value += Number(HealthbyHourData.value[i].sleep);
      timeData.value.push(dayjs(HealthbyHourData.value[i].time).format("hh"));
    }
    sleepSum.value = (sleepSum.value / 60).toFixed(2);
    // console.log(bloodpressureData.value,heartrateData.value,stepData.value,sleepData.value)
  });
};
const getTodayWarnData = () => { 
  getTodayWarn().then((res) => {
    tableData.value = res;
  });
};
const getTenantData = () => {
  getTenant().then((res) => {
    userData.value = res[0];
  });
};
onMounted(() => {
  getHealthData();
  getHealthbyHourData();
  getTenantData();
  getTodayWarnData();
});
</script>
<style lang="scss" scoped>
.item_body {
  width: 100%;
  height: 100%;
  color: #666;
  .item_body1 {
    height: 15%;
    background: #fff;
    width: 100%;
    border: 1px solid rgba(2, 136, 136, 0.05);
    border-radius: 12px;
    padding: 15px;
    .item_body1_L {
      display: inline-block;
      vertical-align: top;
      height: 100%;
      width: 40%;
      border-radius: 12px;
      background: rgba(255, 210, 135, 0.1);
      padding: 10px 0px 0px 10px;
      font-size: 1.6vh;
      font-weight: 800;
      position: relative;
      .body1_L_text {
        // padding-top: 0.3vh;
        div {
          display: inline-block;
          vertical-align: top;
          text-align: center;
          width: 25.33%;
          span {
            font-size: 2vh;
            color: #111;
          }
        }
      }
      img {
        position: absolute;
        bottom: 0;
        right: 20px;
        width: 12%;
      }
    }
    .item_body1_R {
      display: inline-block;
      vertical-align: top;
      height: 100%;
      width: calc(60% - 15px);
      border-radius: 12px;
      background: rgba(176, 141, 249, 0.1);
      margin-left: 15px;
      padding: 10px 0px 0px 10px;
      font-size: 1.6vh;
      font-weight: 800;
      position: relative;
      .body1_L_text {
        // padding-top: 0.3vh;
        div {
          text-align: center;
          width: 80%;
          .body1_L_texts {
            display: inline-block;
            width: 25%;
          }
          span {
            color: #111;
            font-weight: 800;
          }
        }
      }
      img {
        position: absolute;
        bottom: 0;
        right: 20px;
        width: 8.5%;
      }
    }
  }
  .item_body2 {
    height: calc(26% - 15px);
    background: #fff;
    width: 100%;
    border: 1px solid rgba(2, 136, 136, 0.05);
    border-radius: 12px;
    padding: 10px;
    margin-top: 15px;
    .item_body2_ecs {
      height: calc(100% - 25px);
      .item_body2_ec {
        display: inline-block;
        width: 50%;
        height: 100%;
      }
    }
  }
  .item_body3 {
    height: calc(25% - 15px);
    // background: #fff;
    width: 100%;
    // border: 1px solid rgba(2, 136, 136, 0.05);
    border-radius: 12px;
    // padding: 10px;
    margin-top: 15px;

    .ec_title {
      position: absolute;
      right: 10px;
      color: #999;
      font-size: 1.2vh;
      span {
        font-weight: 800;
        color: #333 !important;
        font-size: 1.5vh;
      }
    }
    .item_body3_ec {
      width: 100%;
      height: calc(100% - 25px);
      position: relative;
    }
    .item_body3_L {
      display: inline-block;
      width: calc(50% - 8px);
      height: 100%;
      border-radius: 12px;
      background: #fff;
      margin-right: 8px;
      padding: 10px;
    }
    .item_body3_R {
      display: inline-block;
      width: calc(50% - 7px);
      height: 100%;
      border-radius: 12px;
      background: #fff;
      margin-left: 7px;
      padding: 10px;
    }
  }
  .item_body4 {
    height: calc(34% - 15px);
    background: #fff;
    width: 100%;
    // border: 1px solid rgba(2, 136, 136, 0.05);
    border-radius: 12px;
    padding: 15px;
    margin-top: 15px;
    .content_list {
      height: calc(100% - 35px);
      margin-top: 10px;
      border: 1px solid rgba(2, 136, 136, 0.1);
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
