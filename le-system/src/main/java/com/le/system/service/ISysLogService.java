package com.le.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.le.core.rest.R;
import com.le.system.entity.SysLog;
import com.le.system.entity.vo.SysLogSearch;

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
     * @param url       访问地址
     * @param ip        访问IP
     * @param userId    用户id
     * @param userName  登录名
     * @param name      用户名称
     * @param operation 操作内容
     * @param time      操作时长
     */
    void syncLog(String url, String ip, Long userId, String userName, String name, String operation, Long time);

    /**
     * 保存日志
     *
     * @param url       访问地址
     * @param ip        访问IP
     * @param userId    用户id
     * @param userName  登录名
     * @param name      用户名称
     * @param operation 操作内容
     * @param time      操作时长
     */
    void asyncLog(String url, String ip, Long userId, String userName, String name, String operation, Long time);

}
