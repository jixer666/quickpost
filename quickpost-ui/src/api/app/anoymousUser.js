import request from '@/utils/request'

// 查询匿名用户列表
export function listAnonymousUser(query) {
  return request({
    url: '/app/anonymous/list',
    method: 'get',
    params: query
  })
}

// 查询匿名用户详细
export function getAnonymousUser(anyUserId) {
  return request({
    url: '/app/anonymous/' + anyUserId,
    method: 'get'
  })
}

// 新增匿名用户
export function addAnonymousUser(data) {
  return request({
    url: '/app/anonymous',
    method: 'post',
    data: data
  })
}

// 修改匿名用户
export function updateAnonymousUser(data) {
  return request({
    url: '/app/anonymous',
    method: 'put',
    data: data
  })
}

// 删除匿名用户
export function delAnonymousUser(anyUserId) {
  return request({
    url: '/app/anonymous/' + anyUserId,
    method: 'delete'
  })
}

export function generatedAnonymousUser() {
    return request({
        url: '/app/anonymous/generate',
        method: 'post',
    })
}

