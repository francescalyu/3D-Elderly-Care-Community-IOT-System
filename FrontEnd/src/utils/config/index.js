
/**
 * @description 计算
 * @author emiila
 */
export function compute(formula) {
    return (new Function(`return ${formula}`))()
}

/**
 * @description 数组去重
 * @author emiila
 * @param {array} arr
 * @returns {arr}
 */

export function arrayDeDuplication(arr) {
    return arr.sort().reduce((init, current) => {
        if(init.length === 0 || init[init.length-1] !== current) {
            init.push(current);
        }
        return init;
    }, []);
}

/**
 * @description 获取 url图片 的宽高
 * @author emiila
 * @param {string} url
 * @returns {object}
 */

export function getImageData(url) {
    return new Promise((resolve, reject) => {
        let img = new Image();
        img.src = url;
        if(img.complete) {
            resolve(img);
        } else {
            img.onload = function() {
                resolve(img);
            }
        }
    });
}

/**
 * @description 指定位数的随机数
 * @author emiila
 * @param { Number } val 位数 十百千 10  100  1000
 * @returns { Number }
 */
export function random(val) {
    return Math.floor(Math.random() * val)
}

/**
 * @description 防抖
 * @author emiila
 * @param { Function } fn 执行的方法
 * @param { Number } time 执行的间隔时间
 */
export function debounce (fn, time) {
    let timer = null
    return () => {
        timer && clearTimeout(timer)
        timer = setTimeout(fn, time)
    }
}
/**
 * @description 添加单位
 * @author emiila
 * @param { Number | String } val 数值 或者是带单位的 数值 例如 10 && 10px
 * @returns { String }
 */
export function setUnit(data) {
    if(typeof Number(data) === 'number' && !isNaN(data)) {
        return `${data}px`
    }

    if(data === undefined || data === null) {
        return 0
    }

    return data
}

/**
 * @description 类数组转换成数组
 * @author emiila
 * @param { Array } arr 数据
 * @param { Number | String } index 获取指定下标的数据
 * @returns { Array }
 */
export function toArray(arr, index) {
    if(index) {
        return Array.prototype.slice.call(arr)[index]
    }
    Array.prototype.slice.call(arr)
}