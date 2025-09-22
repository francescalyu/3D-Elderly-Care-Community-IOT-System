/**
 * no use 
 * @author emiila
 * 
**/


import { toString, isFunction, pick, remove } from 'lodash-es'

import { v4 as uuidv4 } from 'uuid'
/**
 * 字典翻译
 *
 * @param {*} code 需要翻译的编码（一般是某id或者code）
 * @param {*} arr 翻译的字典（list）
 * @param {string} [key='code'] 与字典匹配的属性（对象中的O[key]与code相同）
 * @param {string} [valueKey='value'] 翻译值在对象中的属性
 * @param {*} [callback=this.getWordbookValue] 回调
 * @returns 返回翻译结果
 */
export function getWordbookValue(
  code,
  arr,
  key = 'code',
  valueKey = 'value',
  callback = getWordbookValue
) {
  for (let i = 0, length = arr.length; i < length; i++) {
    let v = arr[i]
    if (v[key] == code) {
      return v[valueKey]
    } else if (v.children && v.children.length > 0) {
      let value = callback(code, v.children, key, valueKey, callback)
      if (value != code) return value
    }
  }

  return code
}
export function deepClone(obj: any) {
  var result = typeof obj.splice === 'function' ? [] : {},
    key

  if (obj && typeof obj === 'object') {
    for (key in obj) {
      if (obj[key] && typeof obj[key] === 'object') {
        result[key] = deepClone(obj[key])
      } else {
        result[key] = obj[key]
      }
    }
    return result
  }
  return obj
}

//防抖
export function debounce(fn: any, delay: any) {
  var delay = delay || 200
  var timer: any
  return function () {
    var th = this
    var args = arguments
    if (timer) {
      clearTimeout(timer)
    }
    timer = setTimeout(function () {
      timer = null
      fn.apply(th, args)
    }, delay)
  }
}
//   export const getPreviewUrl = () => import.meta.env.VITE_UPLOAD_PREVIEW_URL
export const getPreviewUrl = () => {
  // return `${import.meta.env.MODE === 'development' ? `http://192.168.100.15:8908/minio` : `${window.location.origin}/minio`}`
   return `${import.meta.env.MODE === 'development' ? `http://192.168.100.15:8908/minio` : `http://192.168.100.15:8908/minio`}`
}

// 获取当前环境的url
export function getUrl(path: String) {
  const debug = import.meta.env.MODE !== 'production'
  let url
  if (debug) url = 'api/' + path
  else url = path
  return url
}
//将阿拉伯数字转换为大写数字
export function numberTransfer(n: any) {
  var cnum = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十']
  var s = ''
  n = '' + n // 数字转为字符串
  for (var i = 0; i < n.length; i++) {
    s += cnum[parseInt(n.charAt(i))]
  }
  if (s.length == 2) {
    // 两位数的时候
    // 如果个位数是0的时候，0改成十
    if (s.charAt(1) == cnum[0]) {
      s = s.charAt(0) + cnum[10]
      // 如果是一十改成十
      if (s == cnum[1] + cnum[10]) {
        s = cnum[10]
      }
    } else if (s.charAt(0) == cnum[1]) {
      // 如果十位数是一的话改成十
      s = cnum[10] + s.charAt(1)
    } else if (s.charAt(0) == cnum[2]) {
      // 如果十位数是二的话改成十二
      s = cnum[2] + cnum[10] + s.charAt(1)
    } else if (s.charAt(0) == cnum[3]) {
      s = cnum[3] + cnum[10] + s.charAt(1)
    } else if (s.charAt(0) == cnum[4]) {
      s = cnum[4] + cnum[10] + s.charAt(1)
    } else if (s.charAt(0) == cnum[5]) {
      s = cnum[5] + cnum[10] + s.charAt(1)
    } else if (s.charAt(0) == cnum[6]) {
      s = cnum[6] + cnum[10] + s.charAt(1)
    } else if (s.charAt(0) == cnum[7]) {
      s = cnum[7] + cnum[10] + s.charAt(1)
    } else if (s.charAt(0) == cnum[8]) {
      s = cnum[8] + cnum[10] + s.charAt(1)
    } else if (s.charAt(0) == cnum[9]) {
      s = cnum[9] + cnum[10] + s.charAt(1)
    } else if (s.charAt(0) == cnum[10]) {
      s = cnum[10] + cnum[10] + s.charAt(1)
    }
  }
  return s
}

//对象数组去重
export function removeDuplicateObj(arr) {
  let obj = {}
  arr = arr.reduce((newArr, next) => {
    obj[next.id] ? '' : (obj[next.id] = true && newArr.push(next))
    return newArr
  }, [])
  return arr
}
/**
 * 是否为空
 * @author emiila
 * @param { any } data
 * @returns { Boolean }
 */

export function isEmpty(data) {
  return (
    data === '' || data === null || data === undefined || data === 'null' || data === 'undefined'
  )
}
/**
 * @description 添加单位
 * @author emiila
 * @param { Number | String } val 数值 或者是带单位的 数值 例如 10 && 10px
 * @returns { String }
 */
export function setUnit(data) {
  if (typeof Number(data) === 'number' && !isNaN(data)) {
    return `${data}px`
  }

  if (data === undefined || data === null) {
    return 0
  }

  return data
}

/**
 * @description 生成唯一id
 * @author emiila
 * @param { String } text 前缀
 * @param { Number | String } val 数值 或者是带单位的 数值 例如 10 && 10px
 * @returns { Number }
 */
export function uuid(text, val) {
  return `${text + uuidv4()}`
}

export function lookForAllItem(data = [] as any, arr = [] as any) {
  for (const item of data) {
    arr.push(item)
    if (item.children && item.children.length) lookForAllItem(item.children, arr)
  }
  return arr
}

export function generateTree(arr, parentId, props, callback) {
  const defaultTreeProps = { idName: 'id', parentIdName: 'parentId', childrenName: 'children' }
  const __props = { ...defaultTreeProps, ...props }
  const propsNeed = pick(__props, ['idName', 'parentIdName', 'childrenName'])
  return _generateTree(arr, parentId, propsNeed, callback)
}
//
export function arrayDeDuplication(arr) {
  return arr.sort().reduce((init, current) => {
    if (init.length === 0 || init[init.length - 1] !== current)
      init.push(current)

    return init
  }, [])
}
export const eval2 = (fn) => {
  const Fun = Function
  return new Fun(`return ${fn}`)()
}

export function getImageData(url) {
  return new Promise((resolve, reject) => {
    const img = new Image()
    img.src = url
    if (img.complete) {
      resolve(img)
    }
    else {
      img.onload = function () {
        resolve(img)
      }
    }
  })
}
//
function _generateTree(arr, parentId, props, callback) {
  parentId = toString(parentId)
  const left = remove(arr, (e) => toString(e[props.parentIdName]) === parentId)
  return left.map((e) => {
    const children = _generateTree(arr, e[props.idName], props, callback)
    if (children && children.length > 0) {
      e[props.childrenName] = children
    }
    isFunction(callback) && callback(e)
    return e
  })
}
