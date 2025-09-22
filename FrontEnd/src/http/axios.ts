/**
 * axios 封装
 * 请求拦截、响应拦截、错误统一处理
 * 
 * @author emiila
 * 
**/


import axios from 'axios'
import { AxiosInstance } from 'axios'
//封装的一个toast的文件 用到了element-plus（注意安装 配置，也可以用vant 用哪个组件库都行） 代码会放在下面
import { toast } from '@/utils/tips'
//基础地址文件代码会放在下面
// import { getApiUrl } from "@/common/value/string";
import { LocalStorageService } from '@/utils/storage'
import { useUserStore } from '@/stores/user'

class HttpRequest {
  //axios的数据类型 AxiosInstance
  static axiosInstance: AxiosInstance
  static init() {
    //创建axios实例
    this.axiosInstance = axios.create()
    // 设置请求超时
    this.axiosInstance.defaults.timeout = 6 * 1000
    // 表示跨域请求时是否需要使用凭证
    // this.axiosInstance.defaults.withCredentials = true

    // 环境的切换，`baseURL` 将自动加在 `url` 前面，除非 `url` 是一个绝对 URL
    //不同的环境下的基础地址可能会不同，逻辑在基础地址文件中
    //process.env.NODE_ENV 所处的环境 是开发 测试之类的，获取不同的baseURL
    const isProd = import.meta.env.MODE === 'production'
    // console.log('window.location.origin: ', window.location.origin,isProd);

    let baseURL = ''
    if (isProd) {
      // baseURL = window.location.origin
      baseURL = import.meta.env.VITE_GLOB_API_URL+'/api'
    } else {
      baseURL = import.meta.env.VITE_GLOB_API_URL+'/api'
    }
    console.log('baseURL: ', baseURL,import.meta.env);
    this.axiosInstance.defaults.baseURL = baseURL

    // 设置 post 请求头    application/x-www-form-urlencoded;application/json
    // this.axiosInstance.defaults.headers.common["Authorization"] =
    //   ticketStore.fullToken;（获取token之类的写在了index.ts文件里）
    // 初始化拦截器
    this.initInterceptors()
    return this.axiosInstance
  }

  static initInterceptors() {
    // 添加请求拦截器
    this.axiosInstance.interceptors.request.use(
      (config) => {
        console.log(config)
        // config.headers['Access-Control-Allow-Origin']='http://localhost:5176'
        // config.headers['Access-Control-Allow-Methods']='GET, PUT, POST, DELETE, OPTIONS'
        // config.headers['Access-Control-Allow-Headers']='Content-Type, Authorization, Content-Length, X-Requested-With'
        // 在发送请求之前做些什么

        /* 登录流程控制中，根据本地是否存在 token 判断用户的登录情况
                        但是即使 token 存在，也有可能 token 是过期的，所以在每次的请求头中携带 token
                        后台根据携带的 token 判断用户的登录情况，并返回给我们对应的状态码
                        而后我们可以在响应拦截器中，根据状态码进行一些统一的操作 */
        // webStorage.ls.get('token') && (config.headers.Authorization = `Bearer ${webStorage.ls.get('token')}`);
        const token = LocalStorageService.get('token')
        if (token) {
          config.headers.Authorization = token
        }

        return config
      },
      (error) => {
        // 对请求错误做些什么
        return Promise.reject(error)
      }
    )
    // 添加响应拦截器
    this.axiosInstance.interceptors.response.use(
      (response) => {
        // 对响应数据做点什么
        // 如果返回的状态码为200，说明接口请求成功，可以正常拿到数据，否则的话抛出错误
        // if (response.status === 200) {
        //     //这里.data，请求数据的时候就不用.data了
        //     return Promise.resolve(response.data);
        // } else {
        //     return Promise.reject(response);
        // }
        // return response;
        const res = response.data
        console.log(res)
        if (res.code == 500 && res.msg == '登录超时，请重新登录') {
          const userStore = useUserStore()
          userStore.resetToken().then(() => {
            location.reload()
          })
        }
        if (res.code == 500) {
          ElMessage({
            type: 'error',
            duration: 5 * 1000,
            message: res.msg
          })
          return
        }
        // 当状态值不为200判断为错误
        if (res.code != 200) {
          if (res.code == 401 || res.code == 50014) {
            ElMessage({
              type: 'error',
              duration: 5 * 1000,
              message: '登陆超时，请重新登录'
            })
            const userStore = useUserStore()
            userStore.resetToken().then(() => {
              location.reload()
            })
          }
          return res
        } else if (res.code == 401) {
          ElMessage({
            type: 'error',
            duration: 5 * 1000,
            message: '登陆超时，请重新登录'
          })
          const userStore = useUserStore()
          userStore.resetToken().then(() => {
            location.reload()
          })
        } else {
          return res
        }
      },
      (error) => {
        // 服务器状态码不是 200 的情况
        // 对响应错误做点什么

        const { config, code, request, response, isAxiosError, toJSON } = error
        if (response) {
          // 请求已发出，但是不在2xx的范围 调用错误处理的方法（把状态码，错误的提示信息传进去）
          this.errorHandle(response.status, response.data.message)
          return Promise.reject(response)
        } else {
          // 超时提示
          if (error.message.includes('timeout')) {
            toast('请求超时，请稍后重试', 'fail')
            return Promise.reject(error)
          }

          /* 处理断网的情况
                              eg: 请求超时或断网时，更新 state 的 network 状态
                              network 状态在 app.vue 中控制着一个全局的断网提示组件的显示隐藏
                              关于断网组件中的刷新重新获取数据，会在断网组件中说明 */
          if (!window.navigator.onLine) {
            // 如果断网....
            // store.commit('changeNetwork', false);
          } else {
            return Promise.reject(error)
          }
        }
      }
    )
  }

