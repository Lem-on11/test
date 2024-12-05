import http from "@/http";
import {Apply} from '@/api/into/IntoModel'
import { MaterialSendParm } from "./SendModel";
//发放接口
export const sendApi = (parm:any)=>{
    return http.post("/api/send/sendSave",parm)
}
//物资记录列表
export const getIntoListApi = (parm:MaterialSendParm)=>{
    return http.get("/api/send/getSendList",parm)
}
//审核
export const intoApplyApi = (parm:Apply)=>{
    return http.post("/api/send/apply",parm)
}