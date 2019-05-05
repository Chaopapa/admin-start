package com.le.system.entity.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author 严秋旺
 * @since 2019-05-05 9:00
 **/
public enum TokenType implements IEnum<String> {

    SYSTEM("SYSTEM", "系统", 20, ChronoUnit.MINUTES, TimeUnit.MINUTES),
    BIZ("BIZ", "业务", 0, ChronoUnit.MINUTES, TimeUnit.MINUTES);

    private String value;
    private String desc;
    private long expire;
    private TemporalUnit temporalUnit;
    private TimeUnit timeUnit;

    TokenType(final String value, final String desc, final long expire, final TemporalUnit temporalUnit, final TimeUnit timeUnit) {
        this.value = value;
        this.desc = desc;
        this.expire = expire;
        this.temporalUnit = temporalUnit;
        this.timeUnit = timeUnit;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String getDesc() {
        return this.desc;
    }

    public long getExpire() {
        return expire;
    }

    public TemporalUnit getTemporalUnit() {
        return temporalUnit;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
