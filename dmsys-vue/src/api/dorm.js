import request from '@/utils/request'

export function getDorm(page,buildingNum) {
    return request({
      url: '/dorm/list?page='+page+'&buildingNum='+buildingNum,
      method: 'get',
    })
  }

  export function getDormTag() {
    return request({
      url: '/dorm/tag',
      method: 'get',
    })
  }
  

  export function insertDorm(dormForm) {
    var realNum = dormForm.buildingNum;
    if(dormForm.buildingNum==''){
      realNum = dormForm.newBuildingNum
      console.log(realNum)
    }
    return request({
      url: '/dorm',
      method: 'post',
      data: {
        'dormNum':dormForm.dormNum,
        'buildingNum':realNum,
        'limit':dormForm.limit,
      },
     
    })
    
  }