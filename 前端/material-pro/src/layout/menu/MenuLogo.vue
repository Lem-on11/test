<template>
  <div class="logo">
    <img :src="MenuLogo" />
    <span v-if="show" class="logo-title">{{ title }}</span>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from "vue";
//获取logo
// import MenuLogo from "@/assets/MenuLogo.png";
const MenuLogo = ref('https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png')
const title = ref("应急物资管理系统");

//接收父组件传递的参数
const props = defineProps({
  isCollapsed: {
    require: true,
    type: Boolean,
  },
});

//解决收缩卡顿的问题:延时
const show = ref(true);
watch(
  () => props.isCollapsed,
  (collapsed: boolean) => {
    if (!collapsed) {
      setTimeout(() => {
        show.value = !collapsed;
      }, 300);
    } else {
      show.value = !collapsed;
    }
  }
);
</script>

<style scoped lang="scss">
.logo {
  display: flex;
  width: 100%;
  height: 50px;
  line-height: 50px;
  cursor: pointer;
  text-align: center;
  align-items: center;
  background: #001528;
  img {
    width: 36px;
    height: 36px;
    margin-left: 20px;
    margin-right: 12px;
  }
  .logo-title {
    color: #fff;
    font-size: 22px;
    font-weight: 600;
    font-family: FangSong;
  }
}
</style>
