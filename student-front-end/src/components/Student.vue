<template>
  <div>
        <el-main>
          <div style="padding: 10px 0">
            <el-input class="w-50 m-2" placeholder="请输入关键词" style="width: 200px" suffix-icon="Search"
                      v-model="username">
              <el-icon>
                <Search/>
              </el-icon>
            </el-input>
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
<!--          新增 删除-->
          <div style="margin: 10px 0">
            <el-button type="primary">新增
              <el-icon>
                <Plus/>
              </el-icon>
            </el-button>
            <el-button type="danger">删除
              <el-icon>
                <Minus/>
              </el-icon>
            </el-button>
          </div>
          <el-scrollbar>
            <!--          固定首行和首列-->
            <el-table :data="tableData" style="width: 100%" border stripe>
              <el-table-column fixed prop="name" label="姓名" width="120"/>
              <el-table-column prop="sid" label="学号" width="120"/>
              <el-table-column prop="sex" label="性别" width="50"/>
              <el-table-column prop="className" label="班级" width="200"/>
              <el-table-column prop="major" label="专业" width="200"/>
              <el-table-column prop="tel" label="电话号码" width="200"/>
              <el-table-column prop="email" label="邮箱" width="200"/>
              <el-table-column label="操作" width="200">
                <div>
                  <el-button type="info">编辑
                    <el-icon>
                      <EditPen/>
                    </el-icon>
                  </el-button>
                  <el-button type="danger">删除
                    <el-icon>
                      <Delete/>
                    </el-icon>
                  </el-button>
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
  </div>
</template>

<script>
import request from "@/utils/request";
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: "Student",
  components: {
    Aside,
    Header
  },
  data() {
    return {
      tableData: [],
      totalNum: 0,
      pageSize: 10,
      pageNum: 1,
      username: "",
      className: [],
      classes: {},
      user: {}
    }
  },
  created() {
    this.loadStudents();
    this.loadClasses();
  },
  methods: {
    loadStudents() {
      request.get('student/page', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.data;
        this.totalNum = res.count;
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      request.get('http://localhost:8181/student/page', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data;
        this.totalNum = res.count;
      })
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      request.get('http://localhost:8181/student/page', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data;
        this.totalNum = res.count;
      })
    },
    reset() {
      this.username = "";
    },
    loadClasses() {
      request.get('class/allCidAndName').then(res => {
        console.log(res);
        this.classes = res;
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