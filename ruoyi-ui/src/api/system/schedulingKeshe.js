import request from '@/utils/request'

// 查询医生排班列表
export function listSchedulingKeshe(query) {
  return request({
    url: '/system/schedulingKeshe/list',
    method: 'get',
    params: query
  })
}

// 查询医生排班详细
export function getSchedulingKeshe(scheduleId) {
  return request({
    url: '/system/schedulingKeshe/' + scheduleId,
    method: 'get'
  })
}

// 新增医生排班
export function addSchedulingKeshe(data) {
  return request({
    url: '/system/schedulingKeshe',
    method: 'post',
    data: data
  })
}

// 修改医生排班
export function updateSchedulingKeshe(data) {
  return request({
    url: '/system/schedulingKeshe',
    method: 'put',
    data: data
  })
}

// 删除医生排班
export function delSchedulingKeshe(scheduleId) {
  return request({
    url: '/system/schedulingKeshe/' + scheduleId,
    method: 'delete'
  })
}
