/**
 * node file upload
 * @author emiila
 * 
**/


/**
 * node 上传文件服务
 */
import request from '@/utils/http/axios';
import { ContentTypeEnum } from '@/enums/httpEnum'

export const uploadFile = (path, fileName, file) => {
  const formData = new FormData();
  formData.append('path', path);
  formData.append('fileName', fileName);
  formData.append('file', file);
  return request({
    url: `node/files/uploadFiles`,
    method: 'post',
    data:formData
  })
  // return Axios
  //     .post("http://192.168.2.15:8220/node/files/uploadFiles", formData)
};
//
// 上传

export const upLoadFile = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/file',
    method: 'post',
    headers: {
      'Content-Type': ContentTypeEnum.FORM_DATA,
    },
    data: formData
  })
}
//
export const deleteFile = pathArr => {
  const data = {
    type: 'files',
    list: pathArr.map(e => ({ type: 'file', path: e }))
  };
  return request({
    url: `node/utils/delFolderOrFile`,
    method: 'delete',
    data
  })
};

/**
 *
 * @param file 上传临时文件夹
 */
export const uploadTempFile = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    method:'post',
    url: '/file',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data;charset=UTF-8',
    },
  })
}

export const getUploadFile = (str)=>{
  let ids
  if (Array.isArray(str))
    ids = str.join
  else
    ids = str
  return request({
    method:'get',
    url:'/file',
    params:{ids}
  })
}
