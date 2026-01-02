import request from '@/utils/request'

// 查询科室信息列表
export function listDepartmentKeshe(query) {
  return request({
    url: '/system/departmentKeshe/list',
    method: 'get',
    params: query
  })
}

// 查询科室信息详细
export function getDepartmentKeshe(deptId) {
  return request({
    url: '/system/departmentKeshe/' + deptId,
    method: 'get'
  })
}

// 新增科室信息
export function addDepartmentKeshe(data) {
  return request({
    url: '/system/departmentKeshe',
    method: 'post',
    data: data
  })
}

// 修改科室信息
export function updateDepartmentKeshe(data) {
  return request({
    url: '/system/departmentKeshe',
    method: 'put',
    data: data
  })
}

// 删除科室信息
export function delDepartmentKeshe(deptId) {
  return request({
    url: '/system/departmentKeshe/' + deptId,
    method: 'delete'
  })
}
