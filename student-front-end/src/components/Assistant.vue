<template>
  <div>
    <el-main>
      <!--          搜索功能-->
      <div style="padding: 10px 0">
        <el-input style="width: 200px" placeholder="请输入uid" v-model="uid"></el-input>
        <el-input style="width: 200px" placeholder="请输入姓名" class="ml-5" v-model="CounsellorName"></el-input>
        <el-button class="ml-5" type="primary" @click="search" round>搜索</el-button>
        <el-button class="ml-5" type="warning" @click="reset" round>重置</el-button>
      </div>
      <!--          新增 删除-->
      <div style="margin: 10px 0">
        <el-button type="primary" @click="handleAdd">新增<el-icon><Plus/></el-icon></el-button>
        <el-popconfirm
            confirm-button-text="是的"
            cancel-button-text="取消"
            icon="InfoFilled"
            icon-color="#626AEF"
            title="确定要删除选中的对象吗?"
            @confirm="delBatch"
        ><template #reference><el-button type="danger">删除<el-icon><Minus/></el-icon></el-button></template>
        </el-popconfirm>
      </div>

      <el-scrollbar>
        <!--          固定首行和首列-->
        <el-table :data="tableData" ref="multipleTable" style="width: 100%" border stripe :row-key="getRowKeys" @selection-change="handleSelectionChange">
          <el-table-column type="selection" :reserve-selection=true></el-table-column>
          <el-table-column prop="name" label="uid" width="120"/>
          <el-table-column prop="sid" label="姓名" width="120"/>
          <el-table-column prop="sex" label="密码" width="50"/>
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
import Aside from "@/components/Aside";
import Header from "@/components/Header";
import {InfoFilled} from "@element-plus/icons-vue";

export default {
  name: "Counsellor",
  components: {
    Aside,
    Header,
    InfoFilled,
  },
  data() {
    return {
      uid: "",
      counsellorName: "",
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      totalNum: 0
    }
  },
  created() {

  },
  methods: {
    search() {

    },
    reset() {

    },
    handleAdd() {

    },
    delBatch() {

    },
    getRowKeys () {

    },
    handleSelectionChange() {

    },
    handleEdit() {

    },
    handleSizeChange() {

    },
    handleCurrentChange() {

    }
  }
}
</script>

<style scoped>

</style>