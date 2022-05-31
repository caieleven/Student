<template>
  <div style="height: 100%" class="body">
    <el-container class="layout-container-demo" style="height: 100vh">
      <el-card class="round mg-10-10" style="height: 100vm">
        <el-aside width="200px" style="height: 100%">
          <Aside></Aside>
        </el-aside>
      </el-card>
      <el-container>
        <el-header>
          <el-card style="height: 50px" class="round mg-10-10">
          <Header></Header>
          </el-card>
        </el-header>
        <el-main>
          <router-view />
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
  name: "Main",
  components: {
    Aside,
    Header,
    Student
  }
}
</script>

<style scoped>
/*.layout-container-demo .el-header {*/
/*  position: relative;*/
/*  background-color: var(--el-color-primary-light-7);*/
/*  color: var(--el-text-color-primary);*/
/*}*/

/*.layout-container-demo .el-aside {*/
/*  color: var(--el-text-color-primary);*/
/*  background: var(--el-color-primary-light-8);*/
/*}*/

/*.layout-container-demo .el-menu {*/
/*  border-right: none;*/
/*}*/

/*.layout-container-demo .el-main {*/
/*  padding: 0;*/
/*}*/

/*.layout-container-demo .toolbar {*/
/*  display: inline-flex;*/
/*  align-items: center;*/
/*  justify-content: center;*/
/*  height: 100%;*/
/*  right: 20px;*/
/*}*/
.round {
  border-radius: 10px;
}
.body {
  background-color: #f1f2f6;
  overflow-x: hidden;
}
.cardStandard{
  width: 1026px;
}
</style>