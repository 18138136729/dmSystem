import request from '@/utils/request'

export function login(userName, password,captcha,captchaKey) {
  return request({
    url: '/user/login/'+captcha+'/'+captchaKey,
    method: 'post',
    data: {
      userName,
      password
    },
   
  })
  
}

export function getInfo() {
  return request({
    url: '/user/info',
    method: 'get',
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'get'
  })
}

