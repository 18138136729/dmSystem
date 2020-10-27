package com.daizhiyuan.dms.controller.admin;


import com.daizhiyuan.dms.entity.Dorm;
import com.daizhiyuan.dms.entity.Student;
import com.daizhiyuan.dms.response.Response;
import com.daizhiyuan.dms.service.DormService;
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
@RequestMapping("/dorm")
public class DormController {
    @Autowired
    private DormService dormService;

    @PreAuthorize("@permission.adminAction()")
    @GetMapping("/tag")
    public Response BuildingTag(){
        return dormService.getBuildingTag();
    }

    @PreAuthorize("@permission.adminAction()")
    @GetMapping("/list")
    public Response ListDorm(@RequestParam("page")int page,@RequestParam(value = "buildingNum",required = false)String buildingNum){
        return dormService.getListDorm(page,buildingNum);
    }

    @PreAuthorize("@permission.adminAction()")
    @PostMapping("")
    public Response addDorm(@Validated @RequestBody Dorm dorm){
        return dormService.insertDorm(dorm);
    }

    @PreAuthorize("@permission.adminAction()")
    @GetMapping("getDormOption")
    public Response getDormOption(){
        return dormService.getDormOption();
    }

}

