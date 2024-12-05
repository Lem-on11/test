//定义角色下拉选择数据类型
export type SelectType = {
    value: string,
    label: string
}
//下拉数据列表
export type SelectTypeList = {
    list: SelectType[]
}
//查询列表的数据类型
export type ListParm = {
    phone: string,
    nickName: string,
    currentPage: number,
    pageSize: number,
    total: number
}
//定义用户的数据类型
export type AddUserModel = {
    type: string, //主要是用了区分 新增还是编辑 0：新增 1：编辑
    userId: string,
    roleId: string,
    username: string,
    password: string,
    phone: string,
    email: string,
    sex: string,
    enabled:boolean
    nickName: string
}
export type UpdatePass = {
    userId: string,
    password: string,
}