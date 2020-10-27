package com.daizhiyuan.dms.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhu
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_dorm")
public class Dorm extends Model<Dorm> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @NotNull(message = "宿舍号不能为空")
    private Integer dormNum;
    @NotNull(message = "宿舍楼号不能为空")
    private Integer buildingNum;

    @JsonFormat( pattern = "yyyy-MM-dd  HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @JsonFormat( pattern = "yyyy-MM-dd  HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @NotNull(message = "请限制人数")
    @TableField(value = "`limit`")
    private int limit;

    private int inNum = 0;


    @Override
    protected Serializable pkVal() {
          return this.id;
      }

}
