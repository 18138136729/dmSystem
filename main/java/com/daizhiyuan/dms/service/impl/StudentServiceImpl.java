package com.daizhiyuan.dms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daizhiyuan.dms.entity.Dorm;
import com.daizhiyuan.dms.entity.Student;
import com.daizhiyuan.dms.entity.User;
import com.daizhiyuan.dms.mapper.DormMapper;
import com.daizhiyuan.dms.mapper.StudentMapper;
import com.daizhiyuan.dms.response.Response;
import com.daizhiyuan.dms.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import sun.invoke.empty.Empty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhu
 * @since 2020-10-19
 */
@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private DormMapper dormMapper;
    @Override
    public Response getListStudent(int page, Long studentNum, String studentName) {
        page = BaseService.checkPage(page);
        Page pager = new Page<Student>(page, 8);

        if (studentNum!=null){
            Page<Student> articlePage = studentMapper.selectPage(pager,
                    new QueryWrapper<Student>().like("student_num",studentNum));
            return Response.succ("获取学生列表成功").setData(articlePage);
        }
        if (studentName!=null){
            Page<Student> articlePage = studentMapper.selectPage(pager,
                    new QueryWrapper<Student>().like("student_name",studentName));
            return Response.succ("获取学生列表成功").setData(articlePage);
        }
        Page<Student> articlePage = studentMapper.selectPage(pager,
        new QueryWrapper<Student>().orderByAsc("student_num"));
        return Response.succ("获取学生列表成功").setData(articlePage);
    }

    @Override
    public Response deleteStudent(Long studentId) {
        int result = studentMapper.deleteById(studentId);
        if (result == 0) {
            return Response.fail("删除失败");
        }
        return Response.succ("删除成功");
    }

    @Override
    public Response insertStudent(Student student) {
        Long studentNum = student.getStudentNum();
        Student isAlive = student.selectOne(new QueryWrapper<Student>().eq("student_num", studentNum));
        if (isAlive!=null){
            return Response.fail("学号已存在");
        }
        int insert = studentMapper.insert(student);
        if (insert>0) {
            return Response.succ("添加学生成功");
        }
        return Response.fail("添加失败");
    }

    @Override
    public Response inDorm( int studentId, @Validated @NotBlank(message="请选择入住的宿舍") String dormId) {
        Student student = studentMapper.selectById(studentId);
        String studentDormId = student.getDormId();
        if (studentDormId != null&&studentDormId == "") {
            return Response.fail("请先退房");
        }
        Dorm dorm = dormMapper.selectById(dormId);
        if (dorm.getInNum() >= dorm.getLimit()) {
            return Response.fail("宿舍已满员");
        }
        student.setDormNum(dorm.getBuildingNum()+"-"+dorm.getDormNum());
        student.setDormId(dormId);
        int update = studentMapper.updateById(student);
        if (update > 0) {
            dorm.setInNum(dorm.getInNum() + 1);
            dormMapper.updateById(dorm);
            return Response.succ("入住成功");
        }
        return Response.fail("未知错误");
    }

    @Override
    public Response outDorm(int studentId, int dormId) {
        Student student = studentMapper.selectById(studentId);
        Dorm dorm = dormMapper.selectById(dormId);
        student.setDormId("");
        student.setDormNum("");
        int i = studentMapper.updateById(student);
        if (i>0){
            dorm.setInNum(dorm.getInNum()-1);
            int result = dormMapper.updateById(dorm);
            if (result>0){
                return Response.succ("退房成功!");
            }
        }
        return null;
    }

    @Override
    public Response getOptions() {
        List<String> buildingTag = dormMapper.getBuildingTag();
        ArrayList<HashMap<String,Object>> result = new ArrayList<>();
        for (int i = 0;i<buildingTag.size();i++){
            HashMap<String,Object> one = new HashMap<>();
            one.put("label",buildingTag.get(i)+"栋宿舍楼");
            one.put("value","");
            result.add(one);

            List<Dorm> dormByBuilding = dormMapper.getDormByBuilding(buildingTag.get(i));
            ArrayList<HashMap<String,Object>> result2 = new ArrayList<>();
            for (int j=0;j<dormByBuilding.size();j++){
                HashMap<String,Object> e = new HashMap<>();
                e.put("label",dormByBuilding.get(j).getDormNum()+"号宿舍");
                e.put("value",dormByBuilding.get(j).getId());
                result2.add(e);
            }
            one.put("children",result2);
        }
        return Response.succ().setData(result);
    }

    @Override
    public Response updateStudent(Student student) {
        studentMapper.updateById(student);
        return Response.succ("修改学生信息成功");
    }

    @Override
    public Response getSexOption() {
        int menNum = studentMapper.getMenNum();
        int womenNum = studentMapper.getWomenNum();
        ArrayList<HashMap<String,Object>> sexOption = new ArrayList<>();
        HashMap<String,Object> total1 = new HashMap<>();
        total1.put("value",menNum);
        total1.put("name","男");
        sexOption.add(total1);
        HashMap<String,Object> total2 = new HashMap<>();
        total2.put("value",womenNum);
        total2.put("name","女");
        sexOption.add(total2);
        return Response.succ().setData(sexOption);
    }

    @Override
    public Response getInDormOption() {
        int inDormStudentCount = studentMapper.getInDormStudentCount();
        int outDormStudentCount = studentMapper.getOutDormStudentCount();
        ArrayList<HashMap<String,Object>> InDormOption = new ArrayList<>();
        HashMap<String,Object> total1 = new HashMap<>();
        total1.put("value",inDormStudentCount);
        total1.put("name","已入住");
        InDormOption.add(total1);
        HashMap<String,Object> total2 = new HashMap<>();
        total2.put("value",outDormStudentCount);
        total2.put("name","未入住");
        InDormOption.add(total2);
        return Response.succ().setData(InDormOption);
    }

}