  /**
   * 请求失败后的错误统一处理
   * @param {Number} status 请求失败的状态码
   * 1**  信息，服务器收到请求，需要请求者继续执行操作
   * 2**  成功，操作被成功接收并处理
   * 3**  重定向，需要进一步的操作以完成请求
   * 4**  客户端错误，请求包含语法错误或无法完成请求
   * 5**  服务器错误，服务器在处理请求的过程中发生了错误
   */

  static errorHandle(status: number, other: string) {
    // 状态码判断
    switch (status) {
      // 400: 请求错误
      case 400:
        toast('请求错误', 'fail')
        break
      // 401: 未登录状态，跳转登录页
      case 401:
        toast('登陆状态已过期，请重新登录', 'fail')

        // window.location.href = process.env.VUE_APP_YFTXGLPT
        break
      // 403 权限不足，拒绝访问
      case 403:
        toast('权限不足，拒绝访问', 'fail')
        break
      // 404请求不存在
      case 404:
        toast('请求的资源不存在或请求地址出错', 'fail')
        break
      // 服务器错误
      case 500:
        toast('服务器错误', 'fail')
        break
      // token异常
      case 10001:
        toast('token异常，请联系管理员', 'fail')
        break
      default:
        console.log(other)
    }
  }
}

export default HttpRequest.init()

/**
 * 100  继续。客户端应继续其请求
 * 101  切换协议。服务器根据客户端的请求切换协议。只能切换到更高级的协议，例如，切换到HTTP的新版本协议
 *
 * 200  请求成功。一般用于GET与POST请求
 * 201  已创建。成功请求并创建了新的资源
 * 202  已接受。已经接受请求，但未处理完成
 * 203  非授权信息。请求成功。但返回的meta信息不在原始的服务器，而是一个副本
 * 204  无内容。服务器成功处理，但未返回内容。在未更新网页的情况下，可确保浏览器继续显示当前文档
 * 205  重置内容。服务器处理成功，用户终端（例如：浏览器）应重置文档视图。可通过此返回码清除浏览器的表单域
 * 206  部分内容。服务器成功处理了部分GET请求
 *
 * 300  多种选择。请求的资源可包括多个位置，相应可返回一个资源特征与地址的列表用于用户终端（例如：浏览器）选择
 * 301  永久移动。请求的资源已被永久的移动到新URI，返回信息会包括新的URI，浏览器会自动定向到新URI。今后任何新的请求都应使用新的URI代替
 * 302  临时移动。与301类似。但资源只是临时被移动。客户端应继续使用原有URI
 * 303  查看其它地址。与301类似。使用GET和POST请求查看
 * 304  未修改。所请求的资源未修改，服务器返回此状态码时，不会返回任何资源。客户端通常会缓存访问过的资源，通过提供一个头信息指出客户端希望只返回在指定日期之后修改的资源
 * 305  使用代理。所请求的资源必须通过代理访问
 * 306  已经被废弃的HTTP状态码
 * 307  临时重定向。与302类似。使用GET请求重定向
 *
 * 400  客户端请求的语法错误，服务器无法理解
 * 401  请求要求用户的身份认证
 * 402  保留，将来使用
 * 403  服务器理解请求客户端的请求，但是拒绝执行此请求
 * 404  服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置"您所请求的资源无法找到"的个性页面
 * 405  客户端请求中的方法被禁止
 * 406  服务器无法根据客户端请求的内容特性完成请求
 * 407  请求要求代理的身份认证，与401类似，但请求者应当使用代理进行授权
 * 408  服务器等待客户端发送的请求时间过长，超时
 * 409  服务器完成客户端的 PUT 请求时可能返回此代码，服务器处理请求时发生了冲突
 * 410  客户端请求的资源已经不存在。410不同于404，如果资源以前有现在被永久删除了可使用410代码，网站设计人员可通过301代码指定资源的新位置
 * 411  服务器无法处理客户端发送的不带Content-Length的请求信息
 * 412  客户端请求信息的先决条件错误
 * 413  由于请求的实体过大，服务器无法处理，因此拒绝请求。为防止客户端的连续请求，服务器可能会关闭连接。如果只是服务器暂时无法处理，则会包含一个Retry-After的响应信息
 * 414  请求的URI过长（URI通常为网址），服务器无法处理
 * 415  服务器无法处理请求附带的媒体格式
 * 416  客户端请求的范围无效
 * 417  服务器无法满足Expect的请求头信息
 *
 * 500  服务器内部错误，无法完成请求
 * 501  服务器不支持请求的功能，无法完成请求
 * 502  作为网关或者代理工作的服务器尝试执行请求时，从远程服务器接收到了一个无效的响应
 * 503  由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
 * 504  充当网关或代理的服务器，未及时从远端服务器获取请求
 * 505  服务器不支持请求的HTTP协议的版本，无法完成处理
 */
