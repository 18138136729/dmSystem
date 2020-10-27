import Vue from 'vue'
import Vuex from 'vuex'
import { login , getInfo,logout} from '@/api/login'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    username: '',
    roles: ''
  },
  mutations: {
    SET_NAME: (state, username) => {
      state.username = username
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    }
  },
  getters:{
  },
  actions: {
  GetInfo({ commit}) {

    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const data = response.data
        if(data==null&&data==""){
          reject('getInfo:用户信息为空!')
        }
        console.log(data.state)
        if(data.state!="1"){
          return reject('账号已冻结！')
        }
        commit('SET_ROLES', data.roles)
        commit('SET_NAME', data.userName)
        resolve(response)
      })
      .catch(error => {
        console.log("error2");
        return reject(error)
      })
    })
},
Logout({ commit}) {

  return new Promise((resolve, reject) => {
    logout().then(response => {
      commit('SET_ROLES', '')
      commit('SET_NAME', '')
      resolve(response)
    })
    .catch(error => {
      console.log("error2");
      return reject(error)
    })
  })
},
}
}
)

