<template>
  <el-breadcrumb separator="/" class="bred">
    <el-breadcrumb-item v-for="item in tabs">{{item.meta.title}}</el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script setup lang="ts">
import { ref, Ref, watch } from "vue";
import { useRoute, RouteLocationMatched } from "vue-router";
//获取当前路由
const route = useRoute();
//定义面包屑导航数据
const tabs: Ref<RouteLocationMatched[]> = ref([]);
//监听到路由变化的操作
const getBredcrumb = () => {
  //获取菜单数据,并且存在title
  let matched = route.matched.filter((item) => item.meta && item.meta.title);
  console.log(matched);
  //判断是否是首页，不是，构造一个首页
  const first = matched[0];
  if (first.path !== "/dashboard") {
    matched = [{ path: "/dashboard", meta: { title: "首页" } } as any].concat(
      matched
    );
  }
  //设置数据到面包屑导航里面
  tabs.value = matched;
};
getBredcrumb()
//监听路由变化
watch(
  () => route.path,
  () => getBredcrumb()
);
</script>

<style scoped>
:deep(.el-breadcrumb__inner) {
  color: #fff !important;
}
:deep(.el-breadcrumb__inner a) {
  color: #fff !important;
}
:deep(.el-breadcrumb__item) {
  font-size: 14px;
}
.bred {
  margin-left: 20px;
}
</style>
