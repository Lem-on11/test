import http from "@/http";
import { UpParm } from "./HomeModel";
//统计总条数
export const getTotalApi = ()=>{
    return http.get("/api/home/getTotal")
}
//物资充足
export const moreInfoApi = ()=>{
    return http.get("/api/home/moreInfo")
}
//物资不足
export const lessInforApi = ()=>{
    return http.get("/api/home/lessInfor")
}
//物资分类
export const getTotalCategoryApi = ()=>{
    return http.get("/api/home/getTotalCategory")
}
//刚公告列表
export const getNoticeListApi = ()=>{
    return http.get("/api/home/getNoticeList")
}
//修改密码
export const updatePasswordApi = (parm:UpParm)=>{
    return http.post("/api/home/upPassword",parm)
}   