package com.le.admin.config;


import com.le.config.entity.config.*;
import com.le.config.service.ISysConfigService;
import com.le.core.rest.R;
import com.le.web.annotation.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * 系统配置管理
 *
 * @author 严秋旺
 * @since 2019-1-3 17:27:28
 **/
@Controller
@RequestMapping("/admin/config")
public class SysConfigController {

    @Autowired
    private ISysConfigService configService;

    /**
     * 跳转云存储配置
     */
    @GetMapping("/global")
//    @RequiresPermissions("sys:config:global")
    @SystemLog("查看云存储配置")
    public String global(ModelMap model) {
        GlobalConfig config = configService.findConfig(GlobalConfig.class);
        model.put("config", config);
        return "admin/system/config/global";
    }

    /**
     * 保存云存储配置
     *
     * @param config
     * @return
     */
    @PostMapping("/global")
    @ResponseBody
//    @RequiresPermissions("sys:config:global")
    @SystemLog("保存云存储配置")
    public R global(@Valid GlobalConfig config) {
        return configService.saveConfig(config);
    }

    /**
     * 跳转云存储配置
     */
    @GetMapping("/oss")
//    @RequiresPermissions("sys:config:oss")
    @SystemLog("查看云存储配置")
    public String oss(ModelMap model) {
        CloudStorageConfig cloudStorageConfig = configService.findConfig(CloudStorageConfig.class);
        model.put("config", cloudStorageConfig);
        return "admin/system/config/oss";
    }

    /**
     * 保存云存储配置
     *
     * @param cloudStorageConfig
     * @return
     */
    @PostMapping("/oss")
    @ResponseBody
//    @RequiresPermissions("sys:config:oss")
    @SystemLog("保存云存储配置")
    public R oss(@Valid CloudStorageConfig cloudStorageConfig) {
        return configService.saveConfig(cloudStorageConfig);
    }

    /**
     * 短信配置
     *
     * @param model
     * @return
     */
    @GetMapping("/sms")
//    @RequiresPermissions("sys:config:sms")
    @SystemLog("查看短信配置")
    public String sms(ModelMap model) {
        SmsConfig config = configService.findConfig(SmsConfig.class);
        model.put("config", config);
        return "admin/system/config/sms";
    }

    @PostMapping("/sms")
    @ResponseBody
//    @RequiresPermissions("sys:config:sms")/
    @SystemLog("保存短信配置")
    public R sms(@Valid SmsConfig smsConfig) {
        return configService.saveConfig(smsConfig);
    }

    /**
     * 广告图配置
     *
     * @param model
     * @return
     */
    @GetMapping("/cms")
//    @RequiresPermissions("sys:config:cms")
    @SystemLog("查看广告图配置")
    public String cms(ModelMap model) {
        CmsConfig cmsConfig = configService.findConfig(CmsConfig.class);
        model.put("cmsConfig", cmsConfig);
        return "admin/system/config/cms";
    }

    @PostMapping("/cms")
    @ResponseBody
//    @RequiresPermissions("sys:config:cms")
    @SystemLog("保存广告图配置")
    public R cms(@Valid CmsConfig cmsConfig) {
        return configService.saveConfig(cmsConfig);
    }

    /**
     * 微信配置
     *
     * @param model
     * @return
     */
    @GetMapping("/wechat")
//    @RequiresPermissions("sys:config:wechat")
    @SystemLog("查看微信配置")
    public String wechat(ModelMap model) {
        WechatConfig config = configService.findConfig(WechatConfig.class);
        model.put("config", config);
        return "admin/system/config/wechat";
    }

    @PostMapping("/wechat")
    @ResponseBody
//    @RequiresPermissions("sys:config:wechat")
    @SystemLog("保存微信配置")
    public R wechat(@Valid WechatConfig config) {
        return configService.saveConfig(config);
    }

    /**
     * 云打印机配置
     *
     * @param model
     * @return
     */
    @GetMapping("/print")
//    @RequiresPermissions("sys:config:print")
    @SystemLog("查看云打印机配置")
    public String print(ModelMap model) {
        PrintConfig config = configService.findConfig(PrintConfig.class);
        model.put("config", config);
        return "admin/system/config/print";
    }

    @PostMapping("/print")
    @ResponseBody
//    @RequiresPermissions("sys:config:print")
    @SystemLog("保存云打印机配置")
    public R print(@Valid PrintConfig config) {
        return configService.saveConfig(config);
    }

}
