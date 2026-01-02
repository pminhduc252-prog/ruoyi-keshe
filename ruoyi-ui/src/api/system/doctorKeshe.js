import request from '@/utils/request'

// 查询医生信息列表
export function listDoctorKeshe(query) {
  return request({
    url: '/system/doctorKeshe/list',
    method: 'get',
    params: query
  })
}

// 查询医生信息详细
export function getDoctorKeshe(doctorId) {
  return request({
    url: '/system/doctorKeshe/' + doctorId,
    method: 'get'
  })
}

// 新增医生信息
export function addDoctorKeshe(data) {
  return request({
    url: '/system/doctorKeshe',
    method: 'post',
    data: data
  })
}

// 修改医生信息
export function updateDoctorKeshe(data) {
  return request({
    url: '/system/doctorKeshe',
    method: 'put',
    data: data
  })
}

// 删除医生信息
export function delDoctorKeshe(doctorId) {
  return request({
    url: '/system/doctorKeshe/' + doctorId,
    method: 'delete'
  })
}
