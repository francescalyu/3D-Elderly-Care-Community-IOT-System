/**
 * 
 * @author emiila
 * 
**/

import instance from './axios'


// const ticketStore = useTicketStore()

interface responseInter {
    success: boolean
    info: string
    result: any
    code: number
    data: any
}

const request = async ({
    method,
    url,
    data,
    config,
    // loading = false,
    //接口地址上有没有api
    withoutApiString = false,
}: any): Promise<responseInter> => {
    method = method.toLowerCase()

    //实时获取新的token
    // instance.defaults.headers['Authorization'] = 从本地获取到的完整的token

    // if (withoutApiString === false) url = 'licensegen' + url
    if (method == 'post') {
        return instance.post(url, data, { ...config },)
    } else if (method == 'get') {
        return instance.get(url, {
            params: data,
            ...config
        })
    } else if (method == 'delete') {
        return instance.delete(url, {
             data,
            ...config
        })
    } else if (method == 'put') {
        return instance.put(url, data, { ...config })
    } else {
        console.error('未知的method' + method)
        return {
            success: false,
            info: '未知method',
            result: {},
            code: 'fail',
        }
    }
}

export {
    request,
    type responseInter,
}