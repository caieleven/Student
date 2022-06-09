<template>
  <div class="body">
        <el-main class="main">
          <div align="center">
<!--          搜索功能-->
          <div class="mg-10">
            <el-card class="round cardStandard" style="text-align: left">
            <el-input class="fontFamily" style="width: 200px" placeholder="请输入姓名" v-model="studentName"></el-input>
            <el-input style="width: 200px" placeholder="请输入学生来源" class="ml-5 fontFamily" v-model="background"></el-input>
            <el-input style="width: 200px" placeholder="请输入生源地" class="ml-5 fontFamily" v-model="originPlace"></el-input>
            <el-input style="width: 200px" placeholder="请输入宿舍楼" class="ml-5 fontFamily" v-model="dormitory"></el-input>
            <el-button class="round fontFamily" style="margin-left: 15px" type="primary" @click="search">搜索</el-button>
            <el-button class="ml-5 round fontFamily" type="warning" @click="reset">重置</el-button>
            </el-card>
          </div>
          <div>
            <el-card style="display: flex" class="round cardStandard mg-10">
<!--          选择器-->
              <el-select class="fontFamily" v-model="cid" multiple clearable filterable placeholder="选择班级" style="width: 200px" @change="loadStudents">
                <el-option
                    v-for="item in classes"
                    :label="item.name"
                    :value="item.cid"
                />
              </el-select>
              <el-select v-model="status" multiple filterable placeholder="选择政治面貌" class="ml-5 fontFamily" style="width: 200px" @change="loadStudents">
                <el-option
                    v-for="item in statuses"
                    :label="item"
                    :value="item"
                />
              </el-select>
              <el-select v-model="sex" multiple placeholder="请选择性别" class="ml-5 fontFamily" style="width: 200px" @change="loadStudents">
                <el-option :label="'男'" :value="'男'" />
                <el-option :label="'女'" :value="'女'" />
              </el-select>
  <!--          新增 删除-->
              <div style="text-align: left; margin-top: 18px">
                <el-button v-if="user.groupName!='assistant'" type="primary" class="round fontFamily" @click="handleAddToATable">添加至附加表</el-button>
                <el-upload action="http://localhost:8181/student/import" :show-file-list="false" accept="'xlsx'" :on-success="handleImport" style="display: inline-block; margin: 0 10px">
                  <el-button v-if="user.groupName!='assistant'" type="primary" class="round fontFamily">导入</el-button>
                </el-upload>
                <el-button v-if="user.groupName!='assistant'" type="primary" class="round fontFamily" @click="handleExport">导出</el-button>
                <el-button v-if="user.groupName=='admin'" type="primary" class="round fontFamily" @click="handleAdd">新增
                  <el-icon>
                    <Plus/>
                  </el-icon>
                </el-button>
                <el-popconfirm v-if="user.groupName=='admin'"
                    confirm-button-text="是的"
                    cancel-button-text="取消"
                    icon="InfoFilled"
                    icon-color="#626AEF"
                    title="确定要删除选中的对象吗?"
                    @confirm="delBatch"
                >
                  <template #reference>
                    <el-button type="danger" class="round fontFamily">删除<el-icon><Minus/></el-icon></el-button>
                  </template>
                </el-popconfirm>
              </div>
            </el-card>
          </div>
