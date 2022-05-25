import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import "./assets/global.css"
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import request from "./utils/request";
import axios from "axios";

const problist = {
    sid: '学号',
    name: '姓名',
    sex: '性别',
    tel: '手机号码', //手机号码
    email: '邮箱',
    className: '班级',
    major: '专业',
    department: '学院',  //
    counsellor: '辅导员',  //
    origin: '生源地',  //
    status: '政治面貌',  //
    nation: '民族', //
    dormitory: '宿舍楼号',   //
    bedroom: '寝号',    //
    bed: '床号',    //
    admissionDate: '入学日期',    //
    birth: '出生日期',    //
    background: '来源',    //
}

const app = createApp(App);
app.config.globalProperties.$problist = problist;
// app.config.globalProperties.axios=axios;

// app.prototype.request=request
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
// app.prototype.$axios=axios;
for(let iconName in ElementPlusIconsVue){
    app.component(iconName,ElementPlusIconsVue[iconName])
}

app.use(store).use(router).use(ElementPlus).mount('#app')



