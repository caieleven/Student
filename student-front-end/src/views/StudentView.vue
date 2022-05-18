
<template>
  <div>
  <el-container class="layout-container-demo" style="height: 100%">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu :default-openeds="['1', '3']" style="height: 100%">
          <el-sub-menu index="1">
            <template #title>
              <el-icon><message /></el-icon>Navigator One
            </template>
            <el-menu-item-group>
              <template #title>Group 1</template>
              <el-menu-item index="1-1">Option 1</el-menu-item>
              <el-menu-item index="1-2">Option 2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group 2">
              <el-menu-item index="1-3">Option 3</el-menu-item>
            </el-menu-item-group>
            <el-sub-menu index="1-4">
              <template #title>Option4</template>
              <el-menu-item index="1-4-1">Option 4-1</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><Menu /></el-icon>Navigator Two
            </template>
            <el-menu-item-group>
              <template #title>Group 1</template>
              <el-menu-item index="2-1">Option 1</el-menu-item>
              <el-menu-item index="2-2">Option 2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group 2">
              <el-menu-item index="2-3">Option 3</el-menu-item>
            </el-menu-item-group>
            <el-sub-menu index="2-4">
              <template #title>Option 4</template>
              <el-menu-item index="2-4-1">Option 4-1</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon><setting /></el-icon>Navigator Three
            </template>
            <el-menu-item-group>
              <template #title>Group 1</template>
              <el-menu-item index="3-1">Option 1</el-menu-item>
              <el-menu-item index="3-2">Option 2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group 2">
              <el-menu-item index="3-3">Option 3</el-menu-item>
            </el-menu-item-group>
            <el-sub-menu index="3-4">
              <template #title>Option 4</template>
              <el-menu-item index="3-4-1">Option 4-1</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <div class="toolbar">
          <el-dropdown>
            <el-icon style="margin-right: 8px; margin-top: 1px"
            ><setting
            /></el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>View</el-dropdown-item>
                <el-dropdown-item>Add</el-dropdown-item>
                <el-dropdown-item>Delete</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span>Tom</span>
        </div>
      </el-header>

      <el-main>
        <div style="padding: 10px 0">
          <el-input class="w-50 m-2" placeholder="请输入关键词" style="width: 200px" suffix-icon="Search" v-model="username"><el-icon><Search /></el-icon></el-input>
          <el-button class="ml-5" type="primary" round>搜索</el-button>
          <el-button class="ml-5" type="info" @click="reset" round>重置</el-button>
        </div>
        <div class="pd-10">
          <el-select v-model="className" filterable placeholder="选择班级">
            <el-option
                v-for="item in classes"
                :key="item.cid"
                :label="item.name"
                :value="item.cid"
            />
          </el-select>
        </div>
        <div style="margin: 10px 0">
          <el-button type="primary">新增<el-icon><Plus /></el-icon></el-button>
          <el-button type="danger">删除<el-icon><Minus /></el-icon></el-button>
        </div>
        <el-scrollbar>
<!--          固定首行和首列-->
          <el-table :data="tableData" style="width: 100%" border stripe>
            <el-table-column fixed prop="name" label="姓名" width="120" />
            <el-table-column prop="sid" label="学号" width="120" />
            <el-table-column prop="sex" label="性别" width="50" />
            <el-table-column prop="className" label="班级" width="200" />
            <el-table-column prop="major" label="专业" width="200" />
            <el-table-column prop="tel" label="电话号码" width="200" />
            <el-table-column prop="email" label="邮箱" width="200" />
            <el-table-column label="操作" width="200">
              <div>
                <el-button type="info">编辑<el-icon><EditPen /></el-icon></el-button>
                <el-button type="danger">删除<el-icon><Delete /></el-icon></el-button>
              </div>
            </el-table-column>
          </el-table>
          <div class="pd-10">
            <el-pagination
                v-model:currentPage="pageNum"
                v-model:page-size="pageSize"
                :page-sizes="[5, 10, 20, 50]"
                layout="total, sizes, prev, pager, next, jumper"
                :total="totalNum"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :hide-on-single-page=true
            />
          </div>
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
  </div>
</template>

<!--<script lang="" setup>-->
<!--import axios from "axios";-->
<!--import {ref} from 'vue'-->
<!--import { Menu as IconMenu, Message, Setting, Search } from '@element-plus/icons-vue';-->
<!--const item = {-->
<!--  date: '2016-05-02',-->
<!--  name: 'Tom',-->
<!--  address: 'No. 189, Grove St, Los Angeles',-->
<!--};-->
<!--// const tableData = ref(Array.from({ length: 20 }).fill(item))-->
<!--var tableData;-->
<!--var totalNum;-->
<!--axios.get('http://localhost:8181/student/page',{-->
<!--  params:{-->
<!--    pageNum: 1,-->
<!--    pageSize: 10-->
<!--  }-->
<!--}).then(function (resp){-->
<!--  tableData = resp.data.data;-->
<!--  totalNum = resp.total;-->
<!--  console.log(resp)-->
<!--});-->
<!--console.log(totalNum)-->

<!--</script>-->
<script>
import axios from "axios";
import request from "@/utils/request";
export default {
  name:"Student",
  data(){
    return{
      tableData:[],
      totalNum: 0,
      pageSize: 10,
      pageNum: 1,
      username: "",
      className: [],
      classes: {},
    }
  },
  created() {
    this.loadStudents();
    this.loadClasses();
  },
  methods:{
    loadStudents(){
      request.get('student/page',{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize
        }
      }).then(res => {
        this.tableData = res.data;
        this.totalNum = res.count;
      })
    },
    handleSizeChange(pageSize){
      this.pageSize=pageSize;
      request.get('http://localhost:8181/student/page',{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data;
        this.totalNum = res.count;
      })
    },
    handleCurrentChange(pageNum){
      this.pageNum=pageNum;
      request.get('http://localhost:8181/student/page',{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data;
        this.totalNum = res.count;
      })
    },
    reset(){
      this.username="";
    },
    loadClasses(){
      request.get('class/allCidAndName').then(res =>{
        console.log(res);
        this.classes=res;
      })
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
</style>