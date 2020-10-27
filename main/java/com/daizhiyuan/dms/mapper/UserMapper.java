package com.daizhiyuan.dms.mapper;

import com.daizhiyuan.dms.entity.User;
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
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM tb_user WHERE `user_name` = #{userName}")
    User findOneByUserName(@Param("userName") String userName);
}
