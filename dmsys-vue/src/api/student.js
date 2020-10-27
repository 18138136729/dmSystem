import request from '@/utils/request'


export function getStudentList(page,studentNum,studentName) {
    return request({
      url: '/student/list?page='+page+'&studentNum='+studentNum+'&studentName='+studentName,
      method: 'get',
    })
  }

  export function delStudent(studentId) {
    return request({
      url: '/student/'+studentId,
      method: 'delete',
    })
  }

  export function insertStudent(studentForm) {
    return request({
      url: '/student',
      method: 'post',
      data: {
        'studentNum':studentForm.studentNum,
        'studentName':studentForm.studentName,
        'sex':studentForm.sex,
        'phone':studentForm.phone,
        'address':studentForm.address
      },
     
    })
    
  }

  
  export function outDorm(studentId,dormId) {
    return request({
      url: '/student/outDorm?studentId='+studentId+'&dormId='+dormId,
      method: 'delete',
    })
  }
  
  export function getOptions() {
    return request({
      url: '/student/getOptions',
      method: 'get',
    })
  }

  export function inDorm(studentId,dormId) {
    return request({
      url: '/student/inDorm?studentId='+studentId+'&dormId='+dormId,
      method: 'post',
    })
  }

  export function getStudentById(studentId) {
    return request({
      url: '/student/studentById?studentId='+studentId,
      method: 'get',
    })
  }

  export function updateStudent(oneStudent) {
    return request({
      url: '/student',
      method: 'put',
      data: {
        'id':oneStudent.id,
        'studentNum':oneStudent.studentNum,
        'studentName':oneStudent.studentName,
        'sex':oneStudent.sex,
        'phone':oneStudent.phone,
        'address':oneStudent.address
      },
     
    })
    
  }