/**
 * axios url define
 * @author emiila
 * 
**/


import axios, { AxiosRequestConfig, AxiosInstance, AxiosResponse } from 'axios'
import { LocalStorageService, SessionStorageService } from '@/utils/storage'
import router from '@/router'

const isProd = import.meta.env.MODE === 'production'
// console.log('window.location.origin: ', window.location.origin);

let baseURL = ''
if (isProd) {
  // baseURL = window.location.origin
  baseURL = import.meta.env.VITE_GLOB_API_URL+'/api'
} else {
  baseURL = import.meta.env.VITE_GLOB_API_URL+'/api'
}
console.log('baseURL: ', baseURL,import.meta,isProd)

const axiosInstance: AxiosInstance = axios.create({
  baseURL: baseURL,
  timeout: 6 * 1000
})

// 添加请求拦截器
axiosInstance.interceptors.request.use(
  (config: any) => {
    console.log(config)
    // 在发送请求之前做些什么
    const token = LocalStorageService.get('token')

    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  (error: any) => {
    // 处理请求错误
    return Promise.reject(error)
  }
)

// 添加响应拦截器
axiosInstance.interceptors.response.use(
  (response: AxiosResponse) => {
    // 对响应数据做点什么
    // 对于目前后台的封装，取data里的数据，因为相较之前的版本
    // 多包了一层data
    console.log(
      response.request.responseURL.split(import.meta.env.VITE_GLOB_API_URL)[1] + '接口请求成功',
      response.data ? response.data : response
    )

    const code = response.data.code
    const type = response.data.type
    // 处理 JSON 类型的 Blob 响应
    if (type === 'application/json' && response.data instanceof Blob) {
      const reader = new FileReader()
      reader.readAsText(response.data, 'utf-8')
      reader.onload = () => {
        const { msg } = JSON.parse(reader.result)
        ElMessage.error(msg)
      }
    }
    // 处理成功返回
    if (
      code === 200 ||
      response.data.type === 'application/octet-stream' ||
      type === 'application/vnd.ms-excel'
    ) {
      return response.data ? response.data : response
    }
    // 处理未授权
    if (code === 401) {
      router.push('/login')
    }
    // 处理登录超时
    if (response.data.code == 500 && response.data.msg == '登录超时，请重新登录') {
      console.log(111);
      ElMessage.error('登录超时，请重新登录')
      LocalStorageService.clearAll()
      SessionStorageService.clearAll()
      router.push('/login').then(() => {
        location.reload()
      })
      return
    }
    // 其他错误信息
    ElMessage.error(response.data.msg)
  },
  (error: any) => {
    // 处理响应错误
    if (error.message === 'Network Error') {
      // LocalStorageService.clearAll()
      // SessionStorageService.clearAll()
    }
    return Promise.reject(error)
  }
)

export default axiosInstance
