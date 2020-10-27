package com.daizhiyuan.dms.mapper;

import com.daizhiyuan.dms.entity.RefreshToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daizhiyuan.dms.entity.Settings;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2020-10-17
 */
@Repository
@Mapper
public interface RefreshTokenMapper extends BaseMapper<RefreshToken> {
    @Delete("DELETE FROM tb_refresh_token WHERE `user_id` = #{userId}")
    void deleteAllByUserId(@Param("userId")String userId);

    @Select("select * FROM tb_refresh_token WHERE `token_key` = #{tokenKey}")
    RefreshToken findOneByTokenKey(@Param("tokenKey")String tokenKey);

    @Delete("DELETE FROM tb_refresh_token WHERE `token_key` = #{tokenKey}")
    int deleteAllByTokenKey(@Param("tokenKey")String tokenKey);
}
