package com.le.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.le.core.base.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @ClassName SystemLog
 * @Author lz
 * @Description 日志表
 * @Date 2018/10/9 11:42
 * @Version V1.0
 **/
@Data
@TableName("sys_log")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysLog extends SuperEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 登录名
     */
    private String userName;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 操作内容
     */
    private String operation;
    /**
     * 访问地址
     */
    private String url;
    /**
     * 响应时间(ms)
     */
    private Long time;
    /**
     * 访问IP
     */
    private String ip;

}
