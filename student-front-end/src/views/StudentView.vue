<template>
  <div>
    <table>
      <tr>
        <td>学号</td>
        <td>姓名</td>
      </tr>
    </table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "StudentView",
  data(){

  },
  created() {
    axios.get('http://localhost:8181/student/all').then(function (resp) {
      console.log(resp);
    })
  }
}
</script>

<style scoped>

</style>