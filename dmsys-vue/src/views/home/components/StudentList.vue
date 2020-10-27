<template>
  <div class="StudentList">
    <el-dialog title="添加学生" :visible.sync="dialogFormVisible" width="30%" @closed="upId=''">
    <el-form enctype="multipart/form-data">
        <el-form-item label="学号" :label-width="formLabelWidth">
        <el-input v-model="studentForm.studentNum" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
        <el-input v-model="studentForm.studentName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
        <el-select v-model="studentForm.sex" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="联系方式" :label-width="formLabelWidth">
        <el-input v-model="studentForm.phone" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="家庭住址" :label-width="formLabelWidth">
        <el-input v-model="studentForm.address" auto-complete="off"></el-input>
        </el-form-item>
        
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="insertStudent(studentForm)">确 定</el-button>
        
    </div>
    </el-dialog>
        <el-form :inline="true">
            <el-form-item label="按学号查找" label-width="200px" style="">
                <el-input v-model="page.studentNum" auto-complete="off" ></el-input>
            </el-form-item>
            <el-button type="primary" @click="getStudentList(page.pageNum,page.studentNum)">查 找</el-button>
            <el-form-item  label="按姓名查找" label-width="200px">
                <el-input v-model="page.studentName" auto-complete="off" ></el-input>
            </el-form-item>
            <el-button type="primary" @click="getStudentList(page.pageNum,page.studentName)">查 找</el-button>
        </el-form>
    <el-button type="primary"  style="float:right;" @click="dialogFormVisible = true"><i class="el-icon-plus"></i>添加学生</el-button>
    <el-table
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        :data="studentList.records"
        style="width: 100%;"
        table-height="1000px">
        <el-table-column
        type="expand"
        width="20">
        <template slot-scope="prop">
            <el-form label-position="left"  class="demo-table-expand">
                <el-form-item label="学号:">
                    <span>{{ prop.row.studentNum }}</span>
                </el-form-item>
                <el-form-item label="姓名:">
                    <span>{{ prop.row.studentName }}</span>
                </el-form-item>
                <el-form-item label="性别:">
                    <span>{{ prop.row.sex }}</span>
                </el-form-item>
                <el-form-item label="联系方式：">
                    <span>{{ prop.row.phone }}</span>
                </el-form-item>
                <el-form-item label="家庭住址：">
                    <span>{{ prop.row.address }}</span>
                </el-form-item>
            </el-form>
        </template>

        </el-table-column>
        <el-table-column
        prop="studentNum"
        label="学号"
        width="150">
        </el-table-column>
        <el-table-column
        prop="studentName"
        label="姓名"
        width="120">
        </el-table-column>
        <el-table-column
        prop="sex"
        label="性别"
        width="80">
        </el-table-column>
        <el-table-column
        label="所住宿舍"
        width="160">
            <template slot-scope="scope">
                <span v-if="scope.row.dormNum!=''&&scope.row.dormNum!=null">{{scope.row.dormNum}}<span @click="outDorm(scope.row.id,scope.row.dormId)" style="cursor:pointer;margin-left:20px;color:#F56C6C">退宿</span> </span>
                <span @click="inForm.studentId=scope.row.id" v-else><a href="#" style="color:#409EFF" @click="inFormVisible =true">入住</a></span>
            </template>

        </el-table-column>



        <el-table-column
        prop="createTime"
        label="创建时间"
        width="170">
        </el-table-column>
        <el-table-column
        prop="updateTime"
        label="更新时间"
        width="160">
        </el-table-column>

        <el-table-column
        fixed="right"
        label="操作"
        prop="id"
        width="120">
        <template slot-scope="scope">
            <el-button
            type="text"
            @click="upId=scope.row.id ; upFormVisible =true ; getStudentById()"
            size="small">
            修改
            </el-button>
            <el-button
            @click.native.prevent="delStudent(scope.row.id)"
            type="text"
            size="small">
            删除
            </el-button>
        </template>
        </el-table-column>
        
    </el-table>
    
    <el-pagination
            background
            style="margin-top:20px;"
            layout="prev,pager,next"
            :page-size="studentList.size"
            :total="studentList.total"
            @current-change="handleCurrentChange">
    </el-pagination>
    

        <el-dialog title="学生入住" :visible.sync="inFormVisible" width="30%">
            <el-cascader
                v-model="inForm.dormId"
                :options="options"
                :props="{ expandTrigger: 'hover' }"
                @change="handleChange">
            </el-cascader>
            <div slot="footer" class="dialog-footer">
                <el-button @click="inFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="inDorm(inForm.studentId,inForm.dormId[1])">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="修改学生信息" :visible.sync="upFormVisible" width="30%">
    <el-form enctype="multipart/form-data">
        <el-form-item label="学号" :label-width="formLabelWidth">
        <el-input v-model="oneStudent.studentNum" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
        <el-input v-model="oneStudent.studentName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" :label-width="formLabelWidth">
        <el-select v-model="oneStudent.sex"  placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="联系方式" :label-width="formLabelWidth">
        <el-input v-model="oneStudent.phone" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="家庭住址" :label-width="formLabelWidth">
        <el-input v-model="oneStudent.address" auto-complete="off"></el-input>
        </el-form-item>
        
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="upFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateStudent(oneStudent)">确 定</el-button>
        
    </div>
    </el-dialog>
  </div>
