package com.daizhiyuan.dms.mapper;

import com.daizhiyuan.dms.entity.Settings;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2020-10-11
 */
@Repository
public interface SettingsMapper extends BaseMapper<Settings> {
    @Select("SELECT * FROM tb_settings WHERE `key` = #{key}")
    Settings findOneByKey(@Param("key")String key);
}
