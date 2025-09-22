<template>
  <div class="ec1_item" id="ecsmartDashboard3"></div>
</template>
<script lang="ts" setup>
import echarts from "@/utils/lib/echarts";
const props = defineProps({
  defineData: {
    type: Object,
    default: {},
  },
});
const baseSize = ref(0);
const totalNum = ref(0);
const clacBaseFontSize = () => {
  const clientwidth =
    window.innerWidth ||
    document.documentElement.clientWidth ||
    document.body.clientWidth;
  baseSize.value = clientwidth / 1920;
};
const init = () => {
  console.log(props.defineData);
  const detailData = [
    { name: "空调能耗", value:Number(props.defineData.acPower).toFixed(2)},
    { name: "厨电能耗", value: Number(props.defineData.electPower).toFixed(2)},
    { name: "照明能耗", value:Number(props.defineData.lightPower).toFixed(2)},
  ];
  let sum = 0;
    detailData.forEach((item) => {
    //   console.log(item);
      sum += Number(item.value);
    });
    totalNum.value = sum;
  const option = {
    backgroundColor: "#fff",
    title: {
      text:Number(totalNum.value).toFixed(2) +' kWh',
      subtext: "用电量",
      top: "40%",
      left: "29%",
      textAlign: "center",
      textStyle: {
        color: "#333",
        fontSize: baseSize.value*16,
      },
      subtextStyle: {
        color: "#666",
        fontSize: baseSize.value*14,
        fontWeight:'bold',
      },
    },
    legend: {
      orient: "vertical",
      top: "center",
      right: "5%",
      itemHeight: "6",
      itemWidth: baseSize.value *14,
      itemGap: baseSize.value*20,
      data: ["空调能耗", "厨电能耗", "照明能耗"],
      textStyle: {
        color: "#666",
        fontSize: baseSize.value*14,
        foontWeight: "bold",
      },
      formatter(name) {
        let total = 0;
        let target;
        if (detailData) {
          for (let i = 0; i < detailData.length; i++) {
            total += Number(detailData[i].value);
            if (detailData[i].name === name) target = Number(detailData[i].value);
          }
          // console.log(target, totalNum.value,name);
        }
        if (!target && target !== 0) return "暂无数据";
        else if (total === 0)
          return `${name.length > 5 ? name.substr(0, 5) + "..." : name} 0%`;
        else
          return `${"  " + (name.length > 5 ? name.substr(0, 5) + "..." : name) + "  "}${
            " " + target + "kWh   " + ((target / totalNum.value) * 100).toFixed(0) + "%"
          }`;
      },
    },
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b} : {c} ({d}%)",
    },
    series: [
      {
        name: "用能结构",
        type: "pie",
        radius: ["50%", "95%"],
        center: ["30%", "50%"],
        roseType: "radius",
        label: {
          show: false,
        },
        labelLine: {
          show: false,
        },
        data: [
          {
            value: detailData[0].value,
            name: "空调能耗",
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 1,
                  color: "rgba(249, 145, 2, 0.2)",
                },
                {
                  offset: 0.7,
                  color: "rgba(249, 145, 2, 0.4)",
                },
                {
                  offset: 0.3,
                  color: "rgba(249, 145, 2, 0.8)",
                },
                {
                  offset: 0,
                  color: "rgba(249, 145, 2, 1)",
                },
              ]),
              borderColor: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 1,
                  color: "rgba(249, 145, 2, 0.2)",
                },
                {
                  offset: 0.7,
                  color: "rgba(249, 145, 2, 0.4)",
                },
                {
                  offset: 0.3,
                  color: "rgba(249, 145, 2, 0.8)",
                },
                {
                  offset: 0,
                  color: "rgba(249, 145, 2, 1)",
                },
              ]),
              borderWidth: 1,
            },
          },
          {
            value: detailData[1].value,
            name: "厨电能耗",
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 1,
                  color: "rgba(164, 204, 69, 0.2)",
                },
                {
                  offset: 0.7,
                  color: "rgba(164, 204, 69, 0.4)",
                },
                {
                  offset: 0.3,
                  color: "rgba(164, 204, 69, 0.8)",
                },
                {
                  offset: 0,
                  color: "rgba(164, 204, 69, 1)",
                },
              ]),
              borderColor: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 1,
                  color: "rgba(164, 204, 69, 0.2)",
                },
                {
                  offset: 0.7,
                  color: "rgba(164, 204, 69, 0.4)",
                },
                {
                  offset: 0.3,
                  color: "rgba(164, 204, 69, 0.8)",
                },
                {
                  offset: 0,
                  color: "rgba(164, 204, 69, 1)",
                },
              ]),
              borderWidth: 1,
            },
          },
          {
            value: detailData[2].value,
            name: "照明能耗",
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 1,
                  color: "rgba(176, 141, 249, 0.2)",
                },
                {
                  offset: 0.7,
                  color: "rgba(176, 141, 249, 0.4)",
                },
                {
                  offset: 0.3,
                  color: "rgba(176, 141, 249, 0.8)",
                },
                {
                  offset: 0,
                  color: "rgba(176, 141, 249, 1)",
                },
              ]),
              borderColor: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 1,
                  color: "rgba(176, 141, 249, 0.2)",
                },
                {
                  offset: 0.7,
                  color: "rgba(176, 141, 249, 0.4)",
                },
                {
                  offset: 0.3,
                  color: "rgba(176, 141, 249, 0.8)",
                },
                {
                  offset: 0,
                  color: "rgba(176, 141, 249, 1)",
                },
              ]),
              borderWidth: 1,
            },
          },
          // {
          //   value: 5,
          //   name: "rose5",
          //   itemStyle: {
          //     color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          //       {
          //         offset: 1,
          //         color: "rgba(15,197,243, 0)",
          //       },
          //       {
          //         offset: 0.7,
          //         color: "rgba(15,197,243, 0.4)",
          //       },
          //       {
          //         offset: 0.3,
          //         color: "rgba(15,197,243, 0.8)",
          //       },
          //       {
          //         offset: 0,
          //         color: "rgba(15,197,243, 1)",
          //       },
          //     ]),
          //     borderColor: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          //       {
          //         offset: 1,
          //         color: "rgba(15,197,243, 0)",
          //       },
          //       {
          //         offset: 0.7,
          //         color: "rgba(15,197,243, 0.4)",
          //       },
          //       {
          //         offset: 0.3,
          //         color: "rgba(15,197,243, 0.8)",
          //       },
          //       {
          //         offset: 0,
          //         color: "rgba(15,197,243, 1)",
          //       },
          //     ]),
          //     borderWidth: 1,
          //   },
          // },
        ],
      },
    ],
  };
  const myChart = echarts.init(document.getElementById("ecsmartDashboard3"));
  myChart.setOption(option);
  // 使用 ResizeObserver 监听容器大小变化
  var resizeObserver = new ResizeObserver((entries) => {
    for (let entry of entries) {
      clacBaseFontSize();
      myChart.resize();
    }
  });
  resizeObserver.observe(document.getElementById("ecsmartDashboard3"));
};
watch(
  () => [props.defineData],
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
