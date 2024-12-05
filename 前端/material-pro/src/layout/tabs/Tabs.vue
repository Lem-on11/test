<template>
  <el-tabs
    v-model="activeTab"
    type="card"
    class="demo-tabs"
    closable
    @tab-remove="removeTab"
    @tab-click="clickBtn"
  >
    <el-tab-pane
      v-for="item in tabList"
      :key="item.path"
      :label="item.title"
      :name="item.path"
    >
    </el-tab-pane>
  </el-tabs>
</template>

<script setup lang="ts">
import { Tab, tabStore } from "@/store/tabs";
import { computed, onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { TabsPaneContext } from "element-plus";
//获取store
const store = tabStore();
//当前路由
const route = useRoute();
const router = useRouter();
//获取选项卡数据
const tabList = computed(() => {
  return store.getTabs;
});
//当前激活的选项卡
const activeTab = ref("");
//设置激活的选项卡
const setActiveTab = () => {
  activeTab.value = route.path;
};
const removeTab = (targetName: string) => {
  //首页不能关闭
  if (targetName === "/dashboard") return;
  const tabs = tabList.value;
  let activeName = activeTab.value;
  if (activeName === targetName) {
    tabs.forEach((tab, index) => {
      if (tab.path === targetName) {
        const nextTab = tabs[index + 1] || tabs[index - 1];
        if (nextTab) {
          activeName = nextTab.path;
        }
      }
    });
  }
  activeTab.value = activeName;
  store.tabList = tabs.filter((tab) => tab.path !== targetName);
  //跳转路由
  router.push({ path: activeName });
};
//添加选项卡数据
const addTab = () => {
  //添加当前菜单到选项卡里面
  console.log(route);
  const { path, meta } = route;
  //放到选项卡数据里面
  const tab: Tab = {
    path: path,
    title: meta.title as string,
  };
  store.addTab(tab);
};

//监听路由的变化
watch(
  () => route.path,
  () => {
    //设置激活的选项卡
    setActiveTab();
    //添加选项卡数据
    addTab();
  }
);
//选项卡点击事件
const clickBtn = (tab: TabsPaneContext) => {
  console.log(tab);
  const { props } = tab;
  //跳转路由
  router.push({ path: props.name as string });
};

//解决刷新浏览器，选项卡数据丢失问题
// const befortRefresh = () => {
//   window.addEventListener("beforeunload", () => {
//     sessionStorage.setItem("tabsView", JSON.stringify(tabList.value));
//   });
//   let tabSesion = sessionStorage.getItem("tabsView");
//   if (tabSesion) {
//     let oldTabs = JSON.parse(tabSesion);
//     if (oldTabs.length > 0) {
//       store.tabList = oldTabs;
//     }
//   }
// };
onMounted(() => {
  // befortRefresh();
  setActiveTab();
  addTab();
});
</script>
<style scoped lang="scss">
:deep(.el-tabs__header) {
  margin: 0px;
}
:deep(.el-tabs__item) {
  height: 26px !important;
  line-height: 26px !important;
  text-align: center !important;
  border: 1px solid #d8dce5 !important;
  margin: 0px 3px !important;
  color: #495060;
  font-size: 12px !important;
  padding: 0xp 10px !important;
}
:deep(.el-tabs__nav) {
  border: none !important;
}
:deep(.is-active) {
  border-bottom: 1px solid transparent !important;
  border: 1px solid #42b983 !important;
  background-color: #42b983 !important;
  color: #fff !important;
}
:deep(.el-tabs__item:hover) {
  color: #495060 !important;
}
:deep(.is-active:hover) {
  color: #fff !important;
}
:deep(.el-tabs__nav-next){
  line-height: 26px !important;
}
:deep(.el-tabs__nav-prev){
  line-height: 26px !important;
}
</style>
