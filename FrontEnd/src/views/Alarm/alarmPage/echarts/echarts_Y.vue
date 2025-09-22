<template>
  <div class="ec1_item" id="echarts_Y"></div>
</template>
<script lang="ts" setup>
import echarts from "@/utils/lib/echarts";
const getfpksrs = ref([1265]);
const getfpkszb1 = ref([0.01]);
const baseSize = ref(0);
const clacBaseFontSize = () => {
  const clientwidth =
    window.innerWidth ||
    document.documentElement.clientWidth ||
    document.body.clientWidth;
  baseSize.value = clientwidth / 1920;
};
const init = () => {
  const option = {
    backgroundColor: "",
    title: [
      {
        text: "",
        x: "center",
        top: "40%",
        textStyle: {
          fontSize: baseSize.value *16,
          color: "#ffd287",
          fontFamily: "DINAlternate-Bold, DINAlternate",
          foontWeight: "600",
        },
      },
    ],
    tooltip: {
      show: false,
      formatter: function (params) {
        return (
          '<span style="color: #fff;">非贫困生人数：' + getfpksrs.value + "人</span>"
        );
      },
    },
    angleAxis: {
      max: 100,
      clockwise: true, // 逆时针
      // 隐藏刻度线
      show: false,
    },
    radiusAxis: {
      type: "category",
      show: true,
      axisLabel: {
        show: false,
      },
      axisLine: {
        show: false,
      },
      axisTick: {
        show: false,
      },
    },
    polar: {
      center: ["50%", "53%"],
      radius: "150%", //图形大小
    },
    series: [
      {
        stack: "测试",
        type: "bar",
        data: ["72"],
        showBackground: true,
        backgroundStyle: {
          color: "#EFF2F5",
          borderColor: "#EFF2F5",
          borderWidth: 10,
        },
        coordinateSystem: "polar",
        roundCap: true,
        barWidth: 4,
        silent: true,
        itemStyle: {
          normal: {
            opacity: 1,
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: "#fff",
              },
              {
                offset: 1,
                color: "#ffd287",
              },
            ]),
            borderColor: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: "#fff",
              },
              {
                offset: 1,
                color: "#ffd287",
              },
            ]),
            borderWidth: 2,
          },
        },
      },
      {
        stack: "测试",
        type: "bar",
        data: getfpkszb1.value,
        showBackground: false,
        // backgroundStyle: {
        //     color: "#EFF2F5",
        //     shadowColor: "rgba(0, 0, 0, 0.2)",
        //     shadowBlur: 10,
        //     shadowOffsetY: 2,
        // },
        coordinateSystem: "polar",
        roundCap: true,
        barWidth: 3,
        itemStyle: {
          color: " ",
          borderColor: " ",
          borderWidth: 2,
        },
      },
    ],
  };
  const myChart = echarts.init(document.getElementById("echarts_Y"));
  myChart.setOption(option);
  // 使用 ResizeObserver 监听容器大小变化
  var resizeObserver = new ResizeObserver((entries) => {
    for (let entry of entries) {
      clacBaseFontSize();
      myChart.resize();
    }
  });
  resizeObserver.observe(document.getElementById("echarts_Y"));
};
onMounted(() => {
  clacBaseFontSize();
  init();
});
</script>
<style lang="scss" scoped>
.ec1_item {
  width: 100%;
  height: 100%;
}
</style>
