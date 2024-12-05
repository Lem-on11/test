import http from "@/http";
import { LoginType } from "./LoginModel";
//生成验证码
export const getImageApi = ()=>{
    return http.post("/api/login/image")
}
//登录
export const loginApi = (parm:LoginType)=>{
    return http.post("/api/login/login",parm)
}
//菜单数据
export const getMenuListApi = (userId:string)=>{
    return http.get("/api/login/getMenuList",{userId:userId})
}
//用户信息
export const getInfoApi = (userId:string)=>{
    return http.get("/api/login/getInfo",{userId:userId})
}
//退出登录
export const loginOutApi = ()=>{
    return http.post("/api/login/loginOut")
}