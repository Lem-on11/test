import { defineStore } from 'pinia'
import { getInfoApi } from '@/api/login'
//定义一个store
export const loginStore = defineStore('loginStore', {
    state: () => {
        return {
            token: '',
            userId: '',
            codeList: []
        }
    },
    getters: {
        getToken(state) {
            return state.token
        },
        getUserId(state) {
            return state.userId
        }
    },
    actions: {
        setToken(token: string) {
            this.token = token
        },
        setUserId(userId: string) {
            this.userId = userId
        },
        getInfo(userId: string) {
            return new Promise((resolve, reject) => {
                //获取数据
                getInfoApi(userId).then((res => {
                    if (res && res.code == 200) {
                        this.codeList = res.data.permissons
                    }
                    resolve(this.codeList)
                })).catch((error) => {
                    reject(error)
                })
            })
        }
    },
    persist: {
        enabled: true,
        strategies: [
            { storage: localStorage, paths: ['token', 'userId'] },
        ],
    }
})