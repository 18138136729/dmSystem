package com.daizhiyuan.dms.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("tb_settings")
public class Settings extends Model<Settings> {

    private static final long serialVersionUID=1L;

      /**
     * ID
     */
        @TableId(value = "id", type = IdType.ASSIGN_ID)
      private String id;

      /**
     * 键
     */
      @TableField(value = "`key`")
      private String key;

      /**
     * 值
     */
      @TableField(value = "`value`")
      private String value;

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
