import request from '@/utils/request'

// 查询患者信息列表
export function listPatientKeshe(query) {
  return request({
    url: '/system/patientKeshe/list',
    method: 'get',
    params: query
  })
}

// 查询患者信息详细
export function getPatientKeshe(patientId) {
  return request({
    url: '/system/patientKeshe/' + patientId,
    method: 'get'
  })
}

// 新增患者信息
export function addPatientKeshe(data) {
  return request({
    url: '/system/patientKeshe',
    method: 'post',
    data: data
  })
}

// 修改患者信息
export function updatePatientKeshe(data) {
  return request({
    url: '/system/patientKeshe',
    method: 'put',
    data: data
  })
}

// 删除患者信息
export function delPatientKeshe(patientId) {
  return request({
    url: '/system/patientKeshe/' + patientId,
    method: 'delete'
  })
}

// 【新增】患者端完善个人档案
export function addPatientProfile(data) {
  return request({
    url: '/system/patientKeshe/addProfile',
    method: 'post',
    data: data
  })
}