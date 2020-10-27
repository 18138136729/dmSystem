package com.daizhiyuan.dms.mapper;

import com.daizhiyuan.dms.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhu
 * @since 2020-10-19
 */
@Repository
public interface StudentMapper extends BaseMapper<Student> {
    @Select("SELECT COUNT(*) FROM tb_student WHERE `sex`='男'")
    int getMenNum();

    @Select("SELECT COUNT(*) FROM tb_student WHERE `sex`='女'")
    int getWomenNum();

    @Select("SELECT COUNT(*) FROM tb_student WHERE `dorm_id` = '' OR `dorm_id` is null")
    int getOutDormStudentCount();

    @Select("SELECT COUNT(*) FROM tb_student WHERE `dorm_id` != ''")
    int getInDormStudentCount();
}
