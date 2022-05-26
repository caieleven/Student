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
      <el-dialog title="新增表" v-model="dialogAddFormVisible" width="50%" center>
          <el-form :model="newForm" label-width="120px">
            <div>
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
            <div>
              <el-form-item v-for="(column, index) in newForm.additionColumns"
                            :key="column.name"
                            :label="'新字段'">
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

    </div>


  </div>
</template>

<script>
import {getCurrentInstance} from "vue";

export default {
  name: "AdditionalTable",
  data(){
    return {
      newForm: {
        checkList: ['sid', 'name'],
        additionColumns: [
          {
            name: "zhiwu"
          }
        ]
      }, //新增表单
      dialogAddFormVisible: false, //新增对话框显示,

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
    },
    handleCheckChange(){
    },
    removeColumn(column){
      const index = this.newForm.additionColumns.indexOf(column);
      if (index != -1){
        this.newForm.additionColumns.splice(index, 1)
      }
    },
    addColumn(){
      this.newForm.additionColumns.push({
        name: ""
      });
    },
    submitForm(){
      console.log(this.newForm)
    }
  }
}
</script>

<style scoped>

</style>