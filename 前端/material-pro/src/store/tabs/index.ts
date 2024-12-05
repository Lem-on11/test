import { defineStore } from 'pinia'
//定义选项卡数据类型
export type Tab = {
    title: string,
    path: string
}
//定义state类型
export type TabState = {
    tabList: Tab[]
}
//定义store
export const tabStore = defineStore('tabStore', {
    state: (): TabState => {
        return {
            tabList: []
        }
    },
    getters: {
        //获取选项卡数据
        getTabs(state) {
            return state.tabList
        }
    },
    actions: {
        //添加选项卡数据
        addTab(tab: Tab) {
            //判断数组里面是否已经存在
            if (this.tabList.some(item => item.path === tab.path)) return
            this.tabList.push(tab)
        }
    },
    persist: {
        enabled: true,
        strategies: [
            { storage: localStorage, paths: ['tabList'] },
        ],
    }
})