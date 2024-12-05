import http from "@/http";
import { MaterialParm, MaterialType } from "./MaterialModel";
//图片上传
export const uploadImageApi = (parm: object) => {
    return http.upload("/api/upload/uploadImage", parm)
}
//新增
export const addApi = (parm:MaterialType)=>{
    return http.post("/api/materialInfo",parm)
}
//列表
export const getListApi = (parm:MaterialParm)=>{
    return http.get("/api/materialInfo/list",parm)
}
//编辑
export const editApi = (parm:MaterialType)=>{
    return http.put("/api/materialInfo",parm)
}
//删除
export const deleteApi = (infoId:string)=>{
    return http.delete(`/api/materialInfo/${infoId}`)
}