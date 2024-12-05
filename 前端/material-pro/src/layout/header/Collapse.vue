<template>
  <el-icon @click="setCollapse" class="icons" color="#FFF" :size="24">
    <!-- 动态组件的方式显示图标 -->
    <component :is="status ? Fold : Expand"></component>
  </el-icon>
</template>

<script setup lang="ts">
import { Fold, Expand } from "@element-plus/icons-vue";
import { collapseStore } from "@/store/collapse/index";
import { computed } from "vue";
//获取store
const store = collapseStore();
//获取collapse
const status = computed(()=>{
    return !store.getCollapse
})
//切换图标
const setCollapse =()=>{
    store.setCollapse(!store.collapse)
}
</script>

<style scoped>
.icons{
    cursor: pointer;
}
</style>
