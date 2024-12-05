//定义列表参数类型
export type MaterialParm = {
    currentPage:number,
    pageSize:number,
    infoName:string,
    total:number
}
//定义物资信息数据类型
export type MaterialType = {
    infoId:string,
    categoryId:string,
    infoName:string,
    image:string,
    specs:string,
    unit:string,
    infoDesc:string,
    type:string,
    num?:number,
    store?:number
}