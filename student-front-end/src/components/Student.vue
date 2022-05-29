<template>
  <div>
        <el-main>
<!--          搜索功能-->
          <div style="padding: 10px 0">
            <el-input style="width: 200px" placeholder="请输入姓名" v-model="studentName"></el-input>
            <el-input style="width: 200px" placeholder="请输入学生来源" class="ml-5" v-model="background"></el-input>
            <el-input style="width: 200px" placeholder="请输入生源地" v-model="originPlace"></el-input>
            <el-input style="width: 200px" placeholder="请输入宿舍楼" v-model="dormitory"></el-input>
            <el-button class="ml-5" type="primary" @click="search" round>搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset" round>重置</el-button>
          </div>
<!--          选择器-->
          <div class="pd-10">
            <el-select v-model="cid" multiple clearable filterable placeholder="选择班级" style="width: 200px" @change="loadStudents">
              <el-option
                  v-for="item in classes"
                  :label="item.name"
                  :value="item.cid"
              />
            </el-select>
            <el-select v-model="status" multiple filterable placeholder="选择政治面貌" class="ml-5" style="width: 200px" @change="loadStudents">
              <el-option
                  v-for="item in statuses"
                  :label="item"
                  :value="item"
              />
            </el-select>
            <el-select v-model="sex" multiple placeholder="性别" style="width: 90px" @change="loadStudents">
              <el-option :label="'男'" :value="'男'" />
              <el-option :label="'女'" :value="'女'" />
            </el-select>
          </div>
<!--          新增 删除-->
          <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增
              <el-icon>
                <Plus/>
              </el-icon>
            </el-button>
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
          </div>
          <el-scrollbar>
            <!--          固定首行和首列-->
            <el-table :data="tableData" ref="multipleTable" style="width: 100%" border stripe :row-key="getRowKeys" @selection-change="handleSelectionChange" @expand-change="showStudentDetail">
              <el-table-column type="selection" :reserve-selection=true ></el-table-column>
              <el-table-column type="expand">
                <template v-slot="props">
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
                </template>
              </el-table-column>
              <el-table-column prop="name" label="姓名" width="120"/>
              <el-table-column prop="sid" label="学号" width="120"/>
              <el-table-column prop="sex" label="性别" width="50"/>
              <el-table-column prop="className" label="班级" width="200"/>
              <el-table-column prop="major" label="专业" width="200"/>
              <el-table-column prop="tel" label="电话号码" width="200"/>
              <el-table-column prop="email" label="邮箱" width="200"/>
              <el-table-column v-if="user.groupName != 'assistant'" label="操作" width="200" align="center">
<!--                操作权限，辅导员可编辑，助手无操作权限-->
                <template v-slot="scope">
                  <el-button v-if="['admin', 'counsellor'].indexOf(user.groupName) > -1 " type="info" @click="handleEdit(scope.row)">编辑<el-icon><EditPen/></el-icon></el-button>
<!--                  有错误，待解决-->
<!--                  <el-popconfirm-->
<!--                      confirm-button-text="是的"-->
<!--                      cancel-button-text="取消"-->
<!--                      icon="InfoFilled"-->
<!--                      icon-color="#626AEF"-->
<!--                      title="确定要删除吗?"-->
<!--                      @confirm="del(scope.row.sid)"-->
<!--                  >-->
<!--                    <template #reference>-->
<!--                    </template>-->
<!--                  </el-popconfirm>-->
                  <el-button v-if="user.groupName=='admin'" type="danger" slot="reference">删除<el-icon><Delete/></el-icon></el-button>
                </template>
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

<!--          新增对话框-->
          <el-dialog title="用户信息" v-model="dialogAddFormVisible" width="20%">
            <el-form label-width="80px" style="text-align: center">
              <el-form-item label="姓名">
                <el-input v-model="form.name" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="学号">
                <el-input v-model="form.sid" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="班级">
                <el-input v-model="form.classId" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="性别">
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
<!--              <el-form-item label="生源地">-->
<!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="政治面貌">-->
<!--                <el-input v-model="form.sid" autocomplete="off"></el-input>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="民族">-->
<!--                <el-input v-model="form.sid" autocomplete="off"></el-input>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="宿舍楼号">-->
<!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="室号">-->
<!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="床号">-->
<!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="入学日期">-->
<!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
<!--              </el-form-item>-->
<!--              <el-form-item label="出生日期">-->
<!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
<!--              </el-form-item>-->
              <el-form-item label="来源">
                <el-input v-model="form.background" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer" style="text-align: center">
              <el-button @click="dialogAddFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="add">确 定</el-button>
            </div>
          </el-dialog>

<!--          编辑对话框-->
          <el-dialog title="用户信息" v-model="dialogEditFormVisible" width="20%">
            <el-form label-width="80px" style="text-align: center">
              <el-form-item label="姓名">
                <el-input v-model="form.name" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="学号">
                <el-input v-model="form.sid" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="班级">
                <el-input v-model="form.classId" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
              <el-form-item label="性别">
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
              <!--              <el-form-item label="生源地">-->
              <!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
              <!--              </el-form-item>-->
              <!--              <el-form-item label="政治面貌">-->
              <!--                <el-input v-model="form.sid" autocomplete="off"></el-input>-->
              <!--              </el-form-item>-->
              <!--              <el-form-item label="民族">-->
              <!--                <el-input v-model="form.sid" autocomplete="off"></el-input>-->
              <!--              </el-form-item>-->
              <!--              <el-form-item label="宿舍楼号">-->
              <!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
              <!--              </el-form-item>-->
              <!--              <el-form-item label="室号">-->
              <!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
              <!--              </el-form-item>-->
              <!--              <el-form-item label="床号">-->
              <!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
              <!--              </el-form-item>-->
              <!--              <el-form-item label="入学日期">-->
              <!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
              <!--              </el-form-item>-->
              <!--              <el-form-item label="出生日期">-->
              <!--                <el-input v-model="form.name" autocomplete="off"></el-input>-->
              <!--              </el-form-item>-->
              <el-form-item label="来源">
                <el-input v-model="form.background" autocomplete="off" style="width: auto"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer" style="text-align: center">
              <el-button @click="dialogEditFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="edit">确 定</el-button>
            </div>
          </el-dialog>

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
    this.loadStudents();
    this.loadClasses();
    this.user=JSON.parse(localStorage.getItem("user"));
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
      this.sex = "";
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
          this.dialogFormVisible = false;
          this.loadStudents();
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

<style>
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
/deep/ .el-table--enable-row-transition .el-table__body td {
  cursor: pointer;
}
/deep/ .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner:after{
  border-color: #fff;
}
/deep/ .el-checkbox__input.is-disabled.is-checked .el-checkbox__inner{
  background-color: #409eff;
}

</style>