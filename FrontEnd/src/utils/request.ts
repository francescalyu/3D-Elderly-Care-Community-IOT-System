/**
 * http request 
 * @author emiila
 * 
**/


import instance from '@/http/axios'
import { ContentTypeEnum } from '@/enums/httpEnum'

const request = {
  get(url: any, params?: any, responseType?: any) {
    params = unref(params)
    return instance({
      method: 'get',
      url: url,
      headers: {
        'content-type': ContentTypeEnum.JSON
      },
      params: params || {},
      responseType: responseType
    })
  },
  post(url: any, data?: any, params?: any, responseType?: any) {
    params = unref(params)
    data = unref(data)
    return instance({
      method: 'POST',
      url: url,
      headers: { 'content-type': ContentTypeEnum.JSON },
      data: data,
      params: params,
      responseType: responseType
    })
  },
  put(url: any, data: any, params?: any) {
    url = url
    data = unref(data)
    params = unref(params)
    return instance({
      method: 'put',
      url: url,
      headers: {
        'content-type': ContentTypeEnum.JSON
      },
      data: data,
      params: params
    })
  },
  delete(url: any, data?: any) {
    url = url
    data = unref(data)
    return instance({
      method: 'delete',
      url: url,
      headers: {
        'content-type': ContentTypeEnum.JSON
      },
      data: data
    })
  },
  //上传文件
  customUpload(url: String, data?: Object) {
    data = unref(data)
    return instance({
      method: 'POST',
      url: url,
      headers: { 'content-type': ContentTypeEnum.FORM_DATA },
      data: data
    })
  },
  // 上传图片
  upLoadFile(file: File) {
    const formData = new FormData()
    formData.append('file', file)
    return instance({
      method: 'POST',
      url: '/file',
      headers: {
        'content-type': ContentTypeEnum.FORM_DATA
      },
      data: formData
    })
  }
  // 下载
  // 导出excel
}

export default request
