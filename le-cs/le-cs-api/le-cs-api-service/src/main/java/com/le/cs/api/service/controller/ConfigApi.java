package com.le.cs.api.service.controller;

import com.le.config.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/sysUser",produces = "application/json;chartset=UTF-8")
public class ConfigApi {
    @Autowired
    ISysConfigService sysConfigService;

//    public R getWXConfig(){
////        return  sysConfigService.getWXConfig();
//    }
}
