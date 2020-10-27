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
import org.springframework.format.annotation.DateTimeFormat;

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
@TableName("tb_student")
public class Student extends Model<Student> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 学号
     */
      @NotNull(message = "学号不能为空")
      private Long studentNum;

      @NotBlank(message = "学生姓名不能为空")
      private String studentName;


    @JsonFormat( pattern = "yyyy-MM-dd  HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    @JsonFormat( pattern = "yyyy-MM-dd  HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @NotBlank(message = "学生性别不能为空")
    private String sex;

    @NotBlank(message = "家庭住址不能为空")
    private String address;

    private Long phone;

    private String dormId;


    private String dormNum;


    @Override
    protected Serializable pkVal() {
          return this.id;
      }

}