</template>

<script>
import {getStudentList ,delStudent,insertStudent,outDorm,getOptions,inDorm,getStudentById,updateStudent} from '@/api/student'
import { Message } from 'element-ui'
export default {
    data(){
        return{
            loading:'true',
            upId:'',
            oneStudent:'',
            inForm:{
                dormId:'',
                studentId:''
            },
            options: '',
            studentList:'',
            upFormVisible:false,
            dialogFormVisible: false,
            inFormVisible: false,
            formLabelWidth: '80px',
            studentForm: {
                studentNum: '',
                studentName:'',
                sex: '',
                phone: '',
                address: '',
            },
            page:{
                pageNum:1,
                studentNum: '',
                studentName: '',
            }
            
        }
    },
    computed:{
       loading(){
         if(this.dormList!=''){
           return false;
         }
         return true;
       }
     },
    beforeMount(){
        this.getStudentList();
        this.getOptions();
    },
    methods:{
        updateStudent(oneStudent){
            return new Promise((resolve,reject)=>{
                updateStudent(oneStudent).then(res=>{
                    Message.success(res.message)
                    this.getStudentList()
                    this.upFormVisible = false
                }).catch(error=>{
                    reject(error)
                })
            })
        },
        getStudentById(){
            return new Promise((resolve,reject)=>{
                getStudentById(this.upId).then(res=>{
                    this.oneStudent = res.data;
                    console.log(res.data);
                    resolve()
                }).catch(error=>{
                    reject(error)
                })
            })
        },
        inDorm(studentId,dormId){
            return new Promise((resolve,reject)=>{
                    inDorm(studentId,dormId).then(res=>{
                        Message.success(res.message)
                        this.getStudentList(); 
                        this.inFormVisible = false;
                        resolve()
                    }).catch(error=>{
                        reject(error)
                    })
                })
        },
      getOptions(){
        return new Promise((resolve,reject)=>{
            getOptions().then(res=>{
                this.options = res.data; 
                resolve()
            }).catch(error=>{
                reject(error)
            })
        })
      },
      outDorm(studentId,dormId){
          this.$confirm('确认退房吗?', '提示', {
					type: 'warning'
				}).then(() => {
            return new Promise((resolve,reject)=>{
                    outDorm(studentId,dormId).then(res=>{
                        Message.success(res.message)
                        this.getStudentList(); 
                        resolve()
                    }).catch(error=>{
                        reject(error)
                    })
                })
                }).catch(() => {

				});
        },
        nextPage(){
        this.page.pageNum = this.studentList.nextPage;
        this.getStudentList();
      },
      prePage(){
        this.page.pageNum = this.studentList.prePage;
        this.getStudentList();
      },
      handleCurrentChange(val){
        this.page.pageNum = val;
        this.getStudentList();
      },
    getStudentList(){
            return new Promise((resolve,reject)=>{
                getStudentList(this.page.pageNum,this.page.studentNum,this.page.studentName).then(res=>{
                    var stuList = res.data
                    this.studentList = stuList
                    this.loading = false
                    resolve()
                }).catch(error=>{
                    reject(error)
                })
            })
        },
        insertStudent(studentForm){
            return new Promise((resolve,reject)=>{
                    insertStudent(studentForm).then(res=>{
                        Message.success(res.message)
                        this.getStudentList()
                        this.dialogFormVisible = false
                    }).catch(error=>{
                        reject(error)
                    })
                })
        },
        delStudent(studentId){

                this.$confirm('确认删除吗?', '提示', {
					type: 'warning'
				}).then(() => {
                    return new Promise((resolve,reject)=>{
                    delStudent(studentId).then(res=>{
                        Message.success("删除成功")
                        this.getStudentList();
                    }).catch(error=>{
                        reject(error)
                    })
                })
				}).catch(() => {

				});
            
        },
        handleChange() {
            console.log(this.inForm.studentId);
            console.log(this.inForm.dormId);
      }

    }
    

}
</script>
<style>
a{
    text-decoration: none;
}
.StudentList{
    margin-top: 10px;
}
.StudentList .el-table{
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
}
.demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;

  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;  
    text-align: left;

  }
</style>