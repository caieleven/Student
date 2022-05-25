<template>
  <div>
    <p>表操作</p>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增
        <el-icon>
          <Plus/>
        </el-icon>
      </el-button>
    </div>
    <div>
      <el-dialog title="新增表" v-model="dialogAddFormVisible" width="20%" style="text-align: center">
          <el-form :model="newForm" label-width="120px">
            <div style="text-align: center">
            <el-form-item label="表名">
              <el-input v-model="newForm.name" autocomplete="off"></el-input>
            </el-form-item>
            </div>
            <el-form-item label="基本表字段">
              <el-checkbox-group v-model="newForm.checkList" @change="handleCheckChange" label="基本表">
                <el-checkbox v-for="item in probsList" :label="item">
                  {{baseprobs[item]}}
                </el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form>

      </el-dialog>

    </div>


  </div>
</template>

<script>
import {getCurrentInstance} from "vue";

export default {
  name: "AdditionalTable",
  data(){
    return {
      newForm: {}, //新增表单
      dialogAddFormVisible: false, //新增对话框显示
      checkList: []
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
    console.log(this.probsList);
  },
  methods: {
    handleAdd() {
      this.dialogAddFormVisible = true;
      this.newForm = {};
    },
    handleCheckChange(value){
      // console.log(this.checkList)
    }
  }
}
</script>

<style scoped>

</style>