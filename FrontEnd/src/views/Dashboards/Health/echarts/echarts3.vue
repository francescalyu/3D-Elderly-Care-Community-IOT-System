<template>
  <div class="ec1_item" id="ecHealth3"></div>
</template>
<script lang="ts" setup>
import echarts from "@/utils/lib/echarts";
const props = defineProps({
  dataItem: {
    type: Array,
    default: [],
  },
  dateItem: {
    type: Array,
    default: [],
  },
});
const baseSize = ref(0);
const clacBaseFontSize = () => {
  const clientwidth =
    window.innerWidth ||
    document.documentElement.clientWidth ||
    document.body.clientWidth;
  baseSize.value = clientwidth / 1920;
};
const init = () => {
  console.log(props.dateItem, props.dataItem);
  var yData = [],
    yData1 = [],
    yData2 = [],
    yData3 = [];
  const min = 2000; // 最小值的定义
  props.dataItem.map(function (a, b) {
    // xData.push(a.name);
    if (a === 0) {
      yData.push(Number(a) + min);
    } else {
      yData2.push(Number(a)+2000);
      yData.push(Number(a));
    }
    // yData.push((Math.random(0,1) * 100).toFixed(0));
  });
  props.dataItem.map(function (a, b) {
    // xData.push(a.name);
    if (a === 0) {
      yData1.push(Number(a) + min);
    } else {
      yData3.push(Number(a)+2000);
      yData1.push(Number(a));
    }
    // yData.push((Math.random(0,1) * 100).toFixed(0));
  });
  console.log(yData2)
  const option9 = {
    // backgroundColor:"#fff",
    // color: ["#3398DB"],
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow",
        // lineStyle: {
        //   opacity: 0,
        // },
      },
      // formatter: function (prams) {
      //   console.log(prams)
      //   if (prams[0].data === min) {
      //     return "合格率：0%";
      //   } else {
      //     return prams.name + prams[0].data + "%";
      //   }
      // },
      formatter: function (params) {
        var text =
          "<span style='color:#3398DB'>" + params[0].axisValueLabel + "</span></br>";
        // console.log(params);
        for (let i = 0; i < params.length; i++) {
          if (params[i].seriesName != "背景") {
            text += params[i].seriesName + ": " + params[i].value + " 步 </br>";
          }
        }
        return text;
      },
    },
    legend: {
      data: [],
      left: "right",
      top: "3%",
      show: false,
    },
    grid: {
      left: baseSize.value * 5,
      right: baseSize.value * 5,
      bottom: "0",
      top: baseSize.value * 40,
      containLabel: true,
    },
    xAxis: [
      {
        type: "category",
        // gridIndex: 0,
        data: props.dateItem,
        axisTick: {
          show: false, //隐藏X轴刻度
        },
        axisLine: {
          show: true, //隐藏X轴轴线
          lineStyle: {
            color: "#E6E6E6",
            width: 2,
          },
        },
        axisLabel: {
          show: true,
          textStyle: {
            color: "#999", //X轴文字颜色
            fontSize: baseSize.value * 10,
          },
        },
      },
      {
        type: "category",
        axisLine: {
          show: false,
        },
        axisTick: {
          show: false,
        },
        axisLabel: {
          show: false,
        },
        splitArea: {
          show: false,
        },
        splitLine: {
          show: false,
        },
        data: props.dateItem,
      },
    ],
    yAxis: [
      {
        name: "步",
        nameTextStyle: {
          color: "#999",
          fontSize: baseSize.value * 12,
          fontWeight: "bold",
        },
        type: "value",
        gridIndex: 0,
        splitLine: {
          show: false,
        },
        axisTick: {
          show: false,
        },
        // min: min,
        // max: 100,
        axisLine: {
          show: false,
          lineStyle: {
            color: "#999",
          },
        },
        axisLabel: {
          formatter: function (value) {
            // console.log(value);
            return value; // 确保返回的是字符串类型
          },
          textStyle: {
            color: "#aaa",
            fontSize: baseSize.value * 12,
          },
        },
      },
    ],
    series: [
      {
        name: "背景",
        type: "bar",
        xAxisIndex: 1,
        // barGap: "50%",
        zlevel: 1,
        itemStyle: {
          normal: {
            color: "#eee",
            boxShadow: "0px 1px 20px 0px rgba(58,90,235,0.22)",
            barBorderRadius: [30, 30, 0, 0],
          },
        },
        barWidth: baseSize.value * 14,
        data: yData2,
      },
      {
        name: "步数",
        type: "bar",
        barGap: "80%",
        itemStyle: {
          normal: {
            barBorderRadius: [30, 30, 30, 30],
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: "rgba(57, 160, 248, 1)",
              },
              {
                offset: 0.3,
                color: "rgba(57, 160, 248, .8)",
              },
              {
                offset: 0.7,
                color: "rgba(57, 160, 248, .4)",
              },
              {
                offset: 1,
                color: "rgba(57, 160, 248, .2)",
              },
            ]),
          },
        },
        barWidth: baseSize.value * 14,
        barCategoryGap: "20%",
        data: yData1,
        zlevel: 2,
      },
    ],
  };
  // console.log(props.dataType);
  // if (props.dataType == "1") {
  //   // option9.legend.data = ["驶入", "驶出"];
  //   option9.series[0].name = "用电量";
  //   // option9.series[1].name = "驶出";
  //   option9.series[0].data = [
  //     5.2,
  //     1.8,
  //     2.8,
  //     2.5,
  //     1.9,
  //     5.8,
  //     2,
  //     4,
  //     6,
  //     3,
  //     1,
  //     2,
  //     8,
  //     3,
  //     4,
  //     7,
  //     3,
  //     5.2,
  //     1.8,
  //     2.8,
  //     2.5,
  //     1.9,
  //     5.8,
  //     2,
  //     2.5,
  //   ];
  // } else {
  //   // option9.legend.data = ["进入", "离开"];
  //   option9.series[0].name = "用电量";
  //   option9.series[0].data = [
  //     2.2,
  //     2.8,
  //     1.8,
  //     4.5,
  //     2.9,
  //     4.8,
  //     2,
  //     6,
  //     3,
  //     3,
  //     6,
  //     8,
  //     3,
  //     2,
  //     4,
  //     1,
  //     6,
  //     3,
  //     5,
  //     3,
  //     1,
  //     2,
  //     1,
  //     1,
  //     8,
  //   ];
  // }
  const myChart = echarts.init(document.getElementById("ecHealth3"));
  myChart.setOption(option9);
  // 使用 ResizeObserver 监听容器大小变化
  var resizeObserver = new ResizeObserver((entries) => {
    for (let entry of entries) {
      clacBaseFontSize();
      myChart.resize();
    }
  });
  resizeObserver.observe(document.getElementById("ecHealth3"));
};
watch(
  () => [props.dataItem],
  () => {
    setTimeout(() => {
      init();
    }, 500);
  }
);
watch(
  () => [props.dateItem],
  () => {
    setTimeout(() => {
      init();
    }, 500);
  }
);
onMounted(() => {
  setTimeout(() => {
    clacBaseFontSize();
    init();
  }, 500);
});
</script>
<style lang="scss" scoped>
.ec1_item {
  width: 100%;
  height: 100%;
}
</style>
