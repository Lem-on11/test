import { nextTick, onMounted, reactive, ref } from "vue";
import { getIntoListApi,intoApplyApi } from "@/api/send";
import useInstance from "@/hooks/useInstance";
import { ElMessage } from "element-plus";
import { MaterialSendParm } from "@/api/send/SendModel";
export default function useDetailTable() {
    const { global } = useInstance()
    //表格高度
    const tableHeigth = ref(0)
    //列表参数
    const listParm = reactive<MaterialSendParm>({
        currentPage: 1,
        pageSize: 10,
        province: '',
        name: '',
        total: 0,
        status: '',
        type: ''
    })
    //表格数据
    const tableList = reactive({
        list: []
    })
    //获取表格数据
    const getList = async () => {
        let res = await getIntoListApi(listParm)
        if (res && res.code == 200) {
            tableList.list = res.data.records;
            listParm.total = res.data.total;
        }
    }

    //搜索
    const searchBtn = () => {
        getList()
    }
    //重置
    const resetBtn = () => {
        listParm.name = ''
        listParm.province = ''
        listParm.status = ''
        listParm.type = ''
        getList()
    }
    //页容量改变时触发
    const sizeChange = (size: number) => {
        listParm.pageSize = size
        getList()
    }
    //页数改变时触发
    const currentChange = (page: number) => {
        listParm.currentPage = page;
        getList()
    }
    //拒绝
    const rejectBtn = async(row: any) => {
        if(row.status == '1' || row.status == '2'){
            ElMessage.warning('该信息已经审核通过，不用重复审核!')
            return;
        }
        const confirm = await global.$myconfirm('确定拒绝发放物资吗？')
        if(confirm){
            let res = await intoApplyApi({
                intoId:row.sendId,
                status:'2'
            })
            if(res && res.code == 200){
                ElMessage.success(res.msg)
                getList()
            }
        }
    }
    //同意
    const applyBtn = async(row: any) => {
        if(row.status == '1' || row.status == '2'){
            ElMessage.warning('该信息已经审核通过，不用重复审核!')
            return;
        }
        const confirm = await global.$myconfirm('确定同意发放物资吗？')
        if(confirm){
            let res = await intoApplyApi({
                intoId:row.sendId,
                status:'1'
            })
            if(res && res.code == 200){
                ElMessage.success(res.msg)
                getList()
            }
        }
    }
    onMounted(() => {
        getList()
        nextTick(() => {
            tableHeigth.value = window.innerHeight - 230
        })
    })
    return {
        listParm,
        getList,
        tableList,
        searchBtn,
        resetBtn,
        sizeChange,
        currentChange,
        tableHeigth,
        rejectBtn,
        applyBtn,
        global
    }
}