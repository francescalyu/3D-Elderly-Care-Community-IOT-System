<!-- 仪表盘 -->
<template>
  <div class="item_body">
    <div class="item_body_top">
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ Number(PowerData.todayPower).toFixed(2) }}</span> kWh<br />当日用电
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ Number(PowerData.currentPower).toFixed(2) }}</span> kW<br />实时功率
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ PowerData.powerSave || 0.0 }}</span> %<br />节能率
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ PowerData.co2 || 0.0 }}</span> kgCO2<br />当月碳减排
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent">
          <span>{{ PowerData.tree || 0 }}</span> 棵<br />等效植树
        </div>
      </div>
    </div>
    <div class="item_body_center">
      <div class="body_center_L">
        <div class="center_L_top">
          <ModelsItem ref="modelsData"></ModelsItem>
        </div>
        <div class="center_L_center">
          <div class="topTitle">设备信息<br /><span></span></div>
          <div class="center_L_center_items">
            <el-row :gutter="24">
              <el-col :span="8" v-for="(item,index) in twinpointData.status" :key="index" style="margin-bottom: .8vh;"
                ><div>{{item.alias}} &nbsp;|</div>
                <div class="items_text" v-if="item.value=='1'">&nbsp;&nbsp;开</div>
                <div class="items_text" v-else-if="item.value=='0'">&nbsp;&nbsp;关</div>
                <div class="items_text" v-else>&nbsp;&nbsp;{{item.value}}</div>
                </el-col
              >
              <!-- <el-col :span="6"
                ><div>设备位置 &nbsp;|</div>
                <div class="items_text">&nbsp;&nbsp;厨房</div></el-col
              > -->
            </el-row>
          </div>
        </div>
      </div>
      <div class="body_center_R">
        <div class="center_R_ec mb">
          <div>
            <div class="topTitle">用能趋势<br /><span></span></div>
            <div class="topTitle_group">
              <el-radio-group
                v-model="radio1"
                fill="#028888"
                text-color="#028888"
                @change="trendChange"
              >
                <el-radio label="1" size="small">日</el-radio>
                <el-radio label="2" size="small">月</el-radio>
              </el-radio-group>
            </div>
          </div>
          <div class="ec_title">
            <div class="ec_title_top">
              累计用电 <span>{{ Number(PowerHourData.sum).toFixed(2) }}</span> kWh
            </div>
            <!-- <div class="ec_title_bottom"></div> -->
          </div>
          <div class="item_echart2">
            <EchartsItem1 :data-time="dayItem" :data-item="PowerHourData"></EchartsItem1>
          </div>
        </div>
        <div class="center_R_ec mb">
          <div>
            <div class="topTitle">分项用能<br /><span></span></div>
            <div class="topTitle_group">
              <el-radio-group v-model="radio2" @change="itemizeChange">
                <el-radio label="1" size="small">日</el-radio>
                <el-radio label="2" size="small">月</el-radio>
              </el-radio-group>
            </div>
            <div class="topTitle_group1">
              <el-radio-group
                v-model="radio3"
                size="small"
                fill="#028888"
                text-color="#fff"
                @change="labelChange($event)"
              >
                <el-radio-button label="空调" value="acpower" />
                <el-radio-button label="厨电" value="electpower" />
                <el-radio-button label="照明" value="lightpower" />
              </el-radio-group>
            </div>
          </div>
          <div class="ec_title1">
            <div class="ec_title_top">累计用电 <span>{{ Number(PowerHourData1[radio3+'sum']).toFixed(2) }}</span> kWh</div>
          </div>
          <div class="item_echart2">
            <EchartsItem2
              :data-time="dayItem1"
              :data-item="PowerHourData1"
              :data-type="radio3"
            ></EchartsItem2>
          </div>
        </div>
        <div class="center_R_ec">
          <div class="topTitle">用能结构<br /><span></span></div>

          <div class="item_echart2">
            <EchartsItem3 :define-data="PowerData"></EchartsItem3>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import EchartsItem2 from "./echarts/echarts2.vue";
