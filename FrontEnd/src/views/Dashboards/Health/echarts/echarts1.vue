<template>
  <div class="ec1_item" id="ecHealth1"></div>
</template>
<script lang="ts" setup>
import echarts from "@/utils/lib/echarts";
const props = defineProps({
  dataItem: {
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
const datas = ref([0, 0]);
const color = "rgba(27, 179, 105, 0.4)";
const color1 = "rgba(27, 179, 105, 0.3)";
const color2 = "rgba(27, 179, 105, 0.2)";
//日
const deta = [
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
//周
const deta1 = ["周一", "周二", "周三", "周四", "周五", "周六", "周日"];
//月
const deta2 = [
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
//年
const deta3 = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"];
const init = () => {
  var sum = 0;
  if (props.dataItem.length) {
    for (let i = 0; i < props.dataItem.length; i++) {
      sum += Number(props.dataItem[i]);
    }
    sum = (sum / props.dataItem.length).toFixed(0);
    console.log(sum);
  }

  const option9 = {
    grid: {
      left: baseSize.value * 5,
      right: baseSize.value * 25,
      bottom: "0",
      top: baseSize.value * 40,
      containLabel: true,
    },
    // tooltip: {
    //   trigger: "axis",
    //   axisPointer: {
    //     type: "shadow",
    //     label: {
    //       show: true,
    //     },
    //   },
    //   formatter: function (val) {

    //     datas.value[0]=val[0].value
    //     // datas.value[1]=val[1].value
    //     console.log(val)
    //     for(let i=0;i<option9.legend.data.length;i++){
    //       option9.legend.formatter(val[i].seriesName);
    //     }
    //   }
    // },
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow",
        label: {
          show: true,
        },
      },
    },
    legend: {
      data: ["血压"],
      left: "right",
      textStyle: {
        color: "#999",
      },
      // icon: "circle",
      itemWidth: 10,
      itemHeight: 10,
      itemGap: 10,
      formatter: function (val) {
        datas.value = [110, 75];
        // console.log("val: ", val, datas.value);
        if (val == "血压") {
          return "平均" + val + " " + (sum || 0) + "mmHg   Max 120  Min 98    ";
        } else {
          return val + " " + datas.value[1] + "mmHg   Max 85   Min 65";
        }
      },
    },
    xAxis: {
      name: "",
      data: [
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
      ],
      axisLine: {
        show: true, //隐藏X轴轴线
        lineStyle: {
          color: "#999",
        },
      },
      axisTick: {
        show: false, //隐藏X轴刻度
      },
      axisLabel: {
        show: true,
        textStyle: {
          color: "#999", //X轴文字颜色
        },
      },
    },
    yAxis: [
      {
        type: "value",
        name: "血压",
        // offset:20,
        offset: baseSize.value * -7,
        nameTextStyle: {
          color: "#999",
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
            color: "#999",
          },
        },
        axisLabel: {
          show: true,
          textStyle: {
            color: "#999",
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
            color: "#999",
          },
        },
      },
    ],

    series: [
      {
        name: "血压",
        type: "line",
        yAxisIndex: 1, //使用的 y 轴的 index，在单个图表实例中存在多个 y轴的时候有用
        smooth: false, //平滑曲线显示
        showAllSymbol: true, //显示所有图形。
        symbol: "emptyCircle", //标记的图形为实心圆
        symbolSize: 2, //标记的大小
        itemStyle: {
          //折线拐点标志的样式
          color: "rgba(249, 145, 2, 1)",
        },
        lineStyle: {
          color: "rgba(249, 145, 2, 1)",
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
              color: "rgba(249, 145, 2, 0.2)",
            },
            {
              offset: 0.3,
              color: "rgba(249, 145, 2, 0.3)",
            },
            {
              offset: 0,
              color: "rgba(249, 145, 2, 0.4)",
            },
          ]),
        },
        data: props.dataItem,
      },
      // {
      //   name: "舒张压",
      //   type: "line",
      //   yAxisIndex: 1, //使用的 y 轴的 index，在单个图表实例中存在多个 y轴的时候有用
      //   smooth: false, //平滑曲线显示
      //   showAllSymbol: true, //显示所有图形。
      //   symbol: "emptyCircle", //标记的图形为实心圆
      //   symbolSize: 2, //标记的大小
      //   itemStyle: {
      //     //折线拐点标志的样式
      //     color: "rgba(176, 141, 249, 1)",
      //   },
      //   lineStyle: {
      //     color: "rgba(176, 141, 249, 1)",
      //     width: 1,
      //   },
      //   areaStyle: {
      //     color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
      //       {
      //         offset: 1,
      //         color: "rgba(164, 204, 69, 0)",
      //       },
      //       {
      //         offset: 0.7,
      //         color: "rgba(176, 141, 249, 0.2)",
      //       },
      //       {
      //         offset: 0.3,
      //         color: "rgba(176, 141, 249, 0.3)",
      //       },
      //       {
      //         offset: 0,
      //         color: "rgba(176, 141, 249, 0.4)",
      //       },
      //     ]),
      //   },
      //   data: [
      //     1.2,
      //     3.8,
      //     5.8,
      //     8.5,
      //     2.9,
      //     6.8,
      //     3,
      //     2,
      //     8.8,
      //     5.8,
      //     9.5,
      //     2.9,
      //     5.8,
      //     2,
      //     9,
      //     1,
      //     5,
      //     2,
      //     9,
      //     4,
      //     2,
      //     3,
      //     9,
      //     2,
      //   ],
      // },
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
  const myChart = echarts.init(document.getElementById("ecHealth1"));
  myChart.setOption(option9);
  // 使用 ResizeObserver 监听容器大小变化
  var resizeObserver = new ResizeObserver((entries) => {
    for (let entry of entries) {
      clacBaseFontSize();
      myChart.resize();
    }
  });
  resizeObserver.observe(document.getElementById("ecHealth1"));
};
watch(
  () => [props.dataItem],
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
