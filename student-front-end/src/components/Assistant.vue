<template>
  <div class="body">
    <el-main class="main">
      <div align="center">
        <!--          搜索功能-->
        <div class="mg-10">
          <el-card class="round cardStandard" style="text-align: left">
            <el-input style="width: 200px" placeholder="请输入uid" v-model="uid"></el-input>
            <!--            <el-input style="width: 200px" placeholder="请输入姓名" class="ml-5" v-model="assistantName"></el-input>-->
            <!--            <el-input style="width: 200px" placeholder="请输入密码" class="ml-5" v-model="password"></el-input>-->
            <el-button class="round" style="margin-left: 15px" type="primary" @click="search">搜索</el-button>
            <el-button class="ml-5 round" type="warning" @click="reset">重置</el-button>
            <el-button type="primary" style="margin-left: 20px" @click="handleAdd">新增<el-icon><Plus/></el-icon></el-button>
            <el-popconfirm
                confirm-button-text="是的"
                cancel-button-text="取消"
                icon="InfoFilled"
                icon-color="#626AEF"
                title="确定要删除选中的对象吗?"
                @confirm="delBatch"
            >
              <template #reference>
                <el-button type="danger">删除<el-icon><Minus/></el-icon></el-button>
              </template>
            </el-popconfirm>
            <el-button type="success" class="round" @click="handleAddToATable">添加至活动表</el-button>
          </el-card>
        </div>
        <!--          Table-->
        <el-card class="cardStandard round">
          <el-table :data="tableData" ref="multipleTable" style="width: fit-content" border stripe :row-key="getRowKeys" @selection-change="handleSelectionChange">
            <el-table-column type="selection" :reserve-selection=true ></el-table-column>
            <el-table-column prop="uid" label="uid" width="80"/>
            <el-table-column prop="username" label="姓名" width="120"/>
<!--            <el-table-column prop="password" label="密码" width="120"/>-->
            <el-table-column prop="permission" label="权限码" width="40"/>
            <el-table-column prop="fName" label="所属教师" width="100"/>
            <el-table-column prop="tableNames" label="活动表" width="200"/>
            <el-table-column label="操作" width="200" align="center">
              <template v-slot="scope">
                <el-button type="warning" @click="handleEdit(scope.row)">编辑<el-icon><EditPen/></el-icon></el-button>
                <el-popconfirm
                    confirm-button-text="是的"
                    cancel-button-text="取消"
                    icon="InfoFilled"
                    icon-color="#626AEF"
                    title="确定要删除吗?"
                    @confirm="del(scope.row)"
                >
                  <template #reference>
                    <el-button type="danger" slot="reference">删除<el-icon><EditPen/></el-icon></el-button>
                  </template>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </el-card>

        <!--        新增对话框-->
        <el-dialog title="用户信息" v-model="dialogAddFormVisible" width="20%">
          <el-form label-width="80px" style="text-align: center">
            <el-form-item label="uid">
              <el-input v-model="form.uid" autocomplete="off" style="width: auto" placeholder="请输入uid"></el-input>
            </el-form-item>
            <el-form-item label="姓名">
              <el-input v-model="form.username" autocomplete="off" style="width: auto" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="form.password" autocomplete="off" style="width: auto" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item label="权限码">
              <el-radio-group v-model="form.permission">
                <el-radio label="1" />
                <el-radio label="0" />
              </el-radio-group>
            </el-form-item>
            <el-form-item label="用户组">
              <el-select v-model="form.groupId" style="width: auto" placeholder="请选择用户组">
                <el-option v-for="i in 3" :value="i"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer" style="text-align: center">
            <el-button @click="dialogAddFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="add">确 定</el-button>
          </div>
        </el-dialog>

        <!--        编辑对话框-->
        <el-dialog title="用户信息" v-model="dialogEditFormVisible" width="20%">
          <el-form label-width="80px" style="text-align: center">
            <el-form-item label="uid">
              <el-input v-model="form.uid" autocomplete="off" style="width: auto" placeholder="请输入uid"></el-input>
            </el-form-item>
            <el-form-item label="姓名">
              <el-input v-model="form.username" autocomplete="off" style="width: auto" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="form.password" autocomplete="off" style="width: auto" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item label="权限码">
              <el-radio-group v-model="form.permission">
                <el-radio label="1" />
                <el-radio label="0" />
              </el-radio-group>
            </el-form-item>
            <el-form-item label="用户组">
              <el-select v-model="form.groupId" style="width: auto" placeholder="请选择用户组">
                <el-option v-for="i in 3" :value="i"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer" style="text-align: center">
            <el-button @click="dialogEditFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="edit">确 定</el-button>
          </div>
        </el-dialog>


        <!--        添加至活动表-->
        <el-dialog title="活动表" v-model="dialogAddToAdditionalTable" width="50%">
          <!--    新增表展示-->
          <div>
            <el-table
                ref="additionalTablesInDialog"
                :data="additionalTables"
                highlight-current-row
                style="width: 100%"
                @row-click="handleCurrentChangeOfATable"
            >
              <el-table-column type="index" width="50" />
              <el-table-column property="tableName" label="表名" width="120" />
              <el-table-column property="counsellorName" label="教师名" width="120" />
              <el-table-column property="assistantName" label="助手名" width="240" />
            </el-table>
          </div>

