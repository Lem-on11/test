<template>
  <div class="header-container">
    <Collapse></Collapse>
    <BreadCrumb></BreadCrumb>
  </div>
  <LoginOut></LoginOut>
</template>

<script setup lang="ts">
import Collapse from "./Collapse.vue";
import BreadCrumb from "./BreadCrumb.vue";
import LoginOut from "./LoginOut.vue";
</script>

<style scoped>
.header-container {
  display: flex;
  align-items: center;
}
</style>
