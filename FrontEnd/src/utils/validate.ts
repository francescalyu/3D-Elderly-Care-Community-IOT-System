/**
 * check define
 * @author emiila
 * 
**/

const text = '格式有误！';

const validate = {
  // 手机号码
  phone(rule, value, callback) {
    if (!value) return callback();

    const reg = /^1[3,4,5,7,8]\d{9}$/;

    if (!reg.test(value)) callback(new Error(`手机号码${text}`));

    callback();
  },

  number1_3(rule, value, callback) {
    if (!value) return callback();
    const reg1 = /^[0]/;
    const reg2 = /^[1-9]\d{0,2}$/;
    if (!reg2.test(value)) callback(new Error(`长度为 1 到 3 位的正整数`));
    if (reg1.test(value)) callback(new Error(`首位不能为0`));
    callback();
  },
  //保留小数点3位有效数字
  number2_3(rule, value, callback) {
    if (!value) return callback();
    const reg2 = /^\d+(?:\.\d{3})$/;
    if (!reg2.test(value)) callback(new Error(`请保留小数点后3位有效数字`));
    callback();
  },
  //
  // 正整数
  posNumber(rule, value, callback) {
    if (!value) return callback();
    const reg1 = /^[0]/;
    const reg2 = /^(0|[1-9]\d{0,9})$/;
    if (reg1.test(value)) callback(new Error(`首位不能为0`));
    if (!reg2.test(value)) callback(new Error(`长度不超过10位的正整数`));
    callback()
  },

  firstZero(rule, value, callback) {
    if (!value) return callback();

    const reg1 = /^[0]/;

    if (reg1.test(value)) callback(new Error(`首位不能为0`));

    callback();
  },

  // 端口号
  port(rule, value, callback) {
    if (value == '' || value == null) {
      callback();
      return;
    }

    const reg = /^[1-9]$|(^[1-9][0-9]$)|(^[1-9][0-9][0-9]$)|(^[1-9][0-9][0-9][0-9]$)|(^[1-6][0-5][0-5][0-3][0-5]$)/;

    if (!reg.test(value)) callback(new Error(`端口号${text}`));

    callback();
  },

  // ip
  ip(rule, value, callback) {
    if (value == '' || value == null) {
      callback();
      return;
    }

    const reg = /(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)/;

    if (!reg.test(value)) callback(new Error(`IP${text}`));

    callback();
  },

  //用户名
  username(rule, value, callback) {
    if (value == '' || value == null) {
      callback();
      return;
    }

    const regEn = /[`~!@#$%^&*()-_+<>?:"{},.\/;'[\]]/im,
      regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im,
      reg = /^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$/;

    if (!reg.test(value) || !regCn.test(value) || !regEn.test(value)) callback(new Error(`用户名不能含有特殊字符！`));

    callback();
  },

  //设备规格型号（不能有汉字）
  spec(rule, value, callback) {
    if (value == '' || value == null) {
      callback();
      return;
    }

    const reg = /^[^\u4e00-\u9fa5]+$/;

    if (!reg.test(value)) callback(new Error(`不可以包含中文字符`));

    callback();
  }
};

export default validate;

// ===============================

/**
 * 邮箱
 * @type {RegExp}
 */
export const emailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

/**
 * 手机号(mobile phone)中国(最宽松), 只要是1开头即可, 如果你的手机号是用来接收短信, 优先建议选择这一条
 * @type {RegExp}
 */
export const mobileRegex = /^(?:(?:\+|00)86)?1\d{10}$/;
