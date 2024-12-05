import { nextTick, onMounted, reactive, ref } from "vue";
import { getListApi } from "@/api/menu";
export default function useTable(){
    //表格高度
    const tableHeight = ref(0)
    //定义列表数据
    const tableList = reactive({
        list:[]
    })
    //获取列表数据
    const getList = async()=>{
        let res = await getListApi()
        if(res && res.code == 200){
            tableList.list = res.data;
        }
    }
    onMounted(()=>{
        getList()
        nextTick(()=>{
            tableHeight.value = window.innerHeight - 200
        })
    })
    return{
        tableList,
        getList,
        tableHeight
    }
}