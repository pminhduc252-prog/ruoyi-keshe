import request from '@/utils/request'

// 查询挂号预约记录列表
export function listAppointmentKeshe(query) {
  return request({
    url: '/system/appointmentKeshe/list',
    method: 'get',
    params: query
  })
}

// 查询挂号预约记录详细
export function getAppointmentKeshe(appointmentId) {
  return request({
    url: '/system/appointmentKeshe/' + appointmentId,
    method: 'get'
  })
}

// 新增挂号预约记录
export function addAppointmentKeshe(data) {
  return request({
    url: '/system/appointmentKeshe',
    method: 'post',
    data: data
  })
}

// 修改挂号预约记录
export function updateAppointmentKeshe(data) {
  return request({
    url: '/system/appointmentKeshe',
    method: 'put',
    data: data
  })
}

// 删除挂号预约记录
export function delAppointmentKeshe(appointmentId) {
  return request({
    url: '/system/appointmentKeshe/' + appointmentId,
    method: 'delete'
  })
}
