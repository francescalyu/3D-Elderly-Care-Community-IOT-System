import { eval2 } from '@/utils'
import { arrayDeDuplication } from './index'

/**
 * 获取 数组-对象 下指定 key 值对应的数据
 * @param {array} arr
 * @param {string} keyValue
 * @param {string} keyName
 * @returns {object}
 */

export function queryArrayItem(dataArr, keyValue, keyName = 'name') {
  if (Array.isArray(dataArr) && dataArr.length > 0) {
    for (let i = 0, l = dataArr.length; i < l; i++) {
      if (dataArr[i][keyName] === keyValue)

        return dataArr[i]
    }
  }

  return false
}

/**
 * 分解 条件 格式
 * @param {string} formula
 * @returns {string}
 */

export function compileTerms(formula, pointData) {
  if (formula !== '' && formula != null && formula !== undefined) {
    const regExpPointId = /\{(.+?)\}/g

    let idArr = formula.match(regExpPointId)

    if (idArr != null) {
      idArr = arrayDeDuplication(idArr)

      idArr.forEach((v) => {
        let id = v.substr(1); let value

        id = id.substr(0, id.length - 1)

        for (const item of pointData) {
          if (item.pointId == id) {
            value = item.value
            break
          }
        }

        formula = formula.replace(new RegExp(`\\{${id}\\}`, 'g'), value)
      })

      try {
        return eval2(formula)
      }
      catch (error) {
        throw new Error('表达式错误')
      }
    }
  }

  return null
}
