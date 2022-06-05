<template>
  <div class="body">
    <el-main class="main">
      <div align="center">
        <!--          搜索功能-->
        <div class="mg-10">
          <el-card class="round cardStandard" style="text-align: left">
            <el-input style="width: 200px" placeholder="请输入uid" v-model="uid"></el-input>
<!--            <el-input style="width: 200px" placeholder="请输入姓名" class="ml-5" v-model="counsellorName"></el-input>-->
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
          </el-card>
        </div>
        <!--          Table-->
        <el-card class="cardStandard round">
          <el-table :data="tableData" ref="multipleTable" style="width: fit-content" border stripe :row-key="getRowKeys" @selection-change="handleSelectionChange">
            <el-table-column type="selection" :reserve-selection=true ></el-table-column>
            <el-table-column prop="uid" label="uid" width="120"/>
            <el-table-column prop="username" label="姓名" width="120"/>
            <el-table-column prop="password" label="密码" width="120"/>
            <el-table-column prop="permission" label="permission" width="150"/>
            <el-table-column prop="fName" label="fName" width="150"/>
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
<!--        <div class="pd-10">-->
<!--          <el-card class="cardStandard round">-->
<!--            <el-pagination-->
<!--                v-model:currentPage="pageNum"-->
<!--                v-model:page-size="pageSize"-->
<!--                :page-sizes="[5, 10, 20, 50]"-->
<!--                layout="total, sizes, prev, pager, next, jumper"-->
<!--                :total="totalNum"-->
<!--                @size-change="handleSizeChange"-->
<!--                @current-change="handleCurrentChange"-->
<!--                :hide-on-single-page=true-->
<!--            />-->
<!--          </el-card>-->
<!--        </div>-->

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
      password: "",
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      totalNum: 0,
      form: {},
      dialogAddFormVisible: false,
      dialogEditFormVisible: false,
      multipleSelection: []
    }
  },
  created() {
    this.user=JSON.parse(localStorage.getItem("user"));
    this.loadCounsellor();
  },
  methods: {
    loadCounsellor() {
      request.get("/user/getCounsellors", {
        params: {
          uid: this.user.uid
        }
      }).then(res => {
          this.tableData = res.data;

      })
    },
    search() {
      this.tableData = [];
      if (!this.uid) {
        this.$message.warning("uid不能为空");
        return
      } else {
        request.get("user/getCounsellor", {
          params: {
            uid: this.uid
          }
        }).then(res => {
          this.tableData.push(res.data);
        })
      }
    },
    reset() {
      this.uid = "";
      this.loadCounsellor();
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
          this.loadCounsellor();
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
          this.loadCounsellor();
        } else {
          this.$message.error("更新失败");
        }
        this.dialogEditFormVisible = false;
      })
    },
    delBatch() {
      // if (!this.multipleSelection.length) {
      //   this.$message.warning("请选择数据！");
      //   return
      // } else {
      //   for(item in this.multipleSelection) {
      //     this.form.uid = item;
      //     this.form.permission = 0;
      //     this.edit();
      //   }
      //   this.loadCounsellor();
      //   })
      // }
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