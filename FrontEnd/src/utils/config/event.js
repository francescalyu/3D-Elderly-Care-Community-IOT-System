/**
 * event register 
 * 
 * @author emiila
 * 
**/

import { toArray } from './index'

/**
 * @description 事件注册机制
*/
class HandleEvent {
    constructor() {
        this._events = {}
    }

    emit(event) {
        let cbs = this._events[event]

        if(cbs) {
            cbs = cbs.length > 1 ? toArray(cbs) : cbs
            const args = toArray(arguments, 1)

            cbs.forEach(fn => {
                try {
                    args ? fn(args) : fn()
                } catch (error) {
                    throw new Error('HandleEvent Error:', error )
                }
            })
        }
    }

    on(event, fn) {
        ( this._events[event] || (this._events[event] = []) ).push(fn)
    }

    off(event, fn) {
        let cbs = this._events[event]

        if(cbs) {
            if(!fn) {
                this._events[event] = []
            } else {
                this._events[event] = this._events[event].filter(itemFn => {
                    return itemFn !== fn
                })
            }
        }
    }
}

export default (function () {
    let handleEvent
    return (() => {
        if(!handleEvent) {
            handleEvent = new HandleEvent()
        }
        return handleEvent
    })()
})()
