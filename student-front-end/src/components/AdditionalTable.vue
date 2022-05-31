<template>
  <div>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增
        <el-icon>
          <Plus/>
        </el-icon>
      </el-button>
    </div>

<!--    新增表展示-->
    <div>
      <el-table
          ref="additionalTablesInDialog"
          :data="additionalTables"
          highlight-current-row
          style="width: 100%"
          @current-change="handleCurrentChangeOfATable"
      >
        <el-table-column type="index" width="50" />
        <el-table-column property="tableName" label="表名" width="120" />
        <el-table-column property="counsellorName" label="教师名" width="120" />
        <el-table-column property="assistantName" label="助手名" width="240" />
      </el-table>
    </div>





    <div>
<!--      新增表对话框-->
      <el-dialog title="新增表" v-model="dialogAddFormVisible" width="50%" center>
          <el-form :model="newForm" label-width="120px">
            <div>
            <el-form-item label="表名">
              <el-input v-model="newForm.tableName" autocomplete="off"></el-input>
            </el-form-item>
            </div>
            <el-form-item label="基本表字段">
              <el-checkbox-group v-model="newForm.baseColumns" @change="handleCheckChange" label="基本表">
                <el-checkbox v-for="item in probsList" :label="item">
                  {{baseprobs[item]}}
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <div>
              <el-form-item v-for="(column, index) in newForm.additionalColumns"
                            :key="column.key"
                            :prop="'additionColumns.' + index + 'name'"
                            :label="'新字段'+(index+1)">
                <el-input v-model="column.name"></el-input>
                <el-button class="mt-2" @click.prevent="removeColumn(column)" type="danger">删除字段</el-button>
              </el-form-item>
            </div>
            <el-form-item style="text-align: left">
              <el-button @click="addColumn" type="success">新添字段</el-button>
              <el-button @click="submitForm" type="primary">提交</el-button>
            </el-form-item>
            <el-form-item style="text-align: center">

            </el-form-item>
          </el-form>

      </el-dialog>

<!--      表格详情-->
      <el-dialog title="" v-model="dialogATableInfo" width="50%" center>

      </el-dialog>
    </div>


  </div>
</template>

<script>
import {getCurrentInstance} from "vue";
import request from "@/utils/request";

export default {
  name: "AdditionalTable",
  data(){
    // this.user = JSON.parse(localStorage.getItem("user"));
    return {
      newForm: {
        tableName: '',
        baseColumns: ['sid', 'name'],
        additionalColumns: [
        ] //为对象数组，包含两个字段，一个key，一个name，key为自动生成，name为新增字段名
      }, //新增表单
      dialogAddFormVisible: false, //新增对话框显示,
      dialogATableInfo: false,  //表格详情对话框显示

      //附加表数据
      additionalTables: [
        // {
        //   tableName:"",
        //   counsellorName: "",
        //   counsellorId: null,
        //   assistantsName: ""
        // }
      ],
      selectedAdditionalTable: {

      },
    }
  },
  setup(){
    let baseprobs = getCurrentInstance()?.appContext.config.globalProperties.$problist;
    const probsList = Object.keys(baseprobs)
    return {
      baseprobs,
      probsList
    }
  },
  created() {
    this.user = JSON.parse(localStorage.getItem("user"));
    this.getAdditionalTables();
  },
  mounted() {
    window.vue = this;
  },
  methods: {
    //新增表触发
    handleAdd() {
      this.dialogAddFormVisible = true;
    },
    handleCheckChange(){
    },
    // 新增表中删除字段
    removeColumn(column){
      const index = this.newForm.additionalColumns.indexOf(column);
      if (index != -1){
        this.newForm.additionalColumns.splice(index, 1)
      }
    },
    //新增表中增加字段
    addColumn(){
      this.newForm.additionalColumns.push({
        name: "",
        key: Date.now()
      });
    },
    submitForm(){
      //先对newForm处理
      this.newForm.counsellorId = this.user.uid;
      //先存储副本，如果省略该部，新增失败之后，无法还原表单
      let tempAdditionalColumns = this.newForm.additionalColumns;
      this.newForm.additionalColumns = this.newForm.additionalColumns.map(
          obj => {return obj.name}
      );
      //请求后端
      request.post("additionalTable/addNewTable", this.newForm).then(res=>{
        if(res){
          if(res.code == 0){
            this.$message.success(res.message);
            //成功之后，初始化表单
            this.resetForm();
            this.dialogAddFormVisible = false;
          }
          else{
            this.$message.error(res.message);
            //还原表单
            this.newForm.additionalColumns = tempAdditionalColumns;
          }
        }
        else{
          this.$message.error("新增失败，原因未知");
          this.newForm.additionalColumns = tempAdditionalColumns;
        }

      })
      console.log(this.newForm)
    },
    // 表单复原操作
    resetForm(){
      this.newForm = {
        tableName: '',
        baseColumns: ['sid', 'name'],
        additionalColumns: [
        ]
      }
    },
    // 附加表表格单选
    handleCurrentChangeOfATable(val){
      this.selectedAdditionalTable["tableName"] = val["tableName"];
    },
    getAdditionalTables(){
      request.get("additionalTable/getTable", {
        params: {
          uid: this.user.uid,
        }
      }).then(res=>{
        let tableNames = Object.keys(res.data);
        tableNames.forEach(key=>{
          let array = res.data[key];
          let assistantsName = [];
          let counsellorName = "";
          let counsellorId = null;
          for(let value of array.values()){
            if(value["assistantName"]!=null)
              assistantsName.push(value["assistantName"]);
            counsellorName = value["counsellorName"];
            counsellorId = value["counsellorId"];
          }
          let assistantName = assistantsName.join();
          let item = {
            tableName:key,
            counsellorName: counsellorName,
            counsellorId: counsellorId,
            assistantName: assistantName
          };
          this.additionalTables.push(item);
        })
      });
      // console.log(this.additionalTables);
    },
    getStudentsInfoFromTable(){
      this.selectedAdditionalTable["uid"] = this.user.uid;
    }
  }
}
</script>

<style scoped>

</style>