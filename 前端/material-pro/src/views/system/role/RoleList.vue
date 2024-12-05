<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="listParm" :inline="true" size="default">
      <el-form-item>
        <el-input
          v-model="listParm.roleName"
          placeholder="请输入角色名称"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="searchBtn" :icon="Search">搜索</el-button>
        <el-button @click="resetBtn" plain type="danger" :icon="Close"
          >重置</el-button
        >
        <el-button
          v-if="global.$hasPerm(['sys:role:add'])"
          type="primary"
          @click="addBtn"
          :icon="Plus"
          >新增</el-button
        >
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :height="tableHeight" :data="tableList.list" border stripe>
      <el-table-column prop="roleName" label="角色名称"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column
        v-if="
          global.$hasPerm([
            'sys:role:edit',
            'sys:role:delete',
            'sys:role:assign',
          ])
        "
        label="操作"
        width="320"
        align="center"
      >
        <template #default="scope">
          <el-button
            v-if="global.$hasPerm(['sys:role:edit'])"
            type="primary"
            :icon="Edit"
            size="default"
            @click="editBtn(scope.row)"
            >编辑</el-button
          >
          <el-button
            v-if="global.$hasPerm(['sys:role:assign'])"
            type="success"
            :icon="Edit"
            size="default"
            @click="assignBtn(scope.row)"
            >分配权限</el-button
          >
          <el-button
            v-if="global.$hasPerm(['sys:role:delete'])"
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

    <!-- 新增、编辑弹框 -->
    <add-role ref="addRef" @refresh="refresh"></add-role>
    <!-- 权限树 -->
    <assign-tree ref="assignRoleRef"></assign-tree>
  </el-main>
</template>

<script setup lang="ts">
import AssignTree from "./AssignTree.vue";
import AddRole from "./AddRole.vue";
import { Search, Edit, Plus, Close, Delete } from "@element-plus/icons-vue";
import useRole from "@/composables/role/useRole";
import useTable from "@/composables/role/useTable";
//表格相关的操作
const {
  getList,
  refresh,
  tableHeight,
  listParm,
  searchBtn,
  resetBtn,
  tableList,
  sizeChange,
  currentChange,
} = useTable();
//新增、编辑、删除操作
const { global, addBtn, editBtn, deleteBtn, addRef, assignRoleRef, assignBtn } =
  useRole(getList);
</script>

<style scoped></style>
