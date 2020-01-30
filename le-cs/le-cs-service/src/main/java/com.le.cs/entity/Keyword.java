package com.le.cs.entity;

import com.le.core.base.SuperEntity;
import com.le.cs.entity.enums.KeywordType;
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
public class Keyword extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 内容类型
     */
    private KeywordType type;


}
