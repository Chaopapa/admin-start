package com.le.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.le.core.rest.R;
import com.le.system.entity.SysLog;
import com.le.system.entity.vo.SysLogSearch;
import com.le.web.annotation.SystemLog;

/**
 * @ClassName ISysLogService
 * @Author lz
 * @Description 系统日志接口层
 * @Date 2018/10/9 11:32
 * @Version V1.0
 **/
public interface ISysLogService extends IService<SysLog> {

    /**
     * 日志分页
     **/
    R getPageList(Page<SysLog> page, SysLogSearch search);

    /**
     * 同步保存日志
     *
     * @param userId
     * @param userName
     * @param name
     * @param operation
     * @param time
     */
    void syncLog(Long userId, String userName, String name, String operation, Long time);

    /**
     * 异步保存日志
     *
     * @param url
     * @param ip
     * @param systemLog 日志注解
     * @param time      执行时间
     */
    void asyncLog(String url, String ip, SystemLog systemLog, Long time);

    /**
     * 保存日志
     *
     * @param url
     * @param ip
     * @param userId
     * @param userName
     * @param name
     * @param operation
     * @param time
     */
    void asyncLog(String url, String ip, Long userId, String userName, String name, String operation, Long time);

}
