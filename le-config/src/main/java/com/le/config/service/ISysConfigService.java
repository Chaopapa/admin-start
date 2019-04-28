package com.le.config.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.le.config.entity.SysConfig;
import com.le.config.entity.config.BaseConfig;
import com.le.core.rest.R;

/**
 * 系统配置接口层
 **/
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 获取配置
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends BaseConfig> T findConfig(Class<T> clazz);

    /**
     * 保存配置
     *
     * @param object
     * @return
     */
    R saveConfig(BaseConfig object);
}
