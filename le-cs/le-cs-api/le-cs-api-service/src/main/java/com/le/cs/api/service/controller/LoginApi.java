package com.le.cs.api.service.controller;

import com.le.core.rest.R;
import com.le.cs.vo.LoginServiceVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/service")
public class LoginApi {


    @RequestMapping("login")
    public R login(@Valid LoginServiceVo loginVO) {

        /*return userService.login(loginVO);*/
        return R.success();
    }
}
