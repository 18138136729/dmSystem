package com.daizhiyuan.dms.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhu
 * @since 2020-10-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_user")
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

      /**
     * ID
     */
        @TableId(value = "id", type = IdType.ASSIGN_ID)
      private String id;

      /**
     * 用户名
     */
      @NotBlank(message = "用户名不能为空")
      private String userName;

      /**
     * 密码
     */
      @NotBlank(message = "密码不能为空")
      private String password;

      /**
     * 角色
     */
      private String roles;
      /**
     * 状态：0表示删除，1表示正常
     */
      private String state = "1";

      /**
     * 创建时间
     */
      @JsonFormat( pattern = "yyyy-MM-dd  HH:mm:ss")
      @TableField(fill = FieldFill.INSERT)
      private Date createTime;

      /**
     * 更新时间
     */
      @JsonFormat( pattern = "yyyy-MM-dd  HH:mm:ss")
      @TableField(fill = FieldFill.INSERT_UPDATE)
      private Date updateTime;


    @Override
    protected Serializable pkVal() {
          return this.id;
      }

}
