<script setup lang="ts">
import { computed } from "vue";
import { storeToRefs } from "pinia";
import { testStore } from "@/store/test/index";
//获取store
const store = testStore();
//获取count,解构数据失去响应性
// const { count } = storeToRefs(store);
const count = computed(()=>{
  return store.getCount
})
const addBtn = () => {
  //方式一
  // ++store.count;
  //方式二
  // store.setCount(++store.count)
  //方式三
  // store.$patch({
  //   count:++store.count
  // })
  //方式四
  store.$patch((state)=>{
    state.count = ++state.count
  })
};
defineProps<{ msg: string }>();
</script>

<template>
  <el-row class="mb-4">
    <el-button>Default</el-button>
    <el-button type="primary">Primary</el-button>
    <el-button type="success">Success</el-button>
    <el-button type="info">Info</el-button>
    <el-button type="warning">Warning</el-button>
    <el-button type="danger">Danger</el-button>
    <el-button>中文</el-button>
  </el-row>
  <Edit style="width: 1em; height: 1em; margin-right: 8px" />
  <Share style="width: 1em; height: 1em; margin-right: 8px" />
  <Delete style="width: 1em; height: 1em; margin-right: 8px" />
  <Search style="width: 1em; height: 1em; margin-right: 8px" />
  <h1>{{ msg }}</h1>
  1111

  <p>pinia的使用</p>
  <el-button type="primary" @click="addBtn">新增</el-button>
  <div>{{ count }}</div>
</template>

<style scoped>
a {
  color: #42b983;
}

label {
  margin: 0 0.5em;
  font-weight: bold;
}

code {
  background-color: #eee;
  padding: 2px 4px;
  border-radius: 4px;
  color: #304455;
}
</style>
