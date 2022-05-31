<template>
  <div style="height: 100%">
    <el-container class="layout-container-demo" style="height: 100%">
      <el-card class="round mg-10-10" style="height:100vm">
        <el-aside width="200px" style="margin-top: 0; height: 100%">
          <Aside></Aside>
        </el-aside>
      </el-card>

      <el-container>
        <el-header >
          <Header></Header>
        </el-header>
        <el-main>
            <p style="text-align: center; font-size: 80px">{{username}}{{role}}你好!</p>
          <p style="text-align: center; font-size: 80px" >欢迎使用学生管理系统!</p>
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
    Header
  },
  data(){
    let user=JSON.parse(localStorage.getItem("user"));
    let username = user.username;
    let role=user.groupName;
    if(user.groupName=="admin"){
      role="管理员";
      username="";
    }
    else if(user.groupName=="counsellor"){
      role="老师";
    }
    else{
      role="助手";
    }
    return{
      username,
      role
    }
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
.main {
  justify-content: center;
}

.header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  min-width: 48.5rem;
  height: 6rem;
}

.body {
  background-color: #f1f2f6;
  overflow-x: hidden;
}

.round {
  border-radius: 10px;
}

.cardStandard{
  width: 1026px;
}

</style>