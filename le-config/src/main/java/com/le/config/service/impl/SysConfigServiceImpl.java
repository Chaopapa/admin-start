package com.le.config.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.le.config.entity.SysConfig;
import com.le.config.entity.config.BaseConfig;
import com.le.config.mapper.SysConfigMapper;
import com.le.config.service.ISysConfigService;
import com.le.core.exception.LEException;
import com.le.core.rest.R;
import com.le.core.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 系统配置接口实现层
 **/
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Override
    @Cacheable(cacheNames = "config", key = "#clazz.name")
    public <T extends BaseConfig> T findConfig(Class<T> clazz) {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("param_key", clazz.getName());
        SysConfig sysConfig = baseMapper.selectOne(wrapper);

        if (sysConfig != null && StringUtils.isNotEmpty(sysConfig.getParamValue())) {
            return JsonUtils.toObject(sysConfig.getParamValue(), clazz);
        } else {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new LEException("配置实例化失败", e);
            }
        }
    }

    @Override
    @CacheEvict(cacheNames = "config", key = "#object.class.name")
    public R saveConfig(BaseConfig object) {
        String paramKey = object.getClass().getName();
        String paramValue = JsonUtils.toJson(object);

        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getParamKey, paramKey);

        SysConfig sysConfig = new SysConfig();
        sysConfig.setParamValue(paramValue);

        if (!retBool(baseMapper.update(sysConfig, wrapper))) {
            sysConfig.setParamKey(paramKey);
            baseMapper.insert(sysConfig);
        }

        return R.success();
    }
}
