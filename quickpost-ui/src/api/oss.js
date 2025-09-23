import request from '@/utils/request'

// 查询包裹列表
export function donwload(fileId) {
    return request({
        url: '/oss/download/' + fileId,
        method: 'get',
    })
}
