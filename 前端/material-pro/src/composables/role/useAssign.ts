import { reactive } from "vue";
import { getAssignTreeApi } from "@/api/role";
import { AssignParm } from "@/api/role/RoleModel";
export default function useAssign() {
    //定义树的属性
    const defaultProps = reactive({
        children: 'children',
        label: 'title'
    })
    //权限树数据
    const assignTreeData = reactive({
        list: [],
        assignTreeChecked: [] //角色原来分配的权限id的集合
    })
    //获取数据
    const getAssignTree = async (parm: AssignParm) => {
        let res = await getAssignTreeApi(parm)
        if (res && res.code == 200) {
            //设置权限树数据
            assignTreeData.list = res.data.listmenu
            //设置角色原来的权限Id
            assignTreeData.assignTreeChecked = res.data.checkList
            //数据回显，判断角色原来是否已经分配过权限，如果有，回显
            if (assignTreeData.assignTreeChecked.length > 0) {
                let newArr: any = [];
                assignTreeData.assignTreeChecked.forEach((item => {
                    checked(item, assignTreeData.list, newArr)
                }))
                assignTreeData.assignTreeChecked = newArr;
            }
        }
    }
    //找出角色原来的权限，用于回显
    const checked = (id: number, data: any, newArr: any) => {
        data.forEach((item: any) => {
            if (item.menuId == id) {
                if (item.children && item.children.length == 0) {
                    newArr.push(item.menuId)
                }
            } else {
                if (item.children && item.children.length != 0) {
                    //递归调用
                    checked(id, item.children, newArr)
                }
            }
        })
    }
    return{
        defaultProps,
        assignTreeData,
        getAssignTree
    }
}