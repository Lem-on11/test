<template>
  <el-dialog
    :model-value="visible"
    :title="title"
    :width="width + 'px'"
    :before-close="onClose"
    :close-on-click-modal="false"
  >
    <div class="container" :style="{ height: height + 'px' }">
      <slot name="content"></slot>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="danger" @click="onClose">取消</el-button>
        <el-button type="primary" @click="onConfirm">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
//定义参数类型
interface DialogProps {
  title: string;
  visible: boolean;
  height: number;
  width: number;
}
//子组件接收父组件参数,设置默认值
const props = withDefaults(defineProps<DialogProps>(), {
  title: "标题",
  visible: false,
  height: 250,
  width: 630,
});
//注册事件
const emits = defineEmits(["onConfirm", "onClose"]);
//弹框确定
const onConfirm = () => {
  emits("onConfirm");
};
//取消
const onClose = () => {
  emits("onClose");
};
</script>
<style lang="scss" scope>
.container {
  overflow-x: initial;
  overflow-y: auto;
}
.el-dialog {
  border-top-left-radius: 7px !important;
  border-top-right-radius: 7px !important;
  .el-dialog__header {
    margin-right: 0px;
    border-top-left-radius: 7px !important;
    border-top-right-radius: 7px !important;
    background-color: #009688 !important;
    .el-dialog__title {
      color: #fff;
      font-size: 16px;
      font-weight: 600;
    }
  }
  .el-dialog__headerbtn {
    .el-dialog__close {
      color: #fff;
    }
  }
  .el-dialog__body {
    padding: 10px;
  }
  .el-dialog__footer {
    border-top: 1px solid #e8eaec !important;
    padding: 10px;
  }
}
</style>
