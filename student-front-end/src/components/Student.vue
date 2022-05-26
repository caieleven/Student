<template>
  <div>
        <el-main>
          <div style="padding: 10px 0">
            <el-input style="width: 200px" placeholder="请输入姓名" v-model="studentName"></el-input>
            <el-input style="width: 200px" placeholder="请输入学号" class="ml-5" v-model="sid"></el-input>
            <el-button class="ml-5" type="primary" @click="search" round>搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset" round>重置</el-button>
          </div>
          <div class="pd-10">
            <el-select v-model="cid" filterable placeholder="选择班级" style="width: 200px" @change="selectByClass">
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
            <el-button type="primary" @click="handleAdd">新增
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
            <el-table :data="tableData" ref="multipleTable" style="width: 100%" border stripe :row-key="getRowKeys" @selection-change="handleSelectionChange">
              <el-table-column type="selection" :reserve-selection=true ></el-table-column>
              <el-table-column fixed prop="name" label="姓名" width="120"/>
              <el-table-column prop="sid" label="学号" width="120"/>
              <el-table-column prop="sex" label="性别" width="50"/>
              <el-table-column prop="className" label="班级" width="200"/>
              <el-table-column prop="major" label="专业" width="200"/>
              <el-table-column prop="tel" label="电话号码" width="200"/>
              <el-table-column prop="email" label="邮箱" width="200"/>
              <el-table-column label="操作" width="200" align="center">
                <template v-slot="scope">
                  <el-button type="info" @click="handleEdit(scope.row)">编辑<el-icon><EditPen/></el-icon></el-button>
                  <el-popconfirm
                      confirm-button-text="是的"
                      cancel-button-text="取消"
                      icon="InfoFilled"
                      icon-color="#626AEF"
                      title="确定要删除吗?"
                      @confirm="del(scope.row.sid)"
                  >
                    <template #reference>
                      <el-button type="danger" slot="reference">删除<el-icon><Delete/></el-icon></el-button>
                    </template>
                  </el-popconfirm>
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
    InfoFilled
  },
  data() {
    return {
      tableData: [],
      arr: [], //sid
      totalNum: 0,  //数据总数
      pageSize: 10,
      pageNum: 1,
      studentName: "", //学生姓名
      sid: "", //学号
      className: [],
      cid: "", //班号
      classes: {},
      user: {}, //当前登录的用户
      multipleSelection: [], //选择的数据
      dialogAddFormVisible: false, //新增对话框显示
      dialogEditFormVisible: false, //编辑对话框显示
      form: {}, //新增表单
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
  },
  methods: {
    loadStudents() {
      request.get('student/page', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data;
        this.totalNum = res.count;
      })
    },
    handleSizeChange(pageSize) {
      console.log(this.studentName)
      this.pageSize = pageSize;
      if (!this.studentName || !this.sid) {
        this.search();
      } else if (!this.cid) {
        this.selectByClass();
      } else {
        this.loadStudents();
      }
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      if (!this.studentName || !this.sid) {
        this.search();
      } else if (!this.cid) {
        this.selectByClass();
      } else {
        this.loadStudents();
      }
    },
    search() {
      request.get('student/specialStudent', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          studentName: this.studentName,
          sid: this.sid
        }
      }).then(res => {
        console.log(res);
        this.tableData = res.data;
        this.totalNum = res.count;
      })

    },
    reset() {
      this.studentName = "";
      this.sid = "";
      this.loadStudents();
    },
    selectByClass() {
      console.log(this.cid)
      request.get("student/selectByClass", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          cid: this.cid,
        }
      }).then(res => {
        this.tableData = res.data;
        this.totalNum = res.count;
      })
    },
    // save() {
    //   request.post("student/exist/" + this.form.sid).then(res => {
    //     if (res) {
    //       request.put("student", this.form).then(res => {
    //         if (res) {
    //           this.$message.success("更新成功");
    //           this.dialogFormVisible = false;
    //           this.loadStudents();
    //         } else {
    //           this.$message.error("更新失败");
    //         }
    //         this.dialogFormVisible = false;
    //       })
    //     } else {
    //       request.post("student", this.form).then(res => {
    //         if (res) {
    //           this.$message.success("新增成功");
    //           this.dialogFormVisible = false;
    //           this.loadStudents();
    //         } else {
    //           this.$message.error("新增失败");
    //         }
    //         this.dialogFormVisible = false;
    //       })
    //     }
    //   })
    // },
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
          this.dialogFormVisible = false;
          this.loadStudents();
        } else {
          this.$message.error("删除失败");
        }
        this.dialogFormVisible = false;
      })
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
      const _this = this;
      val.forEach(function (item){
        _this.multipleSelection.push(item.sid);
      })
      for (let i in val){
        this.multipleSelection.push(val[i].sid);
      }
      // this.multipleSelection = val;
      // console.log(val)
      console.log(this.multipleSelection)
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