import EchartsItem1 from "./echarts/echarts1.vue";
import EchartsItem3 from "./echarts/echarts3.vue";
import ModelsItem from "../modelItme.vue";
import { getPower, getPowerbyHour, getPowerbyDay } from "../api/index";
import dayjs from "dayjs";
import eventBus from "@/utils/event-bus.js";
const modelsData=ref()
const radio1 = ref("1");
const radio2 = ref("1");
const radio3 = ref("空调");
const PowerData = ref({});
const hourTime = dayjs(new Date()).format("YYYYMMDD");
const dayTime = dayjs(new Date()).format("YYYYMM");
const PowerHourData = ref({});
const PowerHourData1 = ref({});
const PowerDayData = ref({});
const dayItem = ref([]);
const dayItem1 = ref([]);
const twinpointData=ref({})
const getPowerData = () => {
  getPower().then((res) => {
    console.log(res);
    PowerData.value = res;
  });
};
eventBus.$on("twinpointData", (payload) => {
  console.log(payload);
  twinpointData.value=payload
});
//数据处理
const forPowerData = (data, name, type) => {
  var arr = {
        arrData: [],
        sum: 0,
      };
  for (let i = 0; i < data.length; i++) {
      arr.arrData.push(Number(data[i][name]).toFixed(2));
      arr.sum += Number(data[i][name]);
    }
  console.log(arr);
  return arr;
};
//数据处理
const forPowerData1 = (data) => {
  var arr = {
        '空调': [],
        '厨电': [],
        '照明': [],
        '空调sum': 0,
        '厨电sum': 0,
        '照明sum': 0,
      };
  for (let i = 0; i < data.length; i++) {
      arr["空调"].push(Number(data[i].acpower).toFixed(2));
      arr["厨电"].push(Number(data[i].electpower).toFixed(2));
      arr["照明"].push(Number(data[i].lightpower).toFixed(2));
      arr['空调sum'] += Number(data[i].acpower);
      arr['厨电sum'] += Number(data[i].electpower);
      arr['照明sum'] += Number(data[i].lightpower);
  }
  console.log(arr);
  return arr;
};
// 用能趋势时间选择
const trendChange = (item) => {
  console.log(item);
  if (item == 1) {
    dayItem.value = [
      "0",
      "1",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9",
      "10",
      "11",
      "12",
      "13",
      "14",
      "15",
      "16",
      "17",
      "18",
      "19",
      "20",
      "21",
      "22",
      "23",
    ];
    getPowerbyHourData(1);
  } else {
    dayItem.value = [
      "01",
      "02",
      "03",
      "04",
      "05",
      "06",
      "07",
      "08",
      "09",
      "10",
      "11",
      "12",
      "13",
      "14",
      "15",
      "16",
      "17",
      "18",
      "19",
      "20",
      "21",
      "22",
      "23",
      "24",
      "25",
      "26",
      "27",
      "28",
      "29",
      "30",
      "31",
    ];
    getPowerbyDayData(1);
  }
};
//分项能耗时间选择
const itemizeChange = (item) => {
  console.log(item);
  if (item == 1) {
    dayItem1.value = [
      "0",
      "1",
      "2",
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9",
      "10",
      "11",
      "12",
      "13",
      "14",
      "15",
      "16",
      "17",
      "18",
      "19",
      "20",
      "21",
      "22",
      "23",
    ];
    getPowerbyHourData(2);
  } else {
    dayItem1.value = [
      "01",
      "02",
      "03",
      "04",
      "05",
      "06",
      "07",
      "08",
      "09",
      "10",
      "11",
      "12",
      "13",
      "14",
      "15",
      "16",
      "17",
      "18",
      "19",
      "20",
      "21",
      "22",
      "23",
      "24",
      "25",
      "26",
      "27",
      "28",
      "29",
      "30",
      "31",
    ];
    getPowerbyDayData(2);
  }
};
//分项能耗类型选择
const labelChange = (item) => {
  console.log(item);
};
//日数据
const getPowerbyHourData = (type) => {
  getPowerbyHour(hourTime).then((res) => {
    if (res) {
      if (type == 1) {
        PowerHourData.value = forPowerData(res, "energytoday", type);
      } else {
        PowerHourData1.value = forPowerData1(res,);
      }
      console.log(res, PowerHourData.value);
    }
  });
};
//月数据
const getPowerbyDayData = (type) => {
  getPowerbyDay(dayTime).then((res) => {
    console.log(res);
    if (res) {
      if (type == 1) {
        PowerHourData.value = forPowerData(res, "energytoday", type);
      } else {
        PowerHourData1.value = forPowerData1(res,);
      }
      console.log(res, PowerHourData.value);
    }
  });
};
watch(() => modelsData.value, (val) => {
  console.log(val);
})
onMounted(() => {
  getPowerData();
  trendChange(1);
  itemizeChange(1);
});
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
.item_echart2 {
  height: calc(100% - 25px);
}
.item_body {
  width: 100%;
  height: 100%;
  .item_body_top {
    min-height: 15%;
    background: linear-gradient(
      to bottom,
      rgba(2, 136, 136, 0.2),
      rgba(2, 136, 136, 0.1),
      rgba(2, 136, 136, 0.1),
      rgba(2, 136, 136, 0)
    );
    width: 100%;
    border: 1px solid rgba(2, 136, 136, 0.05);
    border-radius: 12px;
    .body_top_items {
      width: 20%;
      height: 100%;
      display: inline-block;
      vertical-align: top;
      font-size: 1.4vh;
      text-align: center;
      position: relative;
      padding-top: 1.8%;
      .parent {
        position: absolute;
        width: 100%;
        span {
          font-size: 2.8vh;
          font-weight: 600;
          color: #333;
        }
      }
      .br {
        border-right: 1px solid #999;
      }
    }
  }
  .item_body_center {
    width: 100%;
    height: calc(85% - 15px);
    margin-top: 15px;
    .body_center_L {
      display: inline-block;
      vertical-align: top;
      width: 65%;
      height: 100%;

      .center_L_top {
        width: 100%;
        height: 75%;
        border: 1px solid rgba(2, 136, 136, 0.1);
        border-radius: 12px;
        padding: 5px;
        background: #fff;
      }
      .center_L_center {
        width: 100%;
        margin-top: 15px;
        height: calc(25% - 15px);
        border: 1px solid rgba(2, 136, 136, 0.1);
        border-radius: 12px;
        padding: 10px;
        background: #fff;
        .center_L_center_items {
          width: 100%;
          height: calc(100% - 15px);
          padding-top: 5px;
          padding-left: 15px;
          font-size: 1.6vh;
          .el-row {
            height: 33.33%;
            div {
              display: inline-block;
              .items_text {
                font-weight: 800;
                color: #111;
              }
            }
          }
        }
      }
    }
    .body_center_R {
      margin-left: 15px;
      display: inline-block;
      // vertical-align: top;
      width: calc(35% - 15px);
      height: 100%;
      .center_R_ec {
        width: 100%;
        height: 33.33%;
        border: 1px solid rgba(2, 136, 136, 0.1);
        border-radius: 12px;
        background: #fff;
        padding: 10px;
        position: relative;
        .ec_title {
          position: absolute;
          right: 15px;
          top: 10px;
          .ec_title_top {
            color: #666;
            font-size: 0.8rem;
            span {
              font-size: 1.2rem;
              font-weight: 600;
            }
          }
          .ec_title_bottom {
            font-size: 0.8rem;
            text-align: right;
            color: #666;
            span {
              font-size: 1.2rem;
              font-weight: 600;
            }
          }
        }
        .ec_title1 {
          position: absolute;
          right: 15px;
          top: 40px;
          color: #666;
          font-size: 0.8rem;
          span {
            font-size: 1.2rem;
            font-weight: 600;
          }
        }
      }
      .mb {
        height: calc(33.33% - 15px);
        margin-bottom: 15px;
      }
    }
  }
}
</style>
