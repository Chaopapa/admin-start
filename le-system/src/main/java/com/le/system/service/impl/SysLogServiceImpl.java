package com.le.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.core.rest.R;
import com.le.system.entity.SysLog;
import com.le.system.entity.vo.SysLogSearch;
import com.le.system.mapper.SysLogMapper;
import com.le.system.service.ISysLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 日志接口实现层
 *
 * @author 严秋旺
 * @since 2018/10/9 11:33
 **/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @SuppressWarnings("unchecked")
    @Override
    public R getPageList(Page<SysLog> page, SysLogSearch search) {
        LambdaQueryWrapper<SysLog> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotEmpty(search.getUserName())) {
            wrapper.like(SysLog::getUserName, search.getUserName());
        }

        if (StringUtils.isNotEmpty(search.getName())) {
            wrapper.like(SysLog::getName, search.getName());
        }

        if (StringUtils.isNotEmpty(search.getUrl())) {
            wrapper.like(SysLog::getUrl, search.getUrl());
        }

        if (StringUtils.isNotEmpty(search.getOperation())) {
            wrapper.like(SysLog::getOperation, search.getOperation());
        }

        if (search.getStart() != null) {
            wrapper.gt(SysLog::getCreateDate, search.getStart());
        }

        if (search.getEnd() != null) {
            wrapper.lt(SysLog::getCreateDate, search.getEnd());
        }

        if (StringUtils.isNotEmpty(search.getIp())) {
            wrapper.like(SysLog::getIp, search.getIp());
        }

        wrapper.orderByDesc(SysLog::getCreateDate);
        IPage<SysLog> ipage = baseMapper.selectPage(page, wrapper);
        return R.success(ipage);
    }

    @Override
    public void syncLog(String url, String ip, Long userId, String userName, String name, String operation, Long time) {
        SysLog sysLog = new SysLog();
        sysLog.setUserId(userId)
                .setUserName(userName)
                .setName(name)
                .setOperation(operation)
                .setUrl(url)
                .setTime(time)
                .setIp(ip);
        baseMapper.insert(sysLog);
    }

    @Override
    @Async
    public void asyncLog(String url, String ip, Long userId, String userName, String name, String operation, Long time) {
        SysLog sysLog = new SysLog();
        sysLog.setUserId(userId)
                .setUserName(userName)
                .setName(name)
                .setOperation(operation)
                .setUrl(url)
                .setTime(time)
                .setIp(ip);
        baseMapper.insert(sysLog);
    }
}
