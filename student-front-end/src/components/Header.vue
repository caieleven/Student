<template>
  <div style="text-align: right; font-size: 12px; height: 100%" class="layout-container-demo">
    <div style="margin-top: 0; height: 100%">
      <el-dropdown>
        <el-icon style="margin-right: 8px; margin-top: 20px"><setting/></el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>View</el-dropdown-item>
            <el-dropdown-item>Add</el-dropdown-item>
            <el-dropdown-item>Delete</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <span>{{ user.username }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: {}
    }
  },
  created() {
    this.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
  }
}
</script>

<style scoped>
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>