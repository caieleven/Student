<template>
  <div style="height: 100%">
    <el-container class="layout-container-demo" style="height: 100%">
      <el-aside width="200px" style="margin-top: 0; height: 100%">
        <Aside></Aside>
      </el-aside>
      <el-container>
        <el-header >
          <Header></Header>
        </el-header>
        <el-main>
          <p>主页</p>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from "@/components/Aside";
import Student from "@/components/Student";
import Header from "@/components/Header";

export default {
  name: "Home",
  components: {
    Aside,
    Header,
    Student
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