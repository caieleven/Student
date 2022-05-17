<template>
  <div class="loginbody">
    <div class="mylogin">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="UserFilled" v-model="user.username"><el-icon><UserFilled /></el-icon></el-input>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="Key" show-password v-model="user.password"><el-icon><Key /></el-icon></el-input>

      <div style="margin: 50px 0; text-align: center">
          <el-button color="#792EE6" size="large" :dark="isDark" round @click="login">登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginView",
  data(){
    return {
      user: {}
    }
  },
  methods:{
    login(){

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
  background-image: linear-gradient(to bottom right, #FC4668, #3F5EFB);
  overflow: auto;
  border: 0;
  position: fixed;
  top: 0;
  left: 0;
  /*overflow: scroll;*/
  /*overflow-y: hidden;*/
  /*overflow-x: hidden;*/

}

.login {
  width: 100vw;
  padding: 0;
  margin: 0;
  height: 100vh;
  font-size: 16px;
  background-position: left top;
  background-color: #242645;
  color: #fff;
  font-family: "Source Sans Pro";
  position: relative;
  background-image: linear-gradient(to bottom right, #FC4668, #3F5EFB);
  background-repeat: no-repeat;
  background-size: 100% 100%;
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
  box-shadow: -15px 15px 15px rgba(6, 17, 47, 0.7);
  opacity: 1;
  background: linear-gradient(
      230deg,
      rgba(53, 57, 74, 0) 0%,
      rgb(0, 0, 0) 100%
  );
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
</style>