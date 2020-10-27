package com.daizhiyuan.dms.service;

import com.daizhiyuan.dms.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.daizhiyuan.dms.response.Response;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhu
 * @since 2020-10-19
 */
@Service
public interface StudentService extends IService<Student> {

    Response getListStudent(int page, Long studentNum, String studentName);

    Response deleteStudent(Long studentId);

    Response insertStudent(Student student);

    Response inDorm(int studentId,String dormId);

    Response outDorm(int studentId,int dormId);

    Response getOptions();

    Response updateStudent(Student student);

    Response getSexOption();

    Response getInDormOption();
}