<!--          Table-->
            <el-card style="width: fit-content" class="round">
              <el-table :data="tableData" ref="multipleTable" style="width: fit-content" border stripe :row-key="getRowKeys" @selection-change="handleSelectionChange" @expand-change="showStudentDetail">
                <el-table-column type="selection" :reserve-selection=true ></el-table-column>
                <el-table-column type="expand">
                  <template v-slot="props">
                    <el-card class="mg-10-10">
                    <el-form label-position="left" inline style="margin-left: 20px">
                      <el-form-item label="姓名："  class="item">
                        <span>{{ props.row.name }}</span>
                      </el-form-item>
                      <el-form-item label="学号：" class="item">
                        <span>{{ props.row.sid }}</span>
                      </el-form-item>
                      <el-form-item label="性别：">
                        <span>{{ props.row.sex }}</span>
                      </el-form-item>
                      <el-form-item label="班级：">
                        <span>{{ props.row.className }}</span>
                      </el-form-item>
                      <br />
                      <el-form-item label="政治面貌：">
                        <span>{{ props.row.status }}</span>
                      </el-form-item>
                      <el-form-item label="电话号码：">
                        <span>{{ props.row.tel }}</span>
                      </el-form-item>
                      <el-form-item label="邮箱：">
                        <span>{{ props.row.email }}</span>
                      </el-form-item>
                      <br />
                      <el-form-item label="宿舍楼号："  class="item">
                        <span>{{ props.row.dormitory }}</span>
                      </el-form-item>
                      <el-form-item label="室号：">
                        <span>{{ props.row.bedroom }}</span>
                      </el-form-item>
                      <el-form-item label="床号：">
                        <span>{{ props.row.bed }}</span>
                      </el-form-item>
                      <br />
                      <el-form-item label="入学日期：">
                        <span>{{ props.row.admissionDate }}</span>
                      </el-form-item>
                      <el-form-item label="出生日期：">
                        <span>{{ props.row.birth }}</span>
                      </el-form-item>
                      <el-form-item label="来源：">
                        <span>{{ props.row.background }}</span>
                      </el-form-item>
                    </el-form>
                    </el-card>
                  </template>
                </el-table-column>
                <el-table-column prop="name" label="姓名" width="120"/>
                <el-table-column prop="sid" label="学号" width="120"/>
                <el-table-column prop="sex" label="性别" width="50"/>
                <el-table-column prop="className" label="班级" width="200"/>
                <el-table-column prop="major" label="专业" width="200"/>
                <el-table-column v-if="user.groupName != 'assistant'" label="操作" width="200" align="center">
  <!--                操作权限，辅导员可编辑，助手无操作权限-->
                  <template v-slot="scope">
                    <el-button v-if="['admin', 'counsellor'].indexOf(user.groupName) > -1 " type="warning" class="round fontFamily" @click="handleEdit(scope.row)">编辑<el-icon><EditPen/></el-icon></el-button>
                    <el-popconfirm
                        confirm-button-text="是的"
                        cancel-button-text="取消"
                        icon="InfoFilled"
                        icon-color="#626AEF"
                        title="确定要删除吗?"
                        @confirm="del(scope.row.sid)"
                    >
                      <template #reference>
                        <el-button v-if="user.groupName=='admin'" type="danger" class="round fontFamily" slot="reference">删除<el-icon><Delete/></el-icon></el-button>
                      </template>
                    </el-popconfirm>

                  </template>
                </el-table-column>
              </el-table>
            </el-card>
            <div class="pd-10">
              <el-card class="cardStandard round">
                <el-pagination
                    v-model:currentPage="pageNum"
                    v-model:page-size="pageSize"
                    :page-sizes="[5, 10, 20, 50]"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="totalNum"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :hide-on-single-page=true
                    class="round fontFamily"
                />
              </el-card>
            </div>

<!--          新增对话框-->
          <el-dialog title="用户信息" v-model="dialogAddFormVisible" width="50%" class="round fontFamily">
            <el-form label-width="80px" style="text-align: center" :inline="true">
              <el-form-item label="姓名">
                <el-input v-model="form.name" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="学号">
                <el-input v-model="form.sid" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="班级">
                <el-select class="fontFamily" v-model="form.classId" clearable filterable placeholder="选择班级" style="width: 200px">
                  <el-option
                      v-for="item in classes"
                      :label="item.name"
                      :value="item.cid"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="性别" style="margin-left: 90px">
                <el-radio-group v-model="form.sex">
                  <el-radio label="男" />
                  <el-radio label="女" />
                </el-radio-group>
              </el-form-item>
              <el-form-item label="手机号码">
                <el-input v-model="form.tel" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="form.email" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="生源地">
                <el-input v-model="form.origin" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="政治面貌">
                <el-select v-model="form.status" filterable placeholder="选择政治面貌" class="ml-5 fontFamily" style="width: 200px">
                  <el-option
                      v-for="item in statuses"
                      :label="item"
                      :value="item"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="民族">
                <el-input v-model="form.nation" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="宿舍楼号">
                <el-input v-model="form.dormitory" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="室号">
                <el-input v-model="form.bedroom" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="床号">
                <el-input v-model="form.bed" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="入学日期">
                <el-date-picker
                    v-model="form.admissionDate"
                    type="date"
                    placeholder="请选择入学日期"
                    :default-value="new Date(2019, 9, 1)"
                />
              </el-form-item>
              <el-form-item label="出生日期">
                <el-date-picker
                    v-model="form.birth"
                    type="date"
                    placeholder="请选择出生日期"
                    :default-value="new Date(2000, 1, 1)"
                />
              </el-form-item>
              <el-form-item label="来源">
                <el-input v-model="form.background" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer" style="text-align: center">
              <el-button class="round fontFamily" @click="dialogAddFormVisible = false">取 消</el-button>
              <el-button type="primary" class="round fontFamily" @click="add">确 定</el-button>
            </div>
          </el-dialog>

