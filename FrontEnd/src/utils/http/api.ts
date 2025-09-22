/**
 * axios http
 * @author emiila
 * 
**/


import axiosInstance from './axios';
import { ContentTypeEnum } from '@/enums/httpEnum'

export interface ApiResult<T> {
    code: number;
    message: string;
    data: T;
}

const http = {
    get(url: any, params?: any,responseType?:any) {
        params = unref(params)
        return axiosInstance({
            method: 'get',
            url: url,
            headers: {
                "content-type": ContentTypeEnum.JSON
            },
            params: params || {},
            responseType:responseType
        })
    },
    post(url: any, data?: any, params?: any,responseType?:any) {
        params = unref(params)
        data = unref(data)
        return axiosInstance({
            method: 'POST',
            url: url,
            headers: {"content-type": ContentTypeEnum.JSON},
            data: data,
            params: params,
            responseType:responseType
        })
    },
    put(url: any, data: any,params?: any) {
        url = url;
        data = unref(data)
        params = unref(params)
        return axiosInstance({
            method: 'put',
            url: url,
            headers: {
                "content-type": ContentTypeEnum.JSON
            },
            data: data,
            params: params,
        })
    },
    delete(url: any, data?: any) {
        url = url;
        data = unref(data)
        return axiosInstance({
            method: 'delete',
            url: url,
            headers: {
                "content-type": ContentTypeEnum.JSON
            },
            data: data
        })
    },
    //上传文件
    customUpload(url:String,data?:Object){
        data = unref(data)
        return axiosInstance({
            method: 'POST',
            url: url,
            headers: {"content-type": ContentTypeEnum.FORM_DATA},
            data: data,
        })
    },
    // 上传图片
    upLoadFile(file: File) {
        const formData = new FormData()
        formData.append('file', file)
        return axiosInstance({
            method: 'POST',
            url: '/file',
            headers: {
                "content-type": ContentTypeEnum.FORM_DATA
            },
            data: formData
        })
    },
    // 下载
    // 导出excel
}

export default http;
// export async function get<T>(url: string, params?: any): Promise<ApiResult<T>> {
//     const response = await axiosInstance.get<ApiResult<T>>(url, { params });
//     return response.data;
// }

// export async function post<T>(url:string, data?: any): Promise<ApiResult<T>> {
//     const response = await axiosInstance.post<ApiResult<T>>(url, data);
//     return response.data;
// }

// export async function put<T>(url: string, data?: any): Promise<ApiResult<T>> {
//     const response = await axiosInstance.put<ApiResult<T>>(url, data);
//     return response.data;
// }

// export async function del<T>(url: string, params?: any): Promise<ApiResult<T>> {
//     const response = await axiosInstance.delete<ApiResult<T>>(url, { params });
//     return response.data;
// }