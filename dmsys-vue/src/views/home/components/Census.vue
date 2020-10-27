<template>
  <div class="Census">
      <el-row :gutter="24" style="border:1px solid black;">
          <el-col :span="24"><h1>统计报表</h1></el-col>
      </el-row>
      <el-row :gutter="24" style="border:1px solid black;">
          <el-col :span="12" style="border:1px solid black;"><div id="sex" style="width: 350px;height:300px;margin:0 auto;"></div></el-col>
          <el-col :span="12" style="border:1px solid black;"><div id="dorm" style="width: 350px;height:300px;margin:0 auto;"></div></el-col>
      </el-row>
      <el-row :gutter="24" style="border:1px solid black;">
          <el-col :span="24"><div id="buildingDorm" style="width: 100%;height:300px;margin:0 auto;"></div></el-col>
      </el-row>
  </div>
</template>

<script>
import {getSexOption,getInDormOption} from '@/api/census'
var echarts = require('echarts');
export default {
    data() {
      return {
          buildingDormOption:{
    xAxis: {
        data: ['一', '二', '三', '四'],
        axisTick: {show: false},
    },
    yAxis: {
        splitLine: {show: false}
    },
    animationDurationUpdate: 1200,
    series: [{
        type: 'bar',
        itemStyle: {
            normal: {
                color: '#ddd'
            }
        },
        silent: true,
        barWidth: 40,
        barGap: '-100%', // Make series be overlap
        data: [60, 60, 60, 60]
    }, {
        type: 'bar',
        barWidth: 40,
        z: 10,
        data: [45, 60, 13, 25]
    }]
},
        sexOptions:{
            color:['#5bc2e7', '#6980c5','#3390FF','#6e7074', '#546570', '#c4ccd3'],
            tooltip: {
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
            title: {
                text: "男女学生比例图",
                left: "center",
                top: "bottom",
                textStyle: {
                fontSize: 20
                }
            },
            series : [
                {
                    emphasis: {
                    label: {
                            show: true,
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    },
                    radius: ['30%', '70%'],
                    label: {
                    show: false,
                    position: 'center'
                },
                    name: '男女学生比例',
                    type: 'pie',
                    data:'',
                }
            ],
                
    },
    inDormOption:{
            color:['#70dfdf', '#3390FF','#6e7074', '#546570', '#c4ccd3'],
            tooltip: {
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
            title: {
                text: "入住/未入住比例图",
                left: "center",
                top: "bottom",
                textStyle: {
                fontSize: 20
                }
            },
            series : [
                {
                    emphasis: {
                    label: {
                            show: true,
                            fontSize: '20',
                            fontWeight: 'bold'
                        }
                    },
                    radius: ['30%', '70%'],
                    label: {
                    show: false,
                    position: 'center'
                },
                    name: '入住/未入住比例',
                    type: 'pie',
                    data:'',
                }
            ],
                
    },
    
}
    },
    created(){
        this.getSexOption()
        this.getInDormOption()
    },
    mounted(){
        var buildingDorm = echarts.init(document.getElementById('buildingDorm'));
        buildingDorm.setOption(this.buildingDormOption)
    },
    methods:{
        getSexOption(){
            return new Promise((resolve,reject)=>{
                getSexOption().then(res=>{
                    this.sexOptions.series[0].data=res.data
                    var sexChart = echarts.init(document.getElementById('sex'));
                    sexChart.setOption(this.sexOptions)
                    resolve()
                }).catch(error=>{
                    reject(error)
                })
            })
        },
        getInDormOption(){
            return new Promise((resolve,reject)=>{
                getInDormOption().then(res=>{
                    this.inDormOption.series[0].data=res.data
                    var dormChart = echarts.init(document.getElementById('dorm'));
                    dormChart.setOption(this.inDormOption)
                    resolve()
                }).catch(error=>{
                    reject(error)
                })
            })
        },
        
        
    }
}
</script>

<style>

</style>