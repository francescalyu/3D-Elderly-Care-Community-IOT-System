<!-- 环境 -->
<template>
  <div class="item_body">
    <div class="item_body_top">
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ OutDoorData.temperature }}</span> °C<br />室外温度
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ OutDoorData.humidity }}</span> %<br />室外湿度
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ OutDoorData.illuminance }}</span> lux<br />室外光照
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ airData.temp }}</span> °C<br />室内温度
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ airData.humidity }}</span> %<br />室内湿度
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span v-if="airData.tempLevel == '0'">正常</span>
          <span v-if="airData.tempLevel == '1'">高温</span>
          <span v-if="airData.tempLevel == '2'">低温</span>
          <span v-if="airData.tempLevel == '3'">潮湿</span>
          <span v-if="airData.tempLevel == '4'">干燥</span>
          <br />环境
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ airData.tvoc }}</span
          >{{ airData.tvocUnit }}<br />TVOC
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ airData.co2 }}</span
          >{{ airData.co2Unit }}<br />室内CO2
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ Number(airData.pm25).toFixed(2) }}</span> {{ airData.pm25Unit
          }}<br />室内PM2.5
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent br">
          <span>{{ Number(airData.pm10).toFixed(2) }}</span> {{ airData.pm10Unit
          }}<br />室内PM10
        </div>
      </div>
      <div class="body_top_items">
        <div class="parent">
          <span v-if="airData.level == '0'">正常</span>
          <span v-if="airData.level == '1'">轻度</span>
          <span v-if="airData.level == '2'">严重</span><br />空气质量
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
              <el-col
                :span="8"
                v-for="(item, index) in twinpointData.status"
                :key="index"
                style="margin-bottom: 0.8vh"
                ><div>{{ item.alias }} &nbsp;|</div>
                <div class="items_text" v-if="item.value == '1'">&nbsp;&nbsp;开</div>
                <div class="items_text" v-else-if="item.value == '0'">&nbsp;&nbsp;关</div>
                <div class="items_text" v-else>&nbsp;&nbsp;{{ item.value }}</div>
              </el-col>
              <!-- <el-col :span="6"
                ><div>设备位置 &nbsp;|</div>
                <div class="items_text">&nbsp;&nbsp;厨房</div></el-col
              > -->
            </el-row>
          </div>
        </div>
      </div>
      <div class="body_center_R">
        <div class="center_R_ec mb b60">
          <div style="height: 100%">
            <div class="topTitle">环境安全<br /><span></span></div>
            <div class="secure_items">
              <div class="secure_item b1 bm">
                <div class="item_L y1">
                  <img src="@/assets/img/dbsl.png" alt="" />
                </div>
                <div class="item_C">
                  <div class="fw">门禁</div>
                  <div>
                    {{ OutDoorData.status == "0" ? "关闭" : "开启" }}时间 |
                    <span class="fw">{{ OutDoorData.changeDate }}</span>
                  </div>
                </div>
                <div class="item_R">
                  <div class="cr fw" v-if="OutDoorData.status == '0'">关闭</div>
                  <div class="cg fw" v-if="OutDoorData.status == '1'">开启</div>
                </div>
              </div>
              <div class="secure_item bm b2">
                <div class="item_L y2">
                  <img src="@/assets/img/yxrl.png" alt="" />
                </div>
                <div class="item_C"><div class="cemre_text fw">烟雾检测</div></div>
                <div class="item_lists">
                  <div class="item_list br">
                    <div class="cg fw" v-if="airData.lpgLevel == '0'">正常</div>
                    <div class="cr fw" v-if="airData.lpgLevel == '1'">告警</div>
                    <div class="cr fw" v-if="airData.lpgLevel == '2'">严重</div>
                    <div>液化气</div>
                  </div>
                  <div class="item_list br">
                    <div class="cg fw" v-if="airData.coLevel == '0'">正常</div>
                    <div class="cr fw" v-if="airData.coLevel == '1'">告警</div>
                    <div class="cr fw" v-if="airData.coLevel == '2'">严重</div>
                    <div>一氧化碳</div>
                  </div>
                  <div class="item_list">
                    <div class="cg fw" v-if="airData.smokeLevel == '0'">正常</div>
                    <div class="cr fw" v-if="airData.smokeLevel == '1'">告警</div>
                    <div class="cr fw" v-if="airData.smokeLevel == '2'">严重</div>
                    <div>煤气</div>
                  </div>
                </div>
              </div>
              <div class="secure_item bm b3">
                <div class="item_L y3">
                  <img src="@/assets/img/zjrl.png" alt="" />
                </div>
                <div class="item_C">
                  <div class="cemre_text fw">摄像头</div>
                </div>
                <div class="cemre_btn"><div>查看监控</div></div>
                <div class="item_R">
                  <div class="fw">开启</div>
                </div>
              </div>
              <div class="secure_item bm b4">
                <div class="item_L y4">
                  <img src="@/assets/img/zbts.png" alt="" />
                </div>
                <div class="item_C">
                  <div class="fw">卫生间</div>
                  <div>占用时间 | <span class="fw">1min</span></div>
                </div>
                <div class="item_R">
                  <div class="fw cr">有人</div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="center_R_ec mb b40">
          <div>
            <div class="topTitle">环境控制<br /><span></span></div>
            <!-- <div class="topTitle_group">
              <el-radio-group v-model="radio2">
                <el-radio label="1" size="small">日</el-radio>
                <el-radio label="2" size="small">月</el-radio>
              </el-radio-group>
            </div> -->
            <div class="topTitle_group1">
              <el-radio-group
                v-model="radio3"
                size="small"
                fill="#028888"
                text-color="#fff"
                @change="changeType"
              >
                <el-radio-button label="照明" value="9" />
                <el-radio-button label="空调" value="1" />
              </el-radio-group>
            </div>
          </div>
          <div class="Switch_items">
            <div class="Switch_item" v-for="(item, i) in allData" :key="i">
              <span>{{ item.area }}</span>
              <el-switch
                :title="items.alias"
                size="small"
                v-for="(items, j) in allData[i].devonepointData"
                :key="items.id"
                active-color="#028888"
                v-model="items.value"
                class="ml-2 switch_item"
                :inactive-text="items.alias"
                active-value="1"
                inactive-value="0"
                @change="changeSwitch(items, item.id)"
              />
              <span class="item_text"
                >当日能耗 {{ Number(item.energyToday.value).toFixed(1) }}
                {{ item.energyToday.unit }}</span
              >
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import ModelsItem from "../modelItme.vue";
import {
  getAir,
  getOutDoor,
  getdevpoint,
  getPoint,
  getall,
  getdevonepoint,
  setSndcmd,
} from "../api/index";
import eventBus from "@/utils/event-bus.js";
const radio1 = ref("1");
const radio2 = ref("1");
const radio3 = ref("照明");
const value2 = ref(true);
const airData = ref({});
const OutDoorData = ref({});
const twinpointData = ref({});
const allData = ref([]);
const PointData = ref([]);
const ids = ref([]);
eventBus.$on("twinpointData", (payload) => {
  console.log(payload);
  twinpointData.value = payload;
});
const getOutDoorData = () => {
  getOutDoor().then((res) => {
    console.log(res);
    OutDoorData.value = res;
  });
};
const getAirData = () => {
  getAir().then((res) => {
    console.log(res);
    airData.value = res;
  });
};
const getPointData = (pid, id) => {
  getPoint(pid).then((res) => {
    for (let i = 0; i < res.length; i++) {
      if (res[i].ptUse) {
        if (res[i].ptUse == "2" || res[i].ptUse == "3" || res[i].id == "34") {
          getdevonepointData(id, res[i].id);
        }
      }
    }
    console.log(PointData.value);
  });
};
const getallData = (type) => {
  allData.value = [];
  getall().then((res) => {
    for (let i = 0; i < res.length; i++) {
      if (res[i].pid == type) {
        getPointData(type, res[i].id);
        res[i]["devonepointData"] = [];
        res[i]["energyToday"] = {};
        allData.value.push(res[i]);
      }
    }
  });
};
const getdevonepointData = (devId, pointId) => {
  getdevonepoint(devId, pointId).then((res) => {
    console.log(res, pointId);
    for (let i = 0; i < allData.value.length; i++) {
      if (allData.value[i].id == devId) {
        if (pointId != "34") {
          allData.value[i].devonepointData.push(res);
        } else {
          allData.value[i].energyToday = res;
        }
      }
    }
    console.log(allData.value);
  });
};
const changeType = (val) => {
  radio3.value = val;
  if (val == "照明") {
    getallData("9");
  } else {
    getallData("1");
  }
};
const changeSwitch = (val, devId) => {
  console.log(val.id, val.devId, val);
  var data = {
    id: val.id,
    devId: devId,
    pvalue: val.value,
  };
  setSndcmd(data).then((res) => {
    console.log(res);
    if (res == "OK") {
      if (radio3.value == "照明") {
        getallData("9");
      } else {
        getallData("1");
      }
    }
  });
};
onMounted(() => {
  getAirData();
  getOutDoorData();
  getallData("9");
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
      width: 9.09%;
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
        // height: 33.33%;
        border: 1px solid rgba(2, 136, 136, 0.1);
        border-radius: 12px;
        background: #fff;
        padding: 10px;
        position: relative;
        .secure_items {
          height: calc(100% - 25px);
          padding: 15px;
          .secure_item {
            height: 25%;
            border-radius: 12px;
            background: #fff;
            position: relative;
            .fw {
              font-weight: 800;
            }
            .cr {
              color: #fd4f4f !important;
            }
            .cg {
              color: rgba(164, 204, 69, 1) !important;
            }
            .item_L {
              //   height: 100%;
              min-height: 4vh;
              min-width: 4vw;
              position: absolute;
              top: calc(50% - 1.8vw);
              left: 15px;
              //   background: #3333;
              border-radius: 50%;
              img {
                width: 3.6vw;
                background-repeat: no-repeat;
                background-position: center;
              }
            }
            .item_C {
              position: absolute;
              left: 6vw;
              top: calc(50% - 1.4vw);
              font-size: 1.6vh;
              div {
                margin-bottom: 0.5vw;
              }
              .cemre_text {
                height: 100%;
                line-height: 6vh;
              }
            }
            .cemre_btn {
              position: absolute;
              right: 10vw;
              height: 100%;
              line-height: 8vh;
              cursor: pointer;
              div {
                height: 30px;
                line-height: 30px;
                text-align: center;
                width: 80px;
                border-radius: 5px;
                border: 1px solid #eee;
                position: relative;
                top: calc(50% - 15px);
                background: rgba(2, 136, 136, 0.1);
                font-weight: 800;
              }
            }
            .item_lists {
              position: absolute;
              right: 1vw;
              top: calc(50% - 2.5vh);
              width: 15vw;
              font-size: 1.6vh;
              .item_list {
                width: 33.33%;
                display: inline-block;
                text-align: center;
              }
              .br {
                border-right: 1px solid #999 !important;
              }
            }
            .item_R {
              position: absolute;
              right: 1vw;
              height: 100%;
              line-height: 8vh;
              div {
                font-size: 1.8vh;
                color: rgba(164, 204, 69, 1);
              }
            }
          }
          .bm {
            height: calc(25% - 10px);
            margin-bottom: 15px;
          }
          .b1 {
            border: 1px solid rgba(164, 204, 69, 0.1);
            box-shadow: 0 2px 2px 1px rgba(164, 204, 69, 0.1);
          }
          .b2 {
            border: 1px solid rgba(255, 210, 135, 0.1);
            box-shadow: 0 2px 2px 1px rgba(255, 210, 135, 0.1);
          }
          .b3 {
            border: 1px solid rgba(57, 160, 248, 0.1);
            box-shadow: 0 2px 2px 1px rgba(57, 160, 248, 0.1);
          }
          .b4 {
            border: 1px solid rgba(176, 141, 249, 0.1);
            box-shadow: 0 2px 2px 1px rgba(176, 141, 249, 0.1);
          }
        }
      }
      .mb {
        height: calc(33.33% - 15px);
        margin-bottom: 15px;
      }
      .b60 {
        height: 60%;
      }
      .b40 {
        height: calc(40% - 15px);
      }
      .Switch_items {
        width: calc(100%);
        height: calc(100% - 25px);
        padding: 2vh;
        overflow: auto;
        .Switch_item {
          height: calc(25% - 5px);
          position: relative;
          span {
            font-size: 1.6vh;
            font-weight: 800;
          }
          .item_text {
            position: absolute;
            right: 0;
          }
          .el-switch {
            // position: absolute;
            // left: 5vw;
            // top: -6px;
          }
          .switch_item {
            position: relative;
            top: 5px;
            display: inline-block;
            margin-left: 15px;
          }
        }
        ::v-deep(.el-switch--small .el-switch__label) {
          margin-bottom: 9px;
        }
      }
    }
  }
}
</style>
