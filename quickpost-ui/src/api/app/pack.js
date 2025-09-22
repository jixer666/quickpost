import request from '@/utils/request'

// 查询包裹列表
export function listPack(query) {
  return request({
    url: '/app/pack/list',
    method: 'get',
    params: query
  })
}

// 查询包裹详细
export function getPack(packId) {
  return request({
    url: '/app/pack/' + packId,
    method: 'get'
  })
}

// 新增包裹
export function addPack(data) {
  return request({
    url: '/app/pack',
    method: 'post',
    data: data
  })
}

// 修改包裹
export function updatePack(data) {
  return request({
    url: '/app/pack',
    method: 'put',
    data: data
  })
}

// 删除包裹
export function delPack(packId) {
  return request({
    url: '/app/pack/' + packId,
    method: 'delete'
  })
}
