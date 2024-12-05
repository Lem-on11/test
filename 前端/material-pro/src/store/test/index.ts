import { defineStore } from 'pinia'
//定义store
export const testStore = defineStore('testStore', {
    //定义state
    state: () => {
        return {
            count: 0
        }
    },
    //获取数据
    getters:{
        getCount(state){
            return state.count
        }
    },
    //不能使用箭头函数,this指向会发生变化
    actions:{
        setCount(count:number){
            this.count = count;
        }
    }
})
