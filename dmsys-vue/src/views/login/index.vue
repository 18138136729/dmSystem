<template>
  <div>
    <el-card class="login-form-layout">
      <el-form autoComplete="on"
               :model="loginForm"
               :rules="loginRules"
               ref="loginForm"
               label-position="left"
               >

        <div style="text-align: center">
        </div>
        <h2 class="login-title color-main">茂名职业技术学院宿舍管理系统</h2>
        <el-form-item prop="username">
          <el-input name="username"
                    type="text"
                    v-model="loginForm.username"
                    autoComplete="on"
                    placeholder="请输入用户名">
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input name="password"
                    :type="pwdType"
                    @keyup.enter.native="handleLogin"
                    v-model="loginForm.password"
                    autoComplete="on"
                    placeholder="请输入密码">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-input name="captcha"
                    type="text"
                    @keyup.enter.native="handleLogin"
                    v-model="loginForm.captcha"
                    autoComplete="on"
                    placeholder="请输入验证码">
          </el-input>
          <img @click="updateCaptcha" :src="captchaPath" style="cursor:pointer;"/>
        </el-form-item>

        <el-form-item style="margin-bottom: 60px;text-align: center">
          <el-button style="width: 45%" type="primary" :loading="loading" @click.native.prevent="handleLogin">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <img :src="login_center_bg" class="login-center-layout">
  </div>
</template>

<script>
  import {isvalidUsername} from '@/utils/validate';
  import login_center_bg from '@/assets/images/login_center_bg.png'
  import { Message } from 'element-ui'
  import { login} from '@/api/login'

  export default {
    name: 'login',
    data() {
      return {
        loginForm: {
          username: '',
          password: '',
          captcha:'',
          captchaKey:'',
        },
        captchaPath:'',
        loginRules: {
          username: [{required: true, trigger: 'blur', validator: validateUsername}],
          password: [{required: true, trigger: 'blur', validator: validatePass}]
        },
        loading: false,
        pwdType: 'password',
        login_center_bg,
        supportDialogVisible:false
      }
      const validateUsername = (rule, value, callback) => {
        if (!isvalidUsername(value)) {
          callback(new Error('请输入正确的用户名'))
        } else {
          callback()
        }
      };
      const validatePass = (rule, value, callback) => {
        if (value.length < 3) {
          callback(new Error('密码不能小于3位'))
        } else {
          callback()
        }
      };
      
    },
    created() {
      if(this.loginForm.username === undefined||this.loginForm.username==null||this.loginForm.username===''){
        this.loginForm.username = '戴志远';
      }
      if(this.loginForm.password === undefined||this.loginForm.password==null||this.loginForm.password===''){
        this.loginForm.password = 'root';
      }
    },
    mounted(){
      this.loginForm.captchaKey = Date.parse(new Date());
      this.updateCaptcha();
    },
    methods: {
      updateCaptcha(){
        this.captchaPath = 'http://localhost:2021/user/captcha?captcha_key='+this.loginForm.captchaKey+"&random" + Date.parse(new Date());
      },
      handleLogin() {
        if(this.loginForm.captcha==''){
          this.loginForm.captcha=' '
        }
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.loading = true;
            return new Promise((resolve, reject) => {
            login(this.loginForm.username, this.loginForm.password,this.loginForm.captcha,this.loginForm.captchaKey).then(response => {
            Message.success("登录成功")
            this.$router.push({path: '/'})
            resolve()
          }).catch(error => {
            this.loading = false;
            reject(error)
          }) 
        })
          } else {
            console.log('参数验证不合法！');
            return false
          }
        })
      },
      dialogConfirm(){
        this.dialogVisible =false;
        setSupport(true);
      },
      dialogCancel(){
        this.dialogVisible = false;
        setSupport(false);
      }
    }
  }
</script>

<style scoped>
  .login-form-layout {
    position: absolute;
    left: 0;
    right: 0;
    width: 360px;
    margin: 140px auto;
    border-top: 10px solid #409EFF;
  }

  .login-title {
    text-align: center;
    font-size: 20px;
  }

  .login-center-layout {
    background: #409eff;
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
    margin-top: 200px;
  }
</style>
