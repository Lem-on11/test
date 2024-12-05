import { MaterialType } from "@/api/material/MaterialModel"
import { EditType } from "@/type/BaseEnum"
import { ref } from "vue"
import { deleteApi } from "@/api/material"
import useInstance from "@/hooks/useInstance"
import { ElMessage } from "element-plus"
import { FuncList } from "@/type/BaseType"
export default function useMaterial(getList:FuncList) {
    const { global } = useInstance()
    //弹框属性
    const addRef = ref<{ show: (type: string, row?: MaterialType) => void }>()
    //新增
    const addBtn = () => {
        addRef.value?.show(EditType.ADD)
    }
    //编辑
    const editBtn = (row: MaterialType) => {
        addRef.value?.show(EditType.EDIT, row)
    }
    //删除
    const deleteBtn = async(row: MaterialType) => {
        const cofirm = await global.$myconfirm('确定删除该数据吗？')
        if(cofirm){
            let res = await deleteApi(row.infoId)
            if(res && res.code == 200){
                ElMessage.success(res.msg)
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