/**
 * tips define
 * @author emiila
 * 
**/



// import { ElNotification } from "element-plus";
import "element-plus/es/components/notification/style/css";

//封装全局toast
export const toast = (msg: string, type: string = "default") => {
    switch (type) {
        case "default":
            ElNotification({
                title: "提示",
                message: msg,
                type: "info",
            });
            break;
        case "success":
            ElNotification({
                title: "成功",
                message: msg,
                type: "success",
            });
            break;
        case "fail":
            ElNotification({
                title: "错误",
                message: msg,
                type: "error",
            });
            break;
        case "warning":
            ElNotification({
                title: "警告",
                message: msg,
                type: "warning",
            });
            return toast;
    }
};