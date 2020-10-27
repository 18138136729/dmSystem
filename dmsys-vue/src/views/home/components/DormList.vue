 <template>
    <div class="DormList">
        <el-dialog title="添加宿舍信息" :visible.sync="dialogFormVisible" width="40%">
      <el-form enctype="multipart/form-data" :inline="true" :model="dormForm" >
          <el-form-item label="宿舍号:" :label-width="formLabelWidth" >
          <el-input v-model="dormForm.dormNum" auto-complete="off" placeholder="如：101，201"></el-input>
          </el-form-item>
          <el-row :gutter="24">
            <el-form-item label="所属宿舍楼：" v-show="dormForm.newBuildingNum==''" :label-width="formLabelWidth">
              <el-select v-model="dormForm.buildingNum" clearable placeholder="请选择">
                <el-option
                  v-for="item in dormTag"
                  :key="item.value"
                  :label="item.text+'栋宿舍楼'"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="新建宿舍楼：" v-show="!isNewBuilding" label-width="110px" prop="newBuildingNum">
              <el-col :span="11">
            <el-input type="number" v-model.number="dormForm.newBuildingNum" auto-complete="off"></el-input>
            </el-col>
            </el-form-item>
          </el-row>
          
          <el-form-item label="可住人数:" :label-width="formLabelWidth" prop="limit">
          <el-input type="number" v-model.number="dormForm.limit" auto-complete="off"></el-input>
          </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="insertDorm()">确 定</el-button>
          
      </div>
      </el-dialog>
      <div style="float:left;margin:20px 0">
          <span>筛选宿舍楼:</span>
          <el-select @change="getDormListFileter()" v-model="page.buildingNum" placeholder="请选择">
            <el-option
              v-for="item in dormTag"
              :key="item.text"
              :label="item.text"
              :value="item.value">
            </el-option>
        </el-select>
  </div>
          <el-button type="primary"  style="float:right;margin:20px 0" @click="dialogFormVisible = true"><i class="el-icon-plus"></i>添加宿舍信息</el-button>
      <el-table
    v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    ref="filterTable"
    :data="dormList.records"
    @filter-change="getDormListFileter()"
    style="width: 100%;">
    <el-table-column
      prop="buildingNum"
      label="宿舍楼"
      width="150">
      <template slot-scope="scope">
          <span>{{scope.row.buildingNum}}栋宿舍楼</span>
      </template>
    </el-table-column>
    
    <el-table-column
      label="宿舍号"
      width="180">
      <template slot-scope="scope">
          <span>{{scope.row.buildingNum}}-{{scope.row.dormNum}}</span>
      </template>
    </el-table-column>
    <el-table-column
      prop="inNum"
      label="已住人数">
    </el-table-column>
    <el-table-column
      prop="limit"
      label="可住人数">
    </el-table-column>
    
  </el-table>
  <el-pagination
            background
            style="margin-top:20px;"
            layout="prev,pager,next"
            :page-size="dormList.size"
            :total="dormList.total"
            @current-change="handleCurrentChange">
    </el-pagination>
    </div>
</template>
 
 <script>
 import {getDorm , getDormTag,insertDorm} from '@/api/dorm' 
 import { Message } from 'element-ui'
 export default {
     data(){
       return{
             dormList:'',
             page:{
                pageNum:1,
                buildingNum:'',
            },
            dormForm:{
              dormNum:'',
              buildingNum:'',
              limit:'',
              newBuildingNum:'',
            },
            dormTag:'',
            dialogFormVisible:false,
            formLabelWidth: '100px',
         }
     },
         
     computed:{
       loading(){
         if(this.dormList!=''){
           return false;
         }
         return true;
       },
      isNewBuilding(){
        if(this.dormForm.buildingNum!=''){
          return true;
        }
        return false;
      }
     },
     beforeMount(){
        this.getDormList();
        this.getDormTag();
    },
     methods:{
       getDormListFileter(){
         this.page.pageNum = 1
         this.getDormList();
       },
       nextPage(){
        this.page.pageNum = this.dormList.nextPage;
        this.getDormList();
      },
      prePage(){
        this.page.pageNum = this.dormList.prePage;
        this.getDormList();
      },
      handleCurrentChange(val){
        this.page.pageNum = val;
        this.getDormList();
      },
       insertDorm(){
            return new Promise((resolve,reject)=>{
                    insertDorm(this.dormForm).then(res=>{
                        Message.success(res.message)
                        this.getDormList();
                        this.dialogFormVisible = false;
                    }).catch(error=>{
                      console.log(this.dormForm.buildingNum)
                      console.log(this.dormForm.newBuildingNum)
                        reject(error)
                    })
                })
        },
         getDormList(){
            return new Promise((resolve,reject)=>{
                getDorm(this.page.pageNum,this.page.buildingNum).then(res=>{
                    var dorm = res.data
                    this.dormList = dorm
                    resolve()
                }).catch(error=>{
                    reject(error)
                })
            })
        },
        getDormTag(){
            return new Promise((resolve,reject)=>{
                getDormTag().then(res=>{
                    var dorm = res.data
                    this.dormTag = dorm
                    resolve()
                }).catch(error=>{
                    reject(error)
                })
            })
        },
     },
 
 }
 </script>
 
<style>
input::-webkit-outer-spin-button,
    input::-webkit-inner-spin-button {
        -webkit-appearance: none;
    }
  .DormList .el-table{
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
  }
 </style>
