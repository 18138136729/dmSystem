package com.daizhiyuan.dms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daizhiyuan.dms.entity.Dorm;
import com.daizhiyuan.dms.mapper.DormMapper;
import com.daizhiyuan.dms.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController

public class test {
//    @Autowired
//    private DormMapper dormMapper;
//    @GetMapping("test")
//    public Response test(){
//        List<String> buildingTag = dormMapper.getBuildingTag();
//        ArrayList<HashMap<String,Object>> result = new ArrayList<>();
//        for (int i = 0;i<buildingTag.size();i++){
//            HashMap<String,Object> one = new HashMap<>();
//            one.put("label",buildingTag.get(i)+"栋宿舍楼");
//            one.put("value",buildingTag.get(i));
//            result.add(one);
//
//            List<String> dormByBuilding = dormMapper.getDormByBuilding(buildingTag.get(i));
//            ArrayList<HashMap<String,String>> result2 = new ArrayList<>();
//                for (int j=0;j<dormByBuilding.size();j++){
//                    HashMap<String,String> e = new HashMap<>();
//                    e.put("label",dormByBuilding.get(j)+"号宿舍");
//                    e.put("value",dormByBuilding.get(j));
//                    result2.add(e);
//                }
//                one.put("children",result2);
//        }
//        return Response.succ().setData(result);
//    }
}
