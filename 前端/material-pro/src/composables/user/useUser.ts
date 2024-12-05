import { AddUserModel } from "@/api/user/UserModel"
import { EditType } from "@/type/BaseEnum"
import { ref } from "vue"
import { deleteApi,upPasswordApi } from "@/api/user"
import { ElMessage } from "element-plus"
import { FuncList } from "@/type/BaseType"
import useInstance from "@/hooks/useInstance"
export default function useUser(getList: FuncList) {
    const { global } = useInstance()
    //新增弹框的ref属性
    const addRef = ref<{ show: (type: string, row?: AddUserModel) => void }>()
    //新增
    const addBtn = () => {
        addRef.value?.show(EditType.ADD)
    }
    //编辑
    const editBtn = (row: AddUserModel) => {
        addRef.value?.show(EditType.EDIT, row)
    }
    //删除
    const deleteBtn = async (row: AddUserModel) => {
        //信息确定
        const confirm = await global.$myconfirm('确定删除该数据吗?')
        if (confirm) {
            let res = await deleteApi(row.userId)
            if (res && res.code == 200) {
                ElMessage.success(res.msg)
                //刷新列表
                getList()
            }
        }
    }
    //重置密码
    const resetPassword = async(row: AddUserModel) => {
        //信息确定
        const confirm = await global.$myconfirm('确定重置密码吗?重置后密码为【666666】')
        if(confirm){
            let parm = {
                userId:row.userId,
                password:'666666'
            }
            let res = await upPasswordApi(parm)
            if(res && res.code == 200){
                ElMessage.success('重置密码成功!');
            }
        }
    }
    return {
        addBtn,
        deleteBtn,
        editBtn,
        addRef,
        resetPassword,
        global
    }
}