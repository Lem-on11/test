<template>
  <el-main>
    <!--搜索栏 -->
    <el-form :model="listParm" :inline="true" size="default">
      <el-form-item>
        <el-input
          v-model="listParm.province"
          placeholder="请输入省份"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="listParm.name" placeholder="请输入联系人"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="listParm.status"
          placeholder="请选择审核状态"
          size="default"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="listParm.type"
          placeholder="请选择入库类型"
          size="default"
        >
          <el-option
            v-for="item in optionsType"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button :icon="Search" @click="searchBtn">搜索</el-button>
        <el-button type="danger" plain :icon="Close" @click="resetBtn"
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :height="tableHeigth" :data="tableList.list" border stripe>
      <!-- 详细信息 给 el-table-column 加上 type="expand" -->
      <el-table-column type="expand">
        <template #default="scope">
          <el-table :data="scope.row.children" border stripe>
            <el-table-column
              prop="image"
              width="90"
              label="物资图片"
              align="center"
            >
              <template #default="scope">
                <el-image
                  style="width: 60px; height: 60px; border-radius: 50%"
                  :src="scope.row.image"
                ></el-image>
              </template>
            </el-table-column>
            <el-table-column prop="infoName" label="物资名称"></el-table-column>
            <el-table-column prop="specs" label="规格"></el-table-column>
            <el-table-column prop="unit" label="单位"></el-table-column>
            <el-table-column prop="num" label="数量"></el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column prop="province" label="省份"></el-table-column>
      <el-table-column prop="detailSource" label="详细来源"></el-table-column>
      <el-table-column prop="name" label="联系人"></el-table-column>
      <el-table-column prop="phone" label="联系电话"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="type" label="类型" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.type == '1'" size="default">捐赠</el-tag>
          <el-tag v-if="scope.row.type == '2'" type="success" size="default"
            >下拨</el-tag
          >
          <el-tag v-if="scope.row.type == '3'" type="warning" size="default"
            >采购</el-tag
          >
          <el-tag v-if="scope.row.type == '4'" type="danger" size="default"
            >借用</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="status" label="审核状态" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.status == '0'" size="default">未审核</el-tag>
          <el-tag v-if="scope.row.status == '1'" type="success" size="default"
            >审核通过</el-tag
          >
          <el-tag v-if="scope.row.status == '2'" type="danger" size="default"
            >拒绝入库</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column
        label="操作"
        v-if="global.$hasPerm(['sys:intoDetail:into', 'sys:intoDetail:reject'])"
        align="center"
        width="360"
      >
        <template #default="scope">
          <el-button
            :icon="Edit"
            v-if="global.$hasPerm(['sys:intoDetail:into'])"
            type="primary"
            size="default"
            @click="applyBtn(scope.row)"
            >同意入库</el-button
          >
          <el-button
            :icon="Edit"
            v-if="global.$hasPerm(['sys:intoDetail:reject'])"
            type="danger"
            size="default"
            @click="rejectBtn(scope.row)"
            >拒绝入库</el-button
          >
          <el-button
            :icon="Printer"
            v-if="global.$hasPerm(['sys:intoDetail:print'])"
            type="success"
            size="default"
            @click="printBtn(scope.row)"
            >打印</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="listParm.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="listParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="listParm.total"
      background
    >
    </el-pagination>
    <!-- 打印 -->
    <el-dialog
      :model-value="dialog.visible"
      :title="dialog.title"
      :width="dialog.width + 'px'"
      :before-close="onClose"
      :close-on-click-modal="false"
    >
      <div
        id="printMe"
        class="container"
        :style="{ height: dialog.height + 'px' }"
      >
        <div class="print-title">
          {{ printTitle }}
        </div>
        <el-divider content-position="left" style="margin-top: 14px"
          >基础信息</el-divider
        >
        <el-form style="margin-top: 20px; padding: 0px 50px">
          <el-row>
            <el-col :span="12" :offset="0">
              <el-form-item label="入库单号:" size="default">
                {{ print.intoId }}
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="入库时间:" size="default">
                {{ print.createTime }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12" :offset="0">
              <el-form-item label="省份:" size="default">
                {{ print.province }}
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item class="title" label="详细来源:" size="default">
                {{ print.detailSource }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12" :offset="0">
              <el-form-item label="联系人:" size="default">
                {{ print.name }}
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="联系电话:" size="default">
                {{ print.phone }}
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <el-divider
          content-position="left"
          style="margin-top: 14px; font-weight: 600"
          >物资信息</el-divider
        >
        <el-table style="width: 99.6%" :data="print.children">
          <el-table-column prop="image" label="物资图片" align="center">
            <template #default="scope">
              <el-image
                style="width: 50px; height: 50px; border-radius: 50%"
                :src="scope.row.image"
              ></el-image>
            </template>
          </el-table-column>
          <el-table-column prop="infoName" label="物资名称"></el-table-column>
          <el-table-column prop="specs" label="规格"></el-table-column>
          <el-table-column prop="unit" label="单位"></el-table-column>
          <el-table-column prop="num" label="数量"></el-table-column>
        </el-table>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="danger" @click="onClose">取消</el-button>
          <el-button v-print="printObj" type="primary" @click="onConfirm"
            >打印</el-button
          >
        </span>
      </template>
    </el-dialog>
  </el-main>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import useDialog from "@/hooks/useDialog";
import { Search, Edit, Printer, Close, Delete } from "@element-plus/icons-vue";
import useDetailTable from "@/composables/into/useDetailTable";
//弹框属性
const { dialog, onClose, onConfirm, onShow } = useDialog();
const options = [
  {
    value: "0",
    label: "未审核",
  },
  {
    value: "1",
    label: "已审核",
  },
  {
    value: "2",
    label: "审核失败",
  },
];
const optionsType = [
  {
    value: "1",
    label: "捐赠",
  },
  {
    value: "2",
    label: "下拨",
  },
  {
    value: "3",
    label: "采购",
  },
  {
    value: "4",
    label: "借用",
  },
];
//表格
const {
  listParm,
  tableHeigth,
  resetBtn,
  searchBtn,
  tableList,
  sizeChange,
  currentChange,
  rejectBtn,
  applyBtn,
  global,
} = useDetailTable();

//打印按钮
//数据
const print = ref();
//标题
const printTitle = ref();
const printBtn = (row: any) => {
  console.log(row);
  dialog.title = "入库单打印";
  dialog.height = 500;
  dialog.width = 900;
  print.value = row;
  onShow();
  switch (row.type) {
    case "1":
      return (printTitle.value = "捐赠物资入库单");
    case "2":
      return (printTitle.value = "下拨物资入库单");
    case "3":
      return (printTitle.value = "采购物资入库单");
    case "4":
      return (printTitle.value = "借用物资入库单");
    default:
      return (printTitle.value = "物资入库单");
  }
};

const printObj = reactive({
  id: "printMe",
  preview: true, //是否预览
  extraHead:'<meta http-equiv="Content-Language"content="zh-cn"/>,<style>#printMe{width:100%;height: auto !important;}</style>',
  previewBeforeOpenCallback() {
    console.log("正在加载预览窗口");
    var iframeDoc = document.querySelector(".previewBody iframe") as HTMLIFrameElement;
    var dom = iframeDoc.contentDocument!.querySelector("#printMe") as HTMLElement;
    if (dom) {
      dom.style.height = "100%";
    }
  },
  previewOpenCallback() {
    console.log("已经加载完预览窗口");
  },
  beforeOpenCallback(vus: any) {
    console.log(vus);
    console.log("打开之前");
  },
  openCallback() {
    console.log("执行了打印");
  },
  closeCallback() {
    console.log("关闭了打印工具");
  },
});
</script>

<style media="print">
@page {
  size: auto;
  margin: 3mm;
}
@media print {
  html {
    background-color: #ffffff;
    height: auto;
    margin: 0px;
  }
  body {
    border: solid 1px #ffffff;
    /* margin: 10mm 15mm 10mm 15mm; */
  }
  #printMe table {
    table-layout: auto !important;
    border: solid 1px #f2f2f2;
  }
  #printMe .el-table__header-wrapper .el-table__header {
    width: 100% !important;
    border: solid 1px #f2f2f2;
  }
  #printMe .el-table__body-wrapper .el-table__body {
    width: 100% !important;
    border: solid 1px #f2f2f2;
  }
  #printMe #pagetable table {
    table-layout: fixed !important;
    border: solid 1px #f2f2f2;
  }
}
</style>
<style scoped lang="scss">
.container {
  overflow-x: initial;
  overflow-y: auto;
}
:deep(.el-divider__text) {
  font-weight: 600;
  font-size: 15px;
}
:deep(.el-form-item__label) {
  font-weight: 600;
}
.print-title {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
  font-weight: 600;
}
</style>
