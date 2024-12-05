<template>
  <el-main :style="{ height: mianHeight + 'px' }">
    <!-- 数据统计 -->
    <el-row
      :gutter="20"
      type="flex"
      justify="center"
      style="margin-bottom: 40px"
    >
      <el-col :span="6">
        <div class="show-header" style="background: rgb(45, 183, 245)">
          <div class="show-num">{{ totalParm.intoTotal }}</div>
          <div class="bottom-text">入库审核</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header" style="background: rgb(237, 64, 20)">
          <div class="show-num">{{ totalParm.sendTotal }}</div>
          <div class="bottom-text">发放审核</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header">
          <div class="show-num">{{ totalParm.categoryTotal }}</div>
          <div class="bottom-text">物资种类</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="show-header" style="background: rgb(255, 153, 0)">
          <div class="show-num">{{ totalParm.totalTotal }}</div>
          <div class="bottom-text">累计发放</div>
        </div>
      </el-col>
    </el-row>
    <div style="display: flex">
      <el-card style="flex: 1">
        <template #header>
          <div class="card-header">
            <span>物资分类</span>
          </div>
        </template>
        <div ref="myChart" :style="{ width: '400px', height: '250px' }"></div>
      </el-card>
      <el-card style="margin-left: 20px; flex: 1">
        <template #header>
          <div class="card-header">
            <span>物资预警</span>
          </div>
        </template>
        <div ref="myChart1" :style="{ width: '400px', height: '250px' }"></div>
      </el-card>
      <el-card style="margin-left: 20px; flex: 1">
        <template #header>
          <div class="card-header">
            <span>物资充足</span>
          </div>
        </template>
        <div ref="myChart2" :style="{ width: '400px', height: '250px' }"></div>
      </el-card>
    </div>
    <el-card class="box-card" style="margin-top: 30px">
      <div slot="header" class="clearfix">
        <span style="color: #000000; font-weight: 600; margin-bottom: 10px"
          >公告列表</span
        >
        <el-divider></el-divider>
      </div>
      <div v-for="(item, index) in noticeList.list" class="text item">
        <span style="font-weight: 600; font-size: 14px">{{ item.title }}</span
        ><span style="margin-left: 30px; font-size: 14px">{{
          item.contentText
        }}</span>
        <span style="margin-left: 30px; font-size: 14px">{{
          item.createTime
        }}</span>
        <el-divider></el-divider>
      </div>
    </el-card>
  </el-main>
</template>

<script setup lang="ts">
import {
  getTotalApi,
  moreInfoApi,
  lessInforApi,
  getTotalCategoryApi,
  getNoticeListApi,
} from "@/api/home";
import { onMounted, reactive, nextTick, ref } from "vue";
import useInstance from "@/hooks/useInstance";
const { global } = useInstance();
const myChart = ref<HTMLElement>();
const myChart1 = ref<HTMLElement>();
const myChart2 = ref<HTMLElement>();
const mianHeight = ref(0);
//柱状图
const charts1 = async () => {
  const echartInstance = global.$echarts.init(myChart.value);
  let option = reactive({
    xAxis: {
      type: "category",
      data: [],
      axisLabel: {
        //x轴文字的配置
        show: true,
        interval: 0, //使x轴文字显示全
      },
    },
    yAxis: {
      type: "value",
    },
    series: [
      {
        data: [],
        type: "bar",
      },
    ],
  });
  //动态获取数据
  let res = await getTotalCategoryApi();
  if (res && res.code == 200) {
    option.xAxis.data = res.data.names;
    option.series[0].data = res.data.values;
  }
  echartInstance.setOption(option);
};
//饼图
const charts2 = async () => {
  const myChart = global.$echarts.init(myChart1.value);
  let option = reactive({
    title: {
      subtext: "Fake Data",
      left: "center",
    },
    tooltip: {
      trigger: "item",
    },
    legend: {
      orient: "vertical",
      left: "left",
    },
    series: [
      {
        name: "Access From",
        type: "pie",
        radius: "50%",
        data: [],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: "rgba(0, 0, 0, 0.5)",
          },
        },
      },
    ],
  });
  let res = await lessInforApi();
  if (res && res.code == 200) {
    option.series[0].data = res.data;
  }
  myChart.setOption(option);
};
//环图
const charts3 = async () => {
  const myChart = global.$echarts.init(myChart2.value);
  let option = reactive({
    tooltip: {
      trigger: "item",
    },
    legend: {
      top: "5%",
      left: "center",
    },
    series: [
      {
        type: "pie",
        radius: ["40%", "70%"],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: "center",
        },
        emphasis: {
          label: {
            show: true,
            fontSize: "40",
            fontWeight: "bold",
          },
        },
        labelLine: {
          show: false,
        },
        data: [],
      },
    ],
  });
  let res = await moreInfoApi();
  if (res && res.code == 200) {
    option.series[0].data = res.data;
  }
  myChart.setOption(option);
};
//接收总数的参数
const totalParm = reactive({
  intoTotal: 0,
  sendTotal: 0,
  categoryTotal: 0,
  totalTotal: 0,
});
//获取总数
const getTotal = async () => {
  let res = await getTotalApi();
  if (res && res.code == 200) {
    totalParm.intoTotal = res.data.intoTotal;
    totalParm.sendTotal = res.data.sendTotal;
    totalParm.categoryTotal = res.data.categoryTotal;
    totalParm.totalTotal = res.data.totalTotal;
  }
};
//获取公告数据
const noticeList = reactive({
  list: [] as any[],
});
const getNoticeList = async () => {
  let res = await getNoticeListApi();
  if (res && res.code == 200) {
    noticeList.list = res.data;
  }
};
onMounted(() => {
  charts1();
  charts2();
  charts3();
  getTotal();
  getNoticeList();
  nextTick(() => {
    mianHeight.value = window.innerHeight - 90;
  });
});
</script>

<style lang="scss" scoped>
.bottom-text {
  bottom: 0;
  width: 100%;
  background: rgba(0, 0, 0, 0.1);
  height: 25px;
  line-height: 25px;
  text-align: center;
  position: absolute;
  font-weight: 600;
}
.show-header {
  background: #00c0ef;
  color: #fff;
  height: 80px;
  border-radius: 5px;
  position: relative;
}
.show-num {
  font-size: 38px;
  font-weight: 600;
  padding: 5px;
}
</style>
