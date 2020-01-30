package com.le.config.web;

import com.le.config.entity.config.*;
import com.le.config.service.ISysConfigService;
import com.le.core.rest.R;
import com.le.log.annotation.SystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 系统配置管理
 *
 * @author 严秋旺
 * @since 2019-1-3 17:27:28
 **/
@RestController
@RequestMapping("/admin/config")
public class ConfigController {

    @Autowired
    private ISysConfigService configService;

    @GetMapping("/global")
    @PreAuthorize("hasPermission(null,'config:global:view','aa')")
    @SystemLog("查看全局配置")
    public R global() {
        GlobalConfig config = configService.findConfig(GlobalConfig.class);
        return R.success().putData("config", config);
    }

    /**
     * 保存云存储配置
     *
     * @param config
     * @return
     */
    @PostMapping("/global")
    @ResponseBody
    @PreAuthorize("hasPermission(null,'config:global:edit')")
    @SystemLog("保存全局配置")
    public R global(@Valid GlobalConfig config) {
        configService.saveConfig(config);
        return R.success();
    }

    /**
     * 跳转云存储配置
     */
    @GetMapping("/oss")
    @PreAuthorize("hasPermission(null,'config:oss:view')")
    @SystemLog("查看云存储配置")
    public R oss() {
        CloudStorageConfig config = configService.findConfig(CloudStorageConfig.class);
        return R.success().putData("config", config);
    }

    /**
     * 保存云存储配置
     *
     * @param cloudStorageConfig
     * @return
     */
    @PostMapping("/oss")
    @ResponseBody
    @PreAuthorize("hasPermission(null,'config:oss:edit')")
    @SystemLog("保存云存储配置")
    public R oss(@Valid CloudStorageConfig cloudStorageConfig) {
        configService.saveConfig(cloudStorageConfig);
        return R.success();
    }

    /**
     * 短信配置
     *
     * @return
     */
    @GetMapping("/sms")
    @PreAuthorize("hasPermission(null,'config:sms:view')")
    @SystemLog("查看短信配置")
    public R sms() {
        SmsConfig config = configService.findConfig(SmsConfig.class);
        return R.success().putData("config", config);
    }

    @PostMapping("/sms")
    @ResponseBody
    @PreAuthorize("hasPermission(null,'config:sms:edit')")
    @SystemLog("保存短信配置")
    public R sms(@Valid SmsConfig smsConfig) {
        configService.saveConfig(smsConfig);
        return R.success();
    }

    /**
     * 广告图配置
     *
     * @return
     */
    @GetMapping("/banner")
    @PreAuthorize("hasPermission(null,'config:banner:view')")
    @SystemLog("查看广告图配置")
    public R banner() {
        BannerConfig config = configService.findConfig(BannerConfig.class);
        return R.success().putData("config", config);
    }

    @PostMapping("/cms")
    @ResponseBody
    @PreAuthorize("hasPermission(null,'config:banner:edit')")
    @SystemLog("保存广告图配置")
    public R banner(@Valid BannerConfig config) {
        configService.saveConfig(config);
        return R.success();
    }

    /**
     * 微信配置
     *
     * @return
     */
    @GetMapping("/wechat")
    @PreAuthorize("hasPermission(null,'config:wechat:view')")
    @SystemLog("查看微信配置")
    public R wechat() {
        WechatConfig config = configService.findConfig(WechatConfig.class);
        return R.success().putData("config", config);
    }

    @PostMapping("/wechat")
    @ResponseBody
    @PreAuthorize("hasPermission(null,'config:wechat:edit')")
    @SystemLog("保存微信配置")
    public R wechat(@Valid WechatConfig config) {
        configService.saveConfig(config);
        return R.success();
    }

    /**
     * 云打印机配置
     *
     * @return
     */
    @GetMapping("/print")
    @PreAuthorize("hasPermission(null,'config:print:view')")
    @SystemLog("查看云打印机配置")
    public R print() {
        PrintConfig config = configService.findConfig(PrintConfig.class);
        return R.success().putData("config", config);
    }

    @PostMapping("/print")
    @ResponseBody
    @PreAuthorize("hasPermission(null,'config:print:edit')")
    @SystemLog("保存云打印机配置")
    public R print(@Valid PrintConfig config) {
        configService.saveConfig(config);
        return R.success();
    }

}
