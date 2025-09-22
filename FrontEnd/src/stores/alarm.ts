/**
 * save warn
 * @author emiila
 * 
**/


// 告警sore存储
import { defineStore } from "pinia";

export const useAlarmStore = defineStore('Count', {
    //真正存储数据的地方
    state() {
        return {
            // soundArr: [],
            ids:[],
        }
    }
})
