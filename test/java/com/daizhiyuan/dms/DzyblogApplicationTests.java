package com.daizhiyuan.dms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daizhiyuan.dms.entity.Dorm;
import com.daizhiyuan.dms.mapper.DormMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RestController
class DzyblogApplicationTests {

    @Autowired
    private DormMapper dormMapper;
//    @Test
//    void contextLoads() {
//        List<Dorm> dorms = dormMapper.selectList(new QueryWrapper<>());
//        HashMap<String,String>  result = new HashMap<>();
//        for (int i = 0;i < dorms.size();i++){
//            result.put("value",dorms.get(i).getId().toString());
//            result.put("label",dorms.get(i).getDormNum());
//            System.out.println(result.get("value"));
//            System.out.println(result.get("label"));
//        }
//    }

}
