<template>
	<div class="container">
	<el-row  :gutter="24">
		<el-col :span="24" class="header" style="padding-left:0">
			<el-col :span="10" style="padding-left:0;padding-right:0" class="logo logo-width">
				{{sysName}}
			</el-col>
			<el-col :span="14" class="userinfo">
				<span style="margin-right:50px;">当前时间:{{nowTime}}</span>
				<el-dropdown trigger="hover">
					<span class="el-dropdown-link userinfo-inner">您好，{{role='role_admin'?'超级管理员':'宿舍管理员'}}：<span style="color:green">{{userName}}</span></span>
					<el-dropdown-menu slot="dropdown">
						<el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
					</el-dropdown-menu>
				</el-dropdown>
			</el-col>
		</el-col>
		</el-row>
		<el-row :gutter="24" class="main" >
			<el-col :span="4" style="padding-left:0">
				<aside class="menu-expanded">
					<!--导航菜单-->
					<el-menu
						default-active="1"
						class="el-menu-demo"
						router>
						<el-menu-item index="/home/census">
							<span slot="title">统计报表</span>
						</el-menu-item>
						<el-menu-item index="/home/student">
							<span slot="title">学生管理</span>
						</el-menu-item>
						<el-menu-item index="/home/dorm">
							<span slot="title">宿舍管理</span>
						</el-menu-item>
						<el-menu-item index="4">
							<span slot="title">用户管理</span>
						</el-menu-item>
					</el-menu>
				</aside>
			</el-col>
			<el-col :span="19" style="padding-left:0;padding-right:0">
				<section class="content-container">
					<router-view/>
					<!-- <div class="grid-content bg-purple-light">
						<el-col :span="24" class="breadcrumb-container">
							<strong class="title">{{$route.name}}</strong>
							<el-breadcrumb separator="/" class="breadcrumb-inner">
								<el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
									{{ item.name }}
								</el-breadcrumb-item>
							</el-breadcrumb>
						</el-col>
						<el-col :span="24" class="content-wrapper">
							<transition name="fade" mode="out-in">
								<router-view></router-view>
							</transition>
						</el-col>
					</div> -->
				</section>
			</el-col>
		</el-row>
		
	
</div>
</template>

<script>
import { Message } from 'element-ui';
	export default {
		data() {
			return {
				sysName:'茂名职业技术学院宿舍管理系统',
				role:'',
				userName: '',
				nowTime:''
				
			}
		},
		methods: {
			//退出登录
			logout () {
				var _this = this;
				this.$confirm('确认退出吗?', '提示', {
					//type: 'warning'
				}).then(() => {

					this.$store.dispatch("Logout").then((res)=>{
						if(res.code == 200){
							Message.success(res.message);
							this.$router.push({path: '/login'})
						}
					}).catch((error)=>{
						console.log(error)
					})
				
				}).catch(() => {

				});


			},
			getTime(){
				setInterval(()=>{
					this.nowTime = new Date().toLocaleString();
				})
			}
		},
		created(){
			this.getTime();
			this.$store.dispatch('GetInfo').then((res) => {
			console.log(res)
			if(res.code == 200){
				this.userName = this.$store.state.username;
				this.role = this.$store.state.roles;
				console.log(this.userName);
			}
			}).catch((error)=>{
				console.log(error)
				this.loading = false;
			})
			
  },
		mounted() {
			var user = sessionStorage.getItem('user');
			if (user) {
				user = JSON.parse(user);
				this.sysUserName = user.name || '';
				this.sysUserAvatar = user.avatar || '';
			}

		}
	}

</script>

<style scoped lang="scss">
*{
	box-sizing: border-box;
}
	[v-clock]{
		display: none;
	}
	.container {
		position: absolute;
		top: 0px;
		bottom: 0px;
		width: 100%;
		.header {
			height: 60px;
			line-height: 60px;
			background: #20a0ff;
			color:#fff;
			.userinfo {
				text-align: right;
				padding-right: 35px;
				float: right;
				margin-right: 50px;
				.userinfo-inner {
					cursor: pointer;
					color:#fff;
					img {
						width: 40px;
						height: 40px;
						border-radius: 20px;
						margin: 10px 0px 10px 10px;
						float: right;
					}
				}
			}
			.logo {
				width:230px;
				height:60px;
				font-size: 24px;
				padding-left:20px;
				padding-right:20px;
				border-color: rgba(238,241,146,0.3);
				border-right-width: 1px;
				border-right-style: solid;
				img {
					width: 40px;
					float: left;
					margin: 10px 10px 10px 18px;
				}
				.txt {
					color:#fff;
				}
			}
			.logo-width{
				width:390px;
				border: none;
			}
		}
		.main {
			overflow: hidden;
			
			aside {
				.el-menu{
					height: 100%;
					border-bottom-right-radius: 9px;
				}
			}
			.menu-expanded{
				.el-menu-demo {
					.el-menu-item{
					border-bottom: 1px solid #cfcfcf;
					border-bottom-left-radius: 9px;
					border-bottom-right-radius: 9px;
					margin: 10px 0;
					}
				}
			}
		}
	}
</style>