<!--          编辑对话框-->
          <el-dialog title="用户信息" v-model="dialogEditFormVisible" width="50%">
            <el-form label-width="80px" style="text-align: center" :inline="true">
              <el-form-item label="姓名">
                <el-input v-model="form.name" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="学号">
                <el-input v-model="form.sid" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="班级">
                <el-select class="fontFamily" v-model="form.classId" clearable filterable placeholder="选择班级" style="width: 200px">
                  <el-option
                      v-for="item in classes"
                      :label="item.name"
                      :value="item.cid"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="性别" style="margin-left: 90px">
                <el-radio-group v-model="form.sex">
                  <el-radio label="男" />
                  <el-radio label="女" />
                </el-radio-group>
              </el-form-item>
              <el-form-item label="手机号码">
                <el-input v-model="form.tel" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="form.email" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="生源地">
                <el-input v-model="form.origin" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="政治面貌">
                <el-select v-model="form.status" filterable placeholder="选择政治面貌" class="ml-5 fontFamily" style="width: 200px">
                  <el-option
                      v-for="item in statuses"
                      :label="item"
                      :value="item"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="民族">
                <el-input v-model="form.nation" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="宿舍楼号">
                <el-input v-model="form.dormitory" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="室号">
                <el-input v-model="form.bedroom" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="床号">
                <el-input v-model="form.bed" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="入学日期">
                <el-date-picker
                    v-model="form.admissionDate"
                    type="date"
                    placeholder="请选择入学日期"
                    :default-value="new Date(2019, 9, 1)"
                />
              </el-form-item>
              <el-form-item label="出生日期">
                <el-date-picker
                    v-model="form.birth"
                    type="date"
                    placeholder="请选择出生日期"
                    :default-value="new Date(2000, 1, 1)"
                />
              </el-form-item>
              <el-form-item label="来源">
                <el-input v-model="form.background" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer" style="text-align: center">
              <el-button class="round fontFamily" @click="dialogEditFormVisible = false">取 消</el-button>
              <el-button type="primary" class="round fontFamily" @click="edit">确 定</el-button>
            </div>
          </el-dialog>

<!--          向附加表中添加学生  -->
            <el-dialog title="请选择表" v-model="dialogAddToAdditionalTable" width="40%">
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
                <el-table-column property="assistantName" label="助手名" />
              </el-table>
              <div style="margin-top: 20px">
                <el-button @click="addSidsIntoATable" type="success">确认添加</el-button>
              </div>
            </el-dialog>
          </div>
        </el-main>
  </div>
</template>

<script>
import request from "@/utils/request";
import Aside from "@/components/Aside";
import Header from "@/components/Header";
import { InfoFilled } from '@element-plus/icons-vue';

