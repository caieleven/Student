<template>
  <div>
    <div style="margin: 10px 0">
      <el-button type="primary" class="round fontFamily" @click="handleAdd">新增
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
          style="width: 100%"
      >
        <el-table-column type="index" width="50" />
        <el-table-column property="tableName" label="表名" width="120" />
        <el-table-column property="counsellorName" label="教师名" width="120" />
        <el-table-column property="assistantName" label="助手名" width="240" />
        <el-table-column label="操作" width="200" align="center">
          <template v-slot="scope">
            <el-button class="round fontFamily" type="warning" @click="handleATableEdit(scope.row)">编辑<el-icon><EditPen/></el-icon></el-button>
            <el-popconfirm
                confirm-button-text="是的"
                cancel-button-text="取消"
                icon="InfoFilled"
                icon-color="#626AEF"
                title="确定要删除吗?"
                @confirm="handleATableDelete(scope.row)"
            >
              <template #reference>
                <el-button class="round fontFamily" type="danger" slot="reference">删除<el-icon><Delete/></el-icon></el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
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
      <el-dialog :title="dialogATableInfo.tableName" v-model="dialogATableInfo" width="80%" fullscreen center>
        <el-table :data="selectedATAbleInfo.studentsInfo" stripe style="width: 100%">
          <el-table-column label="基本表字段">
            <template #default="scope">
              <el-table-column v-for="item in selectedATAbleInfo.baseColumns" :label="baseprobs[item]" :prop="item" />
            </template>
          </el-table-column>
          <el-table-column label="附加表字段">
            <template #default="scope">
              <el-table-column v-for="item in selectedATAbleInfo.additionalColumns" :label="item" :prop="item" />
            </template>

          </el-table-column>

          <el-table-column label="操作">
            <template #default="scope">
              <el-row class="mb-4">
                <el-button type="warning" class="round fontFamily" @click="handleStudentEdit(scope.row)" >编辑<el-icon><EditPen/></el-icon></el-button>
                <el-button v-if="['admin', 'counsellor'].indexOf(user.groupName) > -1 " type="danger" class="round fontFamily" @click="handleStudentDelete(scope.row)">删除<el-icon><Delete/></el-icon></el-button>
              </el-row>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>

<!--      编辑表格-->
      <el-dialog :title="selectedStudent.name+'编辑'" v-model="dialogEditATableInfo" width="80%" center>
        <el-form-item v-for="item in selectedATAbleInfo.baseColumns" :label="baseprobs[item]">
          <div>{{selectedStudent[item]}}</div>
        </el-form-item>
        <el-form-item v-for="item in selectedATAbleInfo.additionalColumns" :label="item">
          <input v-model="selectedStudent[item]" style="width: auto" />
        </el-form-item>
        <div slot="footer" class="dialog-footer" style="text-align: center">
          <el-button type="primary" @click="submitStudentEdit">提交</el-button>
        </div>
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
      user: {},
      newForm: {
        tableName: '',
        baseColumns: ['sid', 'name'],
        additionalColumns: [
        ] //为对象数组，包含两个字段，一个key，一个name，key为自动生成，name为新增字段名
      }, //新增表单
      dialogAddFormVisible: false, //新增对话框显示,
      dialogATableInfo: false,  //表格详情对话框显示
      dialogEditATableInfo:false, //表格编辑对话框
      selectedStudent: {},
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
      //选中的附加表详情
      selectedATAbleInfo: {

      }
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
      this.getAdditionalTables()
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
    // 编辑活动表
    handleATableEdit(row){
      this.selectedAdditionalTable["tableName"] = row["tableName"];
      this.selectedAdditionalTable["counsellorId"] = row["counsellorId"];
      this.getStudentsInfoFromTable();
      this.dialogATableInfo = true;

    },
    getAdditionalTables(){
      this.additionalTables = [];
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
    },
    getStudentsInfoFromTable(){
      let queryMap = {};
      request.post(`additionalTable/getStudentsFromTable/${this.selectedAdditionalTable.counsellorId}/${this.selectedAdditionalTable.tableName}`, queryMap).then(res=>{
        this.selectedATAbleInfo.baseColumns = res.data["baseColumns"];
        this.selectedATAbleInfo.additionalColumns = res.data["additionalColumns"];
        this.selectedATAbleInfo.tableName = res.data["tableName"];
        this.selectedATAbleInfo.studentsInfo = [];
        let studentInfo = {};
        let baseInfo = {}, additionalInfo = {};
        for (let value of res.data["students"].values()){
          additionalInfo = value["additionalInfo"];
          delete value["additionalInfo"];
          baseInfo = value;
          // studentInfo["baseInfo"] = baseInfo;
          // studentInfo["additionalInfo"] = additionalInfo;
          this.selectedATAbleInfo.studentsInfo.push(Object.assign(baseInfo, additionalInfo));
        }
        console.log(this.selectedATAbleInfo.studentsInfo);
      })
    },
    //编辑按钮
    handleStudentEdit(row){
      this.selectedStudent = JSON.parse(JSON.stringify(row));
      this.dialogEditATableInfo = true;
    },
    //从活动表中删除学生
    handleStudentDelete(row){
      request.delete(`additionalTable/deleteStudentFromTable/${this.user.uid}/${this.selectedATAbleInfo.tableName}/${row.sid}`).then(res=>{
        if(res.code==0){
          this.$message.success(res.message);
          //避免重新请求，本地删除该学生
          let studentsInfo = this.selectedATAbleInfo.studentsInfo;
          studentsInfo.map((val, i) => {
            if(val.sid == row.sid){
              studentsInfo.splice(i, 1);
            }
          });
          this.selectedATAbleInfo.studentsInfo = studentsInfo;
        }
        else {
          this.$message.error(res.message);
        }
      })
    },
    submitStudentEdit(){
      let data = {};
      data["sid"] = this.selectedStudent["sid"];
      for(let value of this.selectedATAbleInfo.additionalColumns.values()){
        data[value] = this.selectedStudent[value];
      }
      console.log(data);
      request.post(`additionalTable/updateTable/${this.user.uid}/${this.selectedATAbleInfo.tableName}`,data).then(res=>{
        this.$message.success("success");
        this.dialogEditATableInfo=false;
      });
      this.getStudentsInfoFromTable();
    },
    //删除活动表
    handleATableDelete(row){
      console.log(row.tableName);
      request.delete(`additionalTable/deleteTable/${this.user.uid}/${row.tableName}`).then(res=>{
        if(res.code==0){
          this.$message.success(res.message);
          //避免重新请求，本地删除该表
          let aTables = this.additionalTables;
          aTables.map((val, i)=>{
            if(val.tableName == row.tableName){
              aTables.splice(i, 1);
            };
          })
          this.additionalTables = aTables;
        }
        else {
          this.$message.error(res.message);
        }
      })
    }
  }
}
</script>

<style scoped>

.round {
  border-radius: 10px;
}

.fontFamily {
  font-family: 'ZCOOL XiaoWei', serif;
}

.fontFamily /deep/ .el-input__inner{
  font-family: 'ZCOOL XiaoWei', serif;
}

</style>