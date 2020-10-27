package com.daizhiyuan.dms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daizhiyuan.dms.entity.Dorm;
import com.daizhiyuan.dms.mapper.DormMapper;
import com.daizhiyuan.dms.response.Response;
import com.daizhiyuan.dms.service.DormService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhu
 * @since 2020-10-19
 */
@Service
public class DormServiceImpl extends ServiceImpl<DormMapper, Dorm> implements DormService {
    @Autowired
    private DormMapper dormMapper;

    @Override
    public Response getListDorm(int page,String buildingNum) {
        page = BaseService.checkPage(page);
        Page<Dorm> pager = new Page<Dorm>(page,8);
        if (buildingNum!=null&&buildingNum!=""){
            Page<Dorm> dormPage = dormMapper.selectPage(pager,new QueryWrapper<Dorm>().eq("building_num",buildingNum).orderByDesc("dorm_num"));
            return Response.succ("获取宿舍列表成功").setData(dormPage);
        }

        Page<Dorm> dormPage = dormMapper.selectPage(pager,new QueryWrapper<Dorm>().orderByAsc("building_num").orderByAsc("dorm_num"));
        return Response.succ("获取宿舍列表成功").setData(dormPage);
    }

    @Override
    public Response getBuildingTag() {
        List<Integer> buildingTag = dormMapper.findBuildingTag();
        ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> all = new HashMap<>();
        all.put("text","全部");
        all.put("value","");
        result.add(all);
        for (int i = 0; i < buildingTag.size(); i++) {
            HashMap<String,String> map = new HashMap<>();
            map.put("text",buildingTag.get(i).toString());
            map.put("value",buildingTag.get(i).toString());
            result.add(map);
        }
        return Response.succ().setData(result);
    }

    @Override
    public Response insertDorm(Dorm dorm) {
        int dormNum = dorm.getDormNum();
        int buildingNum = dorm.getBuildingNum();
        Dorm sameDorm = dormMapper.findSameDorm(dormNum, buildingNum);
        if (sameDorm!=null){
            return Response.fail("该宿舍已存在");
        }
        int insert = dormMapper.insert(dorm);
        if (insert>0) {
            return Response.succ("添加宿舍信息成功");
        }
        return Response.fail("添加宿舍信息失败");
    }

    @Override
    public Response getDormOption() {
        List<HashMap<String, String>> dormOption = dormMapper.getDormOption();

        ArrayList<Long> inNum = new ArrayList<>();
        ArrayList<Long> limitNum = new ArrayList<>();
        ArrayList<String> buildingNum = new ArrayList<>();
        for (int i = 0;i<dormOption.size();i++){
            Long inNum1 =Long.valueOf(dormOption.get(i).get("inNum"));
            inNum.add(inNum1);
            Long limitNum1 = Long.valueOf(dormOption.get(i).get("limitNum"));
            limitNum.add(limitNum1);
            String buildingNum1 = dormOption.get(i).get("building_num");
            buildingNum.add(buildingNum1);
        }
        ArrayList<HashMap<String,Object>> result = new ArrayList<>();
        HashMap<String,Object> resule1 = new HashMap<>();
        resule1.put("inNum",inNum);
        resule1.put("limitNum",limitNum);
        resule1.put("buildingNum",buildingNum);
        result.add(resule1);
        return Response.succ().setData(result);
    }
}