const changeList = [];
export default {
  name: "Student",
  components: {
    Aside,
    Header,
    InfoFilled,
  },
  data() {
    return {
      tableData: [],
      additionalTables: [
        // {
        //   tableName:"",
        //   counsellorName: "",
        //   counsellorId: null,
        //   assistantsName: ""
        // }
      ],
      addToAtableObject: {

      },
      arr: [], //sid
      totalNum: 0,  //数据总数
      pageSize: 10,
      pageNum: 1,
      className: [],
      classes: {},
      statuses: ["党员", "共青团员", "群众"],


      user: {}, //当前登录的用户
      multipleSelection: [], //选择的数据
      dialogAddFormVisible: false, //新增对话框显示
      dialogEditFormVisible: false, //编辑对话框显示
      dialogAddToAdditionalTable: false,
      form: {}, //新增表单
      //以下信息用以查询中的body
      studentName: "", //学生姓名
      background: "", //学生来源
      originPlace: "", //生源地
      dormitory: "", //宿舍楼
      cid: [], //班号
      sex: [], //性别
      status: [], //政治面貌
    }
  },
//利用计算属性计算changList
//   computed: {
//     changeList() {
//       const { multipleSelection } = this
//       //比对组件得到的选中数据与changList
//       //changList中不存在则push进去
//       multipleSelection.map((item) => {
//         const { sid } = item
//         const exit = changeList.findIndex(item => item.sid === sid) >= 0
//
//         if (!exit) {
//           changeList.push({ sid, state: true })
//         }
//       })
//       return changeList
//     }
//   },

  created() {

    this.user=JSON.parse(localStorage.getItem("user"));
    this.loadStudents();
    this.loadClasses();
    this.getAdditionalTables();

  },
  methods: {
    loadStudents() {
      const  data = {
          "sex": this.sex,
          "name": this.studentName,
          "status": this.status,
          "cid":this.cid,
          "background": this.background,
          "origin": this.originPlace,
          "dormitory":this.dormitory
      }
      request.post(`student/queryPage/${this.pageNum}/${this.pageSize}`, data).then(res=>{
        this.tableData = res.data;
        this.totalNum = res.count;
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.loadStudents();
      // if (!this.studentName || !this.sid || !this.cid || !this.status) {
      //   this.search();
      // } else {
      //   this.loadStudents();
      // }
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.loadStudents();
      // if (!this.studentName || !this.sid || !this.cid || !this.status) {
      //   this.search();
      // } else {
      //   this.loadStudents();
      // }
    },
    search() {
      request.get('student/specialStudent', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          studentName: this.studentName,
          sid: this.sid,
          cid: this.cid,
          status: this.status
        }
      }).then(res => {
        this.tableData = res.data;
        this.totalNum = res.count;
      })
    },
    reset() {
      this.studentName = "";
      this.originPlace = "";
      this.dormitory = "";
      this.background = "";
      this.sex = [];
      this.status = [];
      this.cid = []; //清空
      this.loadStudents();
    },
    add() {
      request.post("student", this.form).then(res => {
        if (res) {
          this.$message.success("新增成功");
          this.dialogFormVisible = false;
          this.loadStudents();
          this.loadClasses();
        } else {
          this.$message.error("新增失败");
        }
        this.dialogAddFormVisible = false;
      })
    },
    edit() {
      request.put("student", this.form).then(res => {
        if (res) {
          this.$message.success("更新成功");
          this.dialogEditFormVisible = false;
          this.loadStudents();
          this.loadClasses();
        } else {
          this.$message.error("更新失败");
        }
        this.dialogEditFormVisible = false;
      })
    },
    handleAdd() {
      this.dialogAddFormVisible = true;
      this.form = {};
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogEditFormVisible = true;
    },
    del(id) {
      request.delete("student/" + id).then(res => {
        if (res) {
          this.$message.success("删除成功");
          this.loadStudents();
        } else {
          this.$message.error("删除失败");
        }
      })
    },
    delBatch() {
      if (!this.multipleSelection.length) {
        this.$message.warning("请选择数据！");
        return
      } else {
        request.post("student/deleteBatch", this.multipleSelection).then(res => {
          if (res) {
            this.$message.success("批量删除成功");
            this.loadStudents();
          } else {
            this.$message.error("批量删除失败");
          }
        })
      }
    },
    loadClasses() {
      request.get('class/allCidAndName').then(res => {
        this.classes = res;
      })
    },
    getRowKeys(row){
      // console.log(row.sid);
      return row.sid;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val.map(v => v.sid)  //对象数组转换为sid数组
      // const _this = this;
      // val.forEach(function (item){
      //   _this.multipleSelection.push(item.sid);
      // })
      // for (let i in val){
      //   this.multipleSelection.push(val[i].sid);
      // }
      // // this.multipleSelection = val;
      // // console.log(val)
      // console.log(this.multipleSelection)
    },
    showStudentDetail() {

    },
    handleAddToATable() {
      this.dialogAddToAdditionalTable = true;
    },
    handleCurrentChangeOfATable(val){
      this.addToAtableObject["tableName"] = val["tableName"];
      this.addToAtableObject["uid"] = val["counsellorId"];
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
    addSidsIntoATable(){
      this.addToAtableObject["sids"] = this.multipleSelection;
      request.post("additionalTable/addStudentsToTable",this.addToAtableObject).then(res=>{
        if(res.code==0){
          this.$message.success(res.message);
        }
        else{
          this.$message.error(res.message);
        }
        this.dialogAddToAdditionalTable = false;
      })
    },
    handleImport() {
      this.$message.success("导入成功");
      this.loadStudents();
    },
    handleExport() {
      window.open("http://localhost:8181/student/export");
    }

    //行选则时的处理逻辑
    // handleRowClick(val) {
    //   const { multipleSelection } = this
    //   const { sid } = val
    //   const state = multipleSelection.findIndex(item => item.sid=== sid) >= 0
    //
    //   this.checkChooseState(sid)
    //   this.$refs.multipleTable.toggleRowSelection(val, !state)
    // },
    // //此处关键，由于直接点击checkbox无法直接获取当前行的数据
    // //于是我禁用checkbox选择，采用点击checkbox所在行来获取当前行数据
    // checkSelectable() {
    //   return false
    // },
    // //此处关键 判断变化的选项是否存在于changList中
    // //存在则修改状态为false
    // //无需在这里添加，我们有计算属性帮我们处理
    // checkChooseState(id) {
    //   changeList.map((item) => {
    //     const { sid } = item
    //     if (id === sid) {
    //       item.state = false
    //     }
    //   })
    // }
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
}

.round {
  border-radius: 10px;
}
.round /deep/ .el-dialog__header {
  border-radius: 10px;
}
.round /deep/ .el-dialog__body {
  border-radius: 10px;
}

.fontFamily {
  font-family: 'ZCOOL XiaoWei', serif;
}

.fontFamily /deep/ .el-input__inner{
  font-family: 'ZCOOL XiaoWei', serif;
}

.cardStandard{
  width: 1026px;
}

</style>