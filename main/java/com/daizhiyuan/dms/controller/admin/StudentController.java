package com.daizhiyuan.dms.controller.admin;


import com.daizhiyuan.dms.entity.Student;
import com.daizhiyuan.dms.response.Response;
import com.daizhiyuan.dms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhu
 * @since 2020-10-19
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PreAuthorize("@permission.adminAction()")
    @GetMapping("/list")
    public Response ListStudent(@RequestParam("page")int page,
                                @RequestParam(value = "studentNum",required = false)Long studentNum,
                                @RequestParam(value = "studentName",required = false)String studentName){
        return studentService.getListStudent(page,studentNum,studentName);
    }


    @PreAuthorize("@permission.adminAction()")
    @DeleteMapping("/{studentId}")
    public Response delStudent(@PathVariable("studentId")Long studentId){
        return studentService.deleteStudent(studentId);
    }

    @PreAuthorize("@permission.adminAction()")
    @GetMapping("studentById")
    public Response studentById(@RequestParam("studentId")Long studentId){
        return Response.succ("获取学生成功").setData(studentService.getById(studentId));
    }

    @PreAuthorize("@permission.adminAction()")
    @PostMapping("")
    public Response addStudent(@Validated @RequestBody Student student){
        return studentService.insertStudent(student);
    }

    @PreAuthorize("@permission.adminAction()")
    @PutMapping("")
    public Response upStudent(@Validated @RequestBody Student student){
        return studentService.updateStudent(student);
    }

    @PreAuthorize("@permission.adminAction()")
    @PostMapping("inDorm")
    public Response inDorm(@RequestParam("studentId") int studentId,@RequestParam("dormId")String dormId){
        return studentService.inDorm(studentId,dormId);
    }

    @PreAuthorize("@permission.adminAction()")
    @DeleteMapping("/outDorm")
    public Response outDorm(@RequestParam("studentId") int studentId,@RequestParam("dormId")int dormId){
        return studentService.outDorm(studentId,dormId);
    }

    @PreAuthorize("@permission.adminAction()")
    @GetMapping("getOptions")
    public Response getOptions(){
        return studentService.getOptions();
    }

    @PreAuthorize("@permission.adminAction()")
    @GetMapping("getSexOption")
    public Response getSexOption(){
        return studentService.getSexOption();
    }

    @PreAuthorize("@permission.adminAction()")
    @GetMapping("getInDormOption")
    public Response getInDormOption(){
        return studentService.getInDormOption();
    }




}

