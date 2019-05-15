package com.le.cs.api.service.controller;


import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.le.core.rest.R;
import com.le.core.rest.RCode;
import com.le.cs.vo.AuthVo;
import com.le.cs.vo.LoginVo;
import com.le.cs.vo.RegisterVo;
import com.le.miniapp.service.IMiniAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author zqj
 * @since 2019-01-29 14:49
 **/
@RestController
@RequestMapping("/api/webchat")
public class XcxApi {

//    @Autowired
//    private IUserService userService;
    @Autowired
    private IMiniAppService miniAppService;
    /**
     * 获取openid
     *
     * @return
     */
    @RequestMapping("login")
    public R login(@Valid LoginVo loginVo) {
        WxMaJscode2SessionResult result = miniAppService.login("wxb443fc65c814424c", loginVo.getCode());
        return new R(RCode.unbind).putData("openid", result.getOpenid());
//        return userService.login(loginVo);
    }



    @RequestMapping("register")
    public R register(@Valid RegisterVo registerVo) {

        return R.success();
    }

    @RequestMapping("auth")
    public R auth(@Valid AuthVo authVo) throws IOException {
        WxMaJscode2SessionResult sessionInfo = miniAppService.jsCode2SessionInfo("wxb443fc65c814424c", authVo.getCode());
        WxMaPhoneNumberInfo phoneNumberInfo = miniAppService.getPhoneNoInfo("wxb443fc65c814424c", sessionInfo.getSessionKey(), authVo.getEncryptedData(), authVo.getIv());
        return R.success().putData("data", phoneNumberInfo);
    }

}

