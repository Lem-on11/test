import { DialogModel } from "@/type/BaseType";
import { reactive } from "vue";
export default function useDialog() {
    //定义弹框属性
    const dialog = reactive<DialogModel>({
        visible: false,
        height: 150,
        title:'标题',
        width:630
    });
    //弹框确定
    const onConfirm = () => {
        dialog.visible = false
    }
    //弹框取消
    const onClose = () => {
        dialog.visible = false
    }
    //弹框显示
    const onShow = () => {
        dialog.visible = true
    }
    return {
        dialog,
        onClose,
        onConfirm,
        onShow
    }
}