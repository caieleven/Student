<!--<template>-->
<!--  <el-affix>-->
<!--  <el-scrollbar style="margin-top: 0; height: 100%">-->
<!--    <el-menu style="height: 100%" class="layout-container-demo" router>-->
<!--      <el-sub-menu v-for="(item, index) in $router.options.routes" :index="index+''">-->
<!--        <template slot="title">{{item.name}}</template>-->
<!--        <el-menu-item v-for="(subitem, subindex) in item.children" :index="subindex+''">{{subitem.name}}</el-menu-item>-->
<!--      </el-sub-menu>-->
<!--    </el-menu>-->
<!--  </el-scrollbar>-->
<!--  </el-affix>-->
<!--</template>-->
<template>
  <el-affix>
    <el-scrollbar style="margin-top: 0; height: 100%">
      <el-menu style="height: 100%" class="layout-container-demo" router>
        <template v-for="(item, index) in $router.options.routes">
          <el-menu-item v-if="item.children==null && item.meta.show" :index="item.path">{{item.name}}</el-menu-item>
          <el-sub-menu v-else-if="item.meta.roles.indexOf(role) > -1" :index="item.path">
            <template #title><span>{{item.name}}</span></template>
            <template v-if="item.children">
            <template v-for="(subitem, subindex) in item.children">
              <el-menu-item v-if="subitem.meta.roles.indexOf(role) > -1" :index="subitem.path">
                {{subitem.name}}
              </el-menu-item>
            </template>
            </template>
          </el-sub-menu>
        </template>
      </el-menu>
    </el-scrollbar>
  </el-affix>
</template>

<script>

export default {
  name: "Aside",
  data(){
    return {
      role: ""
    }
  },
  created() {
    this.role = JSON.parse(localStorage.getItem("user")).groupName;
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
</style>