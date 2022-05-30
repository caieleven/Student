<template>
  <div style="text-align: right; font-size: 12px; height: 100%" class="layout-container-demo">
    <div style="margin-top: 0; height: 100%">
      <el-dropdown @command="handleCommand">
        <el-icon style="margin-right: 8px; margin-top: 1px"><setting/></el-icon>
        <template #dropdown>
          <el-dropdown-menu class="round">
<!--            <el-dropdown-item>View</el-dropdown-item>-->
<!--            <el-dropdown-item>Add</el-dropdown-item>-->
            <el-dropdown-item style="font-size: 12px">Log Out</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
<!--      <el-icon style="margin-right: 8px; margin-top: 20px; line-height: 200px" @click="handleCommand"><SwitchButton/></el-icon>-->
      <span class="fontStyle">{{ user.username }}</span>
    </div>
  </div>
</template>

<script>
import {Sunrise, SwitchButton} from "@element-plus/icons-vue";
export default {
  name: "Header",
  components: {SwitchButton, Sunrise},
  data() {
    return {
      user: {}
    }
  },
  created() {
    this.user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
  },
  methods: {
    handleCommand(command){
      localStorage.removeItem("user");
      this.$router.push("/");
    }
  }
}
</script>

<style lang="css" scoped>
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
.fontStyle {
  color: var(--Gray-8);
  font-size: 15px;
  line-height: 1rem;
  font-weight: 700;
}
.round /deep/ .el-dropdown-menu{
  border-radius: 30px!important;
}
</style>