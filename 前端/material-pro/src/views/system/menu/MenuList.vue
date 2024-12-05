<template>
  <el-main>
    <el-button
      v-if="global.$hasPerm(['sys:menu:add'])"
      type="primary"
      style="margin-bottom: 20px"
      :icon="Plus"
      size="default"
      @click="addBtn"
      >新增</el-button
    >
    <!-- 表格 -->
    <el-table
      :height="tableHeight"
      :data="tableList.list"
      style="width: 100%"
      row-key="menuId"
      border
      default-expand-all
      stripe
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="title" label="菜单名称"></el-table-column>
      <el-table-column prop="type" label="类型">
        <template #default="scope">
          <el-tag v-if="scope.row.type == '0'" size="default">目录</el-tag>
          <el-tag v-if="scope.row.type == '1'" type="success" size="default"
            >菜单</el-tag
          >
          <el-tag v-if="scope.row.type == '2'" type="danger" size="default"
            >按钮</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="icon" label="图标">
        <template #default="scope">
          <el-icon>
            <!-- 动态组件的方式显示图标 -->
            <component v-if="scope.row.icon" :is="scope.row.icon"></component>
          </el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="路由名称"></el-table-column>
      <el-table-column prop="path" label="路由地址"></el-table-column>
      <el-table-column prop="url" label="组件路径"></el-table-column>
      <el-table-column prop="code" label="权限字段"></el-table-column>
      <el-table-column
        v-if="global.$hasPerm(['sys:menu:edit','sys:menu:delete'])"
        label="操作"
        width="220"
        align="center"
      >
        <template #default="scope">
          <el-button
            v-if="global.$hasPerm(['sys:menu:edit'])"
            type="success"
            :icon="Edit"
            size="default"
            @click="editBtn(scope.row)"
            >编辑</el-button
          >
          <el-button
            v-if="global.$hasPerm(['sys:menu:delete'])"
            type="danger"
            :icon="Delete"
            size="default"
            @click="deleteBtn(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增弹框 -->
    <AddMenu ref="addRef" @reFresh="getList"></AddMenu>
  </el-main>
</template>

<script setup lang="ts">
import AddMenu from "./AddMenu.vue";
import useMenu from "@/composables/menu/useMenu";
import useTable from "@/composables/menu/useTable";
import { Search, Edit, Plus, Close, Delete } from "@element-plus/icons-vue";
const { tableList, getList, tableHeight } = useTable();
const { addBtn, editBtn, deleteBtn, addRef, global } = useMenu(getList);
</script>

<style scoped></style>
