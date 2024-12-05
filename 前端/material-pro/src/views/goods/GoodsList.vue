<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="listParm" :inline="true" size="default">
      <el-form-item>
        <el-input
          v-model="listParm.infoName"
          placeholder="请输入物资名称"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button :icon="Search" @click="searchBtn">搜索</el-button>
        <el-button type="danger" plain :icon="Close" @click="resetBtn"
          >重置</el-button
        >
        <el-button
          v-if="global.$hasPerm(['sys:goodsList:add'])"
          :icon="Plus"
          type="primary"
          @click="addBtn"
          >新增</el-button
        >
        <el-button
          v-if="global.$hasPerm(['sys:goodsList:export'])"
          :icon="Plus"
          type="success"
          @click="exportBtn"
          >导出</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :height="tableHeight" :data="tableList.list" border stripe>
      <el-table-column prop="image" width="90" label="物资图片" align="center">
        <template #default="scope">
          <el-image
            style="width: 60px; height: 60px; border-radius: 50%"
            :src="scope.row.image"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="infoName" label="物资名称"></el-table-column>
      <el-table-column prop="categoryName" label="物资分类"></el-table-column>
      <el-table-column prop="specs" label="规格"></el-table-column>
      <el-table-column prop="unit" label="单位"></el-table-column>
      <el-table-column prop="store" label="库存"></el-table-column>
      <el-table-column
        label="操作"
        v-if="global.$hasPerm(['sys:goodsList:edit', 'sys:goodsList:delete'])"
        align="center"
        width="200"
      >
        <template #default="scope">
          <el-button
            v-if="global.$hasPerm(['sys:goodsList:edit'])"
            type="primary"
            :icon="Edit"
            size="default"
            @click="editBtn(scope.row)"
            >编辑</el-button
          >
          <el-button
            v-if="global.$hasPerm(['sys:goodsList:delete'])"
            type="danger"
            :icon="Delete"
            size="default"
            @click="deleteBtn(scope.row)"
            >删除</el-button
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

    <!-- 新增弹框 -->
    <AddInfo ref="addRef" @reFresh="getList"></AddInfo>
  </el-main>
</template>

<script setup lang="ts">
import AddInfo from "./AddInfo.vue";
import { Search, Edit, Plus, Close, Delete } from "@element-plus/icons-vue";
import useTable from "@/composables/material/useTable";
import useMaterial from "@/composables/material/useMaterial";
import { loginStore } from "@/store/login";
const store = loginStore()
//表格
const {
  tableHeight,
  listParm,
  getList,
  resetBtn,
  searchBtn,
  tableList,
  sizeChange,
  currentChange,
} = useTable();
//新增
const { addBtn, editBtn, deleteBtn, addRef, global } = useMaterial(getList);
//导出
const exportBtn = () => {
  const abtn = document.createElement("a");
  abtn.href = process.env.BASE_API + "/api/materialInfo/exporMaterial?token="+store.getToken;
  abtn.click();
};
</script>

<style scoped></style>
