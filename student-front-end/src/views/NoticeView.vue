<template>
  <div style="height: 100vh" class="body">
    <el-container class="layout-container-demo" style="height: 100%">
      <el-card class="round mg-10-10" style="height:100vm">
        <el-aside width="200px" style="height: 100%">
          <Aside></Aside>
        </el-aside>
      </el-card>
      <el-container>
        <el-header >
          <el-card style="height: 50px" class="round mg-10-10">
            <Header></Header>
          </el-card>
        </el-header>
        <el-main style=" justify-content: center">
          <div class="mg-10">
            <el-card class="round cardStandard" style="text-align: left">
              <el-input class="fontFamily" style="width: 200px" placeholder="输入关键词" v-model="queryNotice.keyword"></el-input>
              <el-button class="round fontFamily" style="margin-left: 15px" type="primary" @click="getNotices">搜索</el-button>
              <el-button class="ml-5 round fontFamily" type="warning" @click="resetClick">重置</el-button>
              <el-button v-if="user.groupId !== 3" class="ml-5 round fontFamily" type="primary" @click="dialogAddNotice=true">新增<el-icon><Plus/></el-icon></el-button>
            </el-card>
          </div>

          <el-card class="round boxSize" style="height: 80%">
            <el-collapse accordion v-model="activeNames" v-for="(item, index) in notices">
              <el-collapse-item :name="index + ''">
                <template #title>
                  <span style="font-size: 10px; color: #E6A23C">{{ item.title }}</span><i style="color: #E6A23C" class="header-icon el-icon-info"></i>
                  <span style="margin-left: 20px">{{item.publisher }}</span>
                  <span style="margin-left: 80px">{{item.time}}</span>
                </template>
                <div>
                  <div>{{ item.content }}</div>
                </div>
              </el-collapse-item>
            </el-collapse>
          </el-card>

          <el-dialog title="发布公告" v-model="dialogAddNotice" width="70%">
            <el-form label-width="80px" style="text-align: center">
              <el-form-item label="标题">
                <el-input v-model="noticeForm.title"></el-input>
              </el-form-item>
              <el-form-item label="内容">
                <el-input v-model="noticeForm.content" :autosize="{minRows:2}" type="textarea"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer" style="text-align: center">
              <el-button class="round fontFamily" @click="dialogAddNotice = false">取 消</el-button>
              <el-button type="primary" class="round fontFamily" @click="handleAddNotice">确 定</el-button>
            </div>
          </el-dialog>



        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";
import * as echarts from 'echarts';
import request from "@/utils/request";

export default {
  name: "Notice",
  components: {
    Aside,
    Header
  },
  data(){
    return{
      activeNames:['0'],
      // user: JSON.parse(localStorage.getItem("user")),
      notices: [],
      dialogAddNotice: false,
      noticeForm: {}, //新增公告表单
      queryNotice: {}, //查询条件，只有关键词
    }
  },
  created() {
    this.user=JSON.parse(localStorage.getItem("user"));
    this.getNotices();
  },
  mounted() {
  },
  methods: {
    getNotices(){
      request.post(`notice/getNotice/${this.user.uid}`, this.queryNotice).then(res=>{
        this.notices = res.data;
      })
    },
    handleAddNotice(){
      this.noticeForm.userId = this.user.uid;
      request.post("notice/addNew", this.noticeForm).then(res=>{
        this.$message.success(res.message);
        this.dialogAddNotice = false;
        this.noticeForm = {};
      })
    },
    //reset按钮
    resetClick(){
      this.queryNotice = {};
      this.getNotices();
    }
  }
}
</script>

<style scoped>
.round {
  border-radius: 10px;
}
.body {
  background-color: #f1f2f6;
  overflow-x: hidden;
}
/*.cardStandard{*/
/*  width: 1026px;*/
/*}*/
.fontStyle {
  color: var(--Gray-8);
  font-size: 15px;
  line-height: 1rem;
  font-weight: 700;
}
.boxSize {
  margin: 10px 35px;
  /*height: 50%;*/
  /*width: 40%;*/
}
</style>