import { request } from "@/http"
//关于-作者
export function getAuthor() {
    return request({
        method: "post",
        url: `/v1/about/getAuthor`
    })
}
//关于-架构
export function getArch() {
    return request({
        method: "post",
        url: `/v1/about/getarch`
    })
}
//关于-模型
export function getModel() {
    return request({
        method: "post",
        url: `/v1/about/getmodel`
    })
}