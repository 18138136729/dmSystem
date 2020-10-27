import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '../store'
import { getToken } from '@/utils/auth'

// 创建axios实例
const service = axios.create({
  baseURL: "http://localhost:2021", // api的base_url
  timeout: 15000, // 请求超时时间
  withCredentials: true
})


// respone拦截器
service.interceptors.response.use(
  response => {
  /**
  * code为非200是抛错 可结合自己业务进行修改
  */
    const res = response.data
    if (res.code !== 200) {
      Message({
        message: res.message,
        type: 'error',
        duration: 3 * 1000
      })

      return Promise.reject('error')
    } else {
      return response.data
    }
  },
  error => {
    if (error.response.data){
      error.message = error.response.data.message
  }
  Message({
    message: error,
    type: 'error',
    duration: 3 * 1000
  })
  return Promise.reject(error.message)
}
)

export default service
