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
        <el-main style="display: flex; justify-content: center">
          <el-card class="round boxSize" style="height: 80%; width: 20%">
            <p class="fontStyle">{{username}}{{role}}你好!</p>
            <p class="fontStyle">欢迎使用学生与活动管理系统!</p>
            <br>
            <div v-if="user.groupName=='admin'">
              <p class="fontStyle">当前用户人数：</p>
              <p class="fontStyle" style="text-indent: 1em">管理员：{{adminNum}}</p>
              <p class="fontStyle" style="text-indent: 1em">教师：{{counsellorNum}}</p>
              <p class="fontStyle" style="text-indent: 1em">助手：{{assistantNum}}</p>
            </div>
          </el-card>
          <el-card class="round boxSize" style="height: 80%; width: 80%">
            <div id="main" style="width: 100%; height: 500px">
            </div>
          </el-card>
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
      role,
      cid: [],
      total: [],
      adminNum: 0,
      counsellorNum: 0,
      assistantNum: 0
    }
  },
  created() {
    this.user=JSON.parse(localStorage.getItem("user"));
  },
  mounted() {
    this.getUserTotalByGroup();
    this.getCSTotal();
  },
  methods: {
    getBar() {
      let option = {
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [],
            type: 'bar'
          }
        ]
      };
      let chartDom = document.getElementById('main');
      let myChart = echarts.init(chartDom);
      option.xAxis.data = this.cid;
      option.series[0].data = this.total;
      myChart.setOption(option);
    },
    getCSTotal() {
      request.get("class/getCidAndTotal").then( res => {
        for (let i in res) {
          this.cid.push(res[i].cid);
          this.total.push(res[i].total);
        }
        this.getBar();
      })
    },
    getUserTotalByGroup() {
      request.get("user/getUserTotalByGroup").then( res => {
        this.adminNum = res.data.adminNum;
        this.counsellorNum = res.data.counsellorNum;
        this.assistantNum = res.data.assistantNum;
      })
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