import { MenuType } from "@/api/menu/MenuModel"
import { EditType } from "@/type/BaseEnum"
import { ref } from "vue"
import useInstance from "@/hooks/useInstance"
import { deleteApi } from "@/api/menu"
import { ElMessage } from "element-plus"
import { FuncList } from "@/type/BaseType"
export default function useMenu(getList:FuncList) {
    const { global } = useInstance()
    //新增弹框的ref属性
    const addRef = ref<{ show: (type: string, row?: MenuType) => void }>()
    //新增
    const addBtn = () => {
        addRef.value?.show(EditType.ADD)
    }
    //编辑
    const editBtn = (row: MenuType) => {
        addRef.value?.show(EditType.EDIT, row)
    }
    //删除
    const deleteBtn = async(row: MenuType) => {
        const confirm = await global.$myconfirm('确定删除该数据吗?')
        if(confirm){
            let res = await deleteApi(row.menuId)
            if(res && res.code == 200){
                ElMessage.success(res.msg)
                //刷新列表
                getList()
            }
        }
    }
    return {
        addBtn,
        editBtn,
        deleteBtn,
        addRef,
        global
    }
}