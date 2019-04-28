package com.le.component.freemarker;

import com.le.config.entity.config.CloudStorageConfig;
import com.le.config.entity.config.CmsConfig;
import com.le.config.entity.config.GlobalConfig;
import com.le.config.entity.config.SmsConfig;
import com.le.config.service.ISysConfigService;
import com.le.web.annotation.FreemarkerSupport;
import freemarker.template.TemplateMethodModelEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 严秋旺
 * @since 2019-04-01 22:23
 **/
@Component
@FreemarkerSupport("config")
public class ConfigFreemarkerComponent implements TemplateMethodModelEx {
    @Autowired
    private ISysConfigService configService;

    @Override
    public Object exec(List arguments) {
        String key = String.valueOf(arguments.get(0));
        switch (key) {
            case "global":
                return configService.findConfig(GlobalConfig.class);
            case "oss":
                return configService.findConfig(CloudStorageConfig.class);
            case "cms":
                return configService.findConfig(CmsConfig.class);
            case "sms":
                return configService.findConfig(SmsConfig.class);
            default:
                return null;
        }
    }
}
