package com.le.system.entity.vo;

import com.le.system.entity.SysLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 严秋旺
 * @since 2019-03-25 15:37
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysLogSearch extends SysLog implements Serializable {
    private static final long serialVersionUID = 8302639140354492575L;

    private LocalDateTime start;
    private LocalDateTime end;
}
