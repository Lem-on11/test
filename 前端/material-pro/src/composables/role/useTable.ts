import { nextTick, onMounted, reactive, ref } from "vue";
import { ListParm } from '@/api/role/RoleModel'
import { getListApi } from "@/api/role";
export default function useTable() {
    //表格高度
    const tableHeight = ref(0)
    //列表参数
    const listParm = reactive<ListParm>({
        roleName: '',
        pageSize: 10,
        currentPage: 1,
        total: 0
    })
    //表格数据
    const tableList = reactive({
        list: []
    })
    //列表
    const getList = async () => {
        let res = await getListApi(listParm)
        if (res && res.code == 200) {
            console.log(res.data)
            //设置表格数据
            tableList.list = res.data.records
            //设置分页的总条数
            listParm.total = res.data.total
        }
    }
    //搜索
    const searchBtn = () => {
        getList()
    }
    //重置
    const resetBtn = () => {
        listParm.roleName = ''
        getList()
    }
    //页容量改变时触发
    const sizeChange = (val: number) => {
        listParm.pageSize = val;
        getList()
    }
    //页数改变时触发
    const currentChange = (val: number) => {
        listParm.currentPage = val
        getList()
    }
    //刷新列表
    const refresh = ()=>{
        getList()
    }
    onMounted(() => {
        //计算表格高度
        nextTick(() => {
            tableHeight.value = window.innerHeight - 230
        })
        getList()
    })
    return {
        listParm,
        getList,
        searchBtn,
        resetBtn,
        tableList,
        sizeChange,
        currentChange,
        tableHeight,
        refresh
    }
}