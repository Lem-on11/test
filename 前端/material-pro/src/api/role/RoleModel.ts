//定义角色的数据类型
export type AddRoleModel = {
    type: string,
    roleId: string,
    roleName: string,
    remark: string
}
//定义列表查询的参数类型
export type ListParm = {
    currentPage: number,
    pageSize: number,
    roleName: string,
    total: number
}
export type AssignParm = {
    roleId:string,
    userId:string
}
//保存权限的数据类型
export type SaveAssignParm = {
    roleId:string,
    list:Array<string>
}