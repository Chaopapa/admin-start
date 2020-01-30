package com.le.cs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.le.core.base.SuperEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author WXY
 * @since 2019-05-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cs_chat_record")
public class ChatRecord extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 平台用户ID
     */
    private Long openUserId;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 应用类型
     */
    private String appType;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 发送内容
     */
    private String msgContent;

    /**
     * 发送类型
     */
    private String sendType;

    /**
     * 发送人ID
     */
    private Long senderId;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;


}
