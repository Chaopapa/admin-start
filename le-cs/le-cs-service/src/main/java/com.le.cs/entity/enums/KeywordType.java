package com.le.cs.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @ClassName KeywordType
 * @Author wxy
 * @Description 关键字内容类型枚举
 * @Date 2018/10/10 11:22
 * @Version V1.0
 **/
public enum KeywordType implements IEnum<String> {
    TEXT("TEXT", "文本"),
    CARD("CARD", "小卡片"),
    IMAGE("IMAGE", "图片");

    private String value;
    private String desc;

    KeywordType(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }
}
