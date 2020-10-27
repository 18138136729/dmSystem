import request from '@/utils/request'

export function getSexOption() {
    return request({
      url: '/student/getSexOption',
      method: 'get',
    })
  }
  
export function getInDormOption() {
    return request({
      url: '/student/getInDormOption',
      method: 'get',
    })
  }