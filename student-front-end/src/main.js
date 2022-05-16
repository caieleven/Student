import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import request from "@/utils/request";
import axios from "axios";

const app = createApp(App);
// app.config.globalProperties.axios=axios;
// app.prototype.request=request;
app.use(store).use(router).use(ElementPlus).mount('#app')

