<template>
  <el-container class="layout">
    <el-aside width="auto" class="asside">
      <menu-bar></menu-bar>
    </el-aside>
    <el-container>
      <el-header height="50px" class="header">
        <Header></Header>
      </el-header>
      <el-main class="main">
        <Tabs style="padding: 0px 20px"></Tabs>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import Header from "./header/Header.vue";
import MenuBar from "./menu/MenuBar.vue";
import Tabs from "./tabs/Tabs.vue";
</script>

<style lang="scss" scoped>
.layout {
  height: 100%;
  .asside {
    background-color: #304156;
  }
  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: #009688;
  }
  .main {
    padding-top: 1px;
    padding-left: 0px !important;
    padding-right: 0px !important;
  }
}
</style>
