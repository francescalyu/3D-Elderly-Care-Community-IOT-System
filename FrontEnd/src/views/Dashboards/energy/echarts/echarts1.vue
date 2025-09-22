<template>
  <div class="ec1_item" id="ecsmartDashboard1"></div>
</template>
<script lang="ts" setup>
import echarts from "@/utils/lib/echarts";
const props = defineProps({
  dataTime: {
    type: Array,
    default: [],
  },
  dataItem: {
    type: Object,
    default: {},
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
const color = "rgba(27, 179, 105, 0.4)";
const color1 = "rgba(27, 179, 105, 0.3)";
const color2 = "rgba(27, 179, 105, 0.2)";
const init = () => {
  console.log(props.dataTime, props.dataItem);
  var timeType = "";
  if (props.dataTime.length > 24) {
    timeType = "日";
  } else {
    timeType = "时";
  }
  const option9 = {
    grid: {
      left: baseSize.value * 5,
      right: baseSize.value * 5,
      bottom: "0",
      top: baseSize.value * 40,
      containLabel: true,
    },
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b}" + timeType + " : {c} kWh",
      // axisPointer: {
      //   type: "shadow",
      //   label: {
      //     show: true,
      //   },
      // },
    },
    legend: {
      data: ["用电量", "基准用电"],
      top: "5",
      textStyle: {
        color: "#666",
      },
      icon: "circle",
      itemWidth: 10,
      itemHeight: 10,
      itemGap: 10,
    },
    xAxis: {
      name: "",
      data: props.dataTime,
      axisLine: {
        show: true, //隐藏X轴轴线
        lineStyle: {
          color: "#666",
        },
      },
      axisTick: {
        show: false, //隐藏X轴刻度
      },
      axisLabel: {
        show: true,
        textStyle: {
          color: "#666", //X轴文字颜色
        },
      },
    },
    yAxis: [
      {
        type: "value",
        name: "kWh",
        // offset:20,
        offset: baseSize.value * -7,
        nameTextStyle: {
          color: "#666",
          fontSize: baseSize.value * 12,
          fontWeight: "bold",
        },
        position: "center",
        splitLine: {
          show: false,
        },
        axisTick: {
          show: false,
        },
        axisLine: {
          show: false,
          lineStyle: {
            color: "#6666",
          },
        },
        axisLabel: {
          show: true,
          textStyle: {
            color: "#666",
          },
        },
      },
      {
        type: "value",
        name: "",
        offset: baseSize.value * -7,
        nameTextStyle: {
          show: true,
          color: "#1BB369",
          fontSize: 16,
          fontWeight: "bold",
        },
        position: "left",
        splitLine: {
          show: false,
        },
        axisTick: {
          show: false,
        },
        axisLine: {
          show: false,
        },
        axisLabel: {
          show: true,
          textStyle: {
            color: "#666",
          },
        },
      },
    ],

    series: [
      {
        name: "用电量",
        type: "line",
        yAxisIndex: 1, //使用的 y 轴的 index，在单个图表实例中存在多个 y轴的时候有用
        smooth: false, //平滑曲线显示
        showAllSymbol: true, //显示所有图形。
        symbol: "emptyCircle", //标记的图形为实心圆
        symbolSize: 2, //标记的大小
        itemStyle: {
          //折线拐点标志的样式
          color: "rgba(164, 204, 69, 1)",
        },
        lineStyle: {
          color: "rgba(164, 204, 69, 1)",
          width: 1,
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            {
              offset: 1,
              color: "rgba(164, 204, 69, 0)",
            },
            {
              offset: 0.7,
              color: "rgba(164, 204, 69, 0.2)",
            },
            {
              offset: 0.3,
              color: "rgba(164, 204, 69, 0.3)",
            },
            {
              offset: 0,
              color: "rgba(164, 204, 69, 0.4)",
            },
          ]),
        },
        data: props.dataItem.arrData,
      },
      {
        name: "基准用电",
        type: "line",
        yAxisIndex: 1, //使用的 y 轴的 index，在单个图表实例中存在多个 y轴的时候有用
        smooth: false, //平滑曲线显示
        showAllSymbol: true, //显示所有图形。
        symbol: "none", //标记的图形为实心圆
        symbolSize: 4, //标记的大小
        itemStyle: {
          //折线拐点标志的样式
          color: "rgba(176, 141, 249, 1)",
        },
        lineStyle: {
          color: "rgba(176, 141, 249, 1)",
          width: 1,
          type: "dashed",
        },
        data: [5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5],
      },
    ],
  };
  if (props.dataType == "1") {
    option9.series[0].name = "用电量";
    option9.series[0].itemStyle.color = "rgba(253, 79, 79, 1)";
    option9.series[0].lineStyle.color = "rgba(253, 79, 79, 1)";
    option9.series[0].areaStyle = {
      color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
        {
          offset: 1,
          color: "rgba(253, 79, 79, 0)",
        },
        {
          offset: 0.7,
          color: "rgba(253, 79, 79, 0.2)",
        },
        {
          offset: 0.3,
          color: "rgba(253, 79, 79, 0.3)",
        },
        {
          offset: 0,
          color: "rgba(253, 79, 79, 0.4)",
        },
      ]),
    };
    option9.series[0].data = [
      5.2,
      1.8,
      2.8,
      2.5,
      1.9,
      5.8,
      2,
      4,
      6,
      3,
      1,
      2,
      8,
      3,
      4,
      7,
      3,
      5.2,
      1.8,
      2.8,
      2.5,
      1.9,
      5.8,
      2,
      2.5,
    ];
  }
  if (props.dataType == "2") {
    option9.series[0].name = "用水量";
    option9.series[0].itemStyle.color = "rgba(57, 160, 248, 1)";
    option9.series[0].lineStyle.color = "rgba(57, 160, 248, 1)";
    option9.series[0].areaStyle = {
      color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
        {
          offset: 1,
          color: "rgba(57, 160, 248, 0)",
        },
        {
          offset: 0.7,
          color: "rgba(57, 160, 248, 0.2)",
        },
        {
          offset: 0.3,
          color: "rgba(57, 160, 248, 0.3)",
        },
        {
          offset: 0,
          color: "rgba(57, 160, 248, 0.4)",
        },
      ]),
    };
    option9.series[0].data = [
      2.2,
      2.8,
      1.8,
      4.5,
      2.9,
      4.8,
      2,
      6,
      3,
      3,
      6,
      8,
      3,
      2,
      4,
      1,
      6,
      3,
      5,
      3,
      1,
      2,
      1,
      1,
      8,
    ];
  }
  if (props.dataType == "3") {
    option9.series[0].name = "用气量";
    option9.series[0].itemStyle.color = "rgba(176, 141, 249, 1)";
    option9.series[0].lineStyle.color = "rgba(176, 141, 249, 1)";
    option9.series[0].areaStyle = {
      color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
        {
          offset: 1,
          color: "rgba(164, 204, 69, 0)",
        },
        {
          offset: 0.7,
          color: "rgba(176, 141, 249, 0.2)",
        },
        {
          offset: 0.3,
          color: "rgba(176, 141, 249, 0.3)",
        },
        {
          offset: 0,
          color: "rgba(176, 141, 249, 0.4)",
        },
      ]),
    };
    option9.series[0].data = [
      6.2,
      4.8,
      4.8,
      7.5,
      4.9,
      7.8,
      3,
      5,
      4,
      9,
      2,
      1,
      6,
      8,
      3,
      7,
      2,
      6,
      2,
      6,
      5,
      5,
      3,
      6,
      5,
    ];
  }
  if (props.dataType == "4") {
    option9.series[0].name = "碳排量";
    option9.series[0].itemStyle.color = "rgba(164, 204, 69, 1)";
    option9.series[0].lineStyle.color = "rgba(164, 204, 69, 1)";
    option9.series[0].areaStyle = {
      color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
        {
          offset: 1,
          color: "rgba(164, 204, 69, 0)",
        },
        {
          offset: 0.7,
          color: "rgba(164, 204, 69, 0.2)",
        },
        {
          offset: 0.3,
          color: "rgba(164, 204, 69, 0.3)",
        },
        {
          offset: 0,
          color: "rgba(164, 204, 69, 0.4)",
        },
      ]),
    };
    option9.series[0].data = [
      1.2,
      3.8,
      3.8,
      2.5,
      7.9,
      5.8,
      4,
      2,
      1,
      9,
      4,
      3,
      7,
      2,
      3,
      4,
      5,
      6,
      3,
      2,
      7,
      8,
      9,
      4,
      4,
    ];
  }
  if (props.dateType == "1") {
  }
  if (props.dateType == "2") {
  }
  if (props.dateType == "3") {
  }
  if (props.dateType == "4") {
  }
  const myChart = echarts.init(document.getElementById("ecsmartDashboard1"));
  myChart.setOption(option9);
  // 使用 ResizeObserver 监听容器大小变化
  var resizeObserver = new ResizeObserver((entries) => {
    for (let entry of entries) {
      clacBaseFontSize();
      myChart.resize();
    }
  });
  resizeObserver.observe(document.getElementById("ecsmartDashboard1"));
};
watch(
  () => [props.dataTime],
  () => {
    setTimeout(() => {
      init();
    }, 500);
  }
);
watch(
  () => [props.dataItem],
  () => {
    setTimeout(() => {
      init();
    }, 500);
  }
);
onMounted(() => {
  // setTimeout(() => {
  clacBaseFontSize();
  init();
  // }, 500);
});
</script>
<style lang="scss" scoped>
.ec1_item {
  width: 100%;
  height: 100%;
}
</style>
