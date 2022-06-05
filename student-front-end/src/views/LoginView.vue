<template>
  <div class="loginbody">
    <div class="mylogin round">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-input size="default" style="margin: 10px 0" prefix-icon="UserFilled" v-model="uid"><el-icon><UserFilled /></el-icon></el-input>
      <el-input size="default" style="margin: 10px 0" prefix-icon="Key" show-password v-model="password"><el-icon><Key /></el-icon></el-input>

      <div style="margin: 50px 0; text-align: center">
          <el-button color="#DBD4B4" size="large" round @click="login" style="font-family: 'ZCOOL XiaoWei', serif">登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "LoginView",
  data(){
    return {
      uid:"",
      password:"",
    }
  },
  methods:{
    login(){
      request.post("/user/login",{
        uid:this.uid,
        password:this.password
      }).then(res=>{
        if(res.code != 0){
          this.$message.error(res.message);
        }
        else {
          localStorage.setItem("user", JSON.stringify(res.data));
          this.$router.push("/main")
        }
        console.log(res)
      })
    }
  }
}
</script>

<style scoped>
.login-style {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC4668, #3F5EFB);
  overflow: hidden;
}

p {
  font-size: 12px;
  cursor: pointer;
}

.loginbody {
  background-size: contain;
  height: 100vh;
  width: 100%;
  background: #CC95C0;  /* fallback for old browsers */
  background: -webkit-linear-gradient(to top, #7AA1D2, #DBD4B4, #CC95C0);  /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(to top, #7AA1D2, #DBD4B4, #CC95C0); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
  overflow: auto;
  border: 0;
  position: fixed;
  top: 0;
  left: 0;
  /*overflow: scroll;*/
  /*overflow-y: hidden;*/
  /*overflow-x: hidden;*/

}

.mylogin {
  width: 240px;
  height: 280px;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  margin: auto;
  padding: 50px 40px 40px 40px;
  box-shadow: 0 0 30px rgba(6, 17, 47, 0.3);
  opacity: 1;
  background-color: #f1f2f6;
  border-color: #e4e7ed;
}

.inps input {
  border: none;
  color: #fff;
  background-color: transparent;
  font-size: 12px;
}

.submitBtn {
  background-color: transparent;
  color: #39f;
  width: 200px;
}

.round {
  border-radius: 10px;
}
</style>