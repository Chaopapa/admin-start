package com.le.component.print;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 返回公共参数说明
 * <a href="http://www.feieyun.com/open/index.html">参考文档</a>
 *
 * @author 严秋旺
 * @since 2019-04-23 15:38
 **/
@Data
public class PrintResult implements Serializable {
    private static final long serialVersionUID = 444159607381359608L;
    /**
     * 返回码，正确返回0，【注意：结果正确与否的判断请用此返回参数】，错误返回非零。
     */
    private Integer ret;
    /**
     * 结果提示信息，正确返回”ok”，如果有错误，返回错误信息。
     */
    private String msg;
    /**
     * 数据类型和内容详看私有返回参数data，如果有错误，返回null。
     */
    private Object data;
    /**
     * 服务器程序执行时间，单位：毫秒。
     */
    private Long serverExecutedTime;

    public boolean isSuccess() {
        return ret == null || ret == 0;
    }

}