<!--          按钮-->
          <div slot="footer" class="dialog-footer" style="text-align: center">
            <el-button @click="dialogAddToAdditionalTable = false">取 消</el-button>
            <el-button type="primary" @click="addSelectedToAdditionalTable">确 定</el-button>
          </div>
        </el-dialog>
      </div>
    </el-main>
  </div>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";
import {InfoFilled} from "@element-plus/icons-vue";
import request from "@/utils/request";

export default {
  name: "Assistant",
  components: {
    Aside,
    Header,
    InfoFilled,
  },
  data() {
    return {
      uid: "",
      assistantName: "",
      password: "",
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      totalNum: 0,
      form: {},
      dialogAddFormVisible: false,
      dialogEditFormVisible: false,
      //以下四个数据用于给助手分配活动表
      dialogAddToAdditionalTable: false,
      multipleSelection: [],
      additionalTables:[],
      selectedTableName:{}
    }
  },
  created() {
    this.user=JSON.parse(localStorage.getItem("user"));
    this.loadAssistant();
  },
  methods: {
    loadAssistant() {
      request.get("/user/getAssistants", {
        params: {
          uid: this.user.uid
        }
      }).then(res => {
        this.tableData = res.data;

      })
    },
    search() {
      // this.tableData = [];
      // if (!this.uid) {
      //   this.$message.warning("uid不能为空");
      //   return
      // } else {
      //   request.get("user/getUser", {
      //     params: {
      //       uid: this.uid
      //     }
      //   }).then(res => {
      //     if (res.data.group)
      //     this.tableData.push(res.data);
      //   })
      // }
    },
    reset() {
      this.uid = "";
      this.loadAssistant();
    },
    handleAdd() {
      this.dialogAddFormVisible = true;
      this.form = {};
    },
    add() {
      this.form.fid = this.user.uid;
      request.post("user/addUser", this.form).then( res => {
        if (res) {
          this.$message.success("新增成功");
          this.dialogFormVisible = false;
          this.loadAssistant();
        } else {
          this.$message.error("新增失败");
        }
        this.dialogAddFormVisible = false;
      })
    },
    edit() {
      request.put("user/updateUser", this.form).then(res => {
        if (res) {
          this.$message.success("更新成功");
          this.dialogEditFormVisible = false;
          this.loadAssistant();
        } else {
          this.$message.error("更新失败");
        }
        this.dialogEditFormVisible = false;
      })
    },
    delBatch() {

    },
    getRowKeys(row) {
      return row.uid;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val.map(v => v.uid)  //对象数组转换为uid数组
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogEditFormVisible = true;
    },
    del(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.form.permission = 0;
      this.edit();
    },
    handleSizeChange() {

    },
    handleCurrentChange() {

    },

    // 以下三个方法用于给助手分配活动表
    handleAddToATable() {
      //获取表数据
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
      this.dialogAddToAdditionalTable = true;
    },
    addSelectedToAdditionalTable() {
      let data = {};
      data.assistantId = this.multipleSelection;
      data.counsellorId = this.user.uid;
      data.tableName = this.selectedTableName;
      request.post("additionalTable/addAssistantToTable", data).then(res => {
        if (res.code == 0) {
          this.dialogAddToAdditionalTable = false;
          this.$message.success("success");
        }
        else{
          this.$message.faile(res.message);
        }
      })
    },
    //选择活动表，单选
    handleCurrentChangeOfATable(val){
      this.selectedTableName = val.tableName;
      console.log(this.selectedTableName);
    }
  }
}
</script>

<style lang="css" scoped>
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