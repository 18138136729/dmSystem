package com.daizhiyuan.dms.mapper;

import com.daizhiyuan.dms.entity.Dorm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daizhiyuan.dms.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2020-10-19
 */
@Repository
public interface DormMapper extends BaseMapper<Dorm> {
    @Select("SELECT building_num FROM tb_dorm GROUP BY `building_num` ORDER BY `building_num` ASC")
    List<Integer> findBuildingTag();

    @Select("SELECT * FROM tb_dorm WHERE `dorm_num` = #{dormNum} AND `building_num` = #{buildingNum}" )
    Dorm findSameDorm(int dormNum,int buildingNum);

    @Select("SELECT building_num FROM tb_dorm group by `building_num`")
    List<String> getBuildingTag();

    @Select("SELECT id,dorm_num FROM tb_dorm WHERE building_num = #{buildingNum}")
    List<Dorm> getDormByBuilding(String buildingNum);

    @Select("SELECT building_num as buildingNum,SUM(`limit`) as limitNum,SUM(`in_num`) as inNum FROM tb_dorm group by building_num")
    List<HashMap<String,String>> getDormOption();
}
