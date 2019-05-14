package com.le.miniapp.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import com.le.miniapp.config.WxMaConfiguration;
import com.le.miniapp.service.IMiniAppService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * @author:zqj
 * @createTime:2019/5/9
 */
@Service
@Slf4j
public class MiniAppService implements IMiniAppService {

    @Override
    public String checkSignature(String appid, String signature, String timestamp, String nonce, String echostr) {
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    @Override
    public String receivingServiceMessages(String appid, String requestBody, String msgSignature, String signature, String timestamp, String nonce, String echostr, String encryptType) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        final boolean isJson = Objects.equals(wxService.getWxMaConfig().getMsgDataFormat(),
                WxMaConstants.MsgDataFormat.JSON);
        if (StringUtils.isBlank(encryptType)) {
            // 明文传输的消息
            WxMaMessage inMessage;
            if (isJson) {
                inMessage = WxMaMessage.fromJson(requestBody);
            } else {//xml
                inMessage = WxMaMessage.fromXml(requestBody);
            }

            return "success";
        }

        if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            WxMaMessage inMessage;
            if (isJson) {
                inMessage = WxMaMessage.fromEncryptedJson(requestBody, wxService.getWxMaConfig());
            } else {//xml
                inMessage = WxMaMessage.fromEncryptedXml(requestBody, wxService.getWxMaConfig(),
                        timestamp, nonce, msgSignature);
            }
            return "success";
        }
        log.error("不可识别的加密类型：" + encryptType);
        throw new RuntimeException("不可识别的加密类型：" + encryptType);
    }

    @Override
    public void sendMessage(WxMaMessage message, String appid) {
        try {
            WxMaConfiguration.getRouters().get(appid).route(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public WxMaJscode2SessionResult login(String appid, String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        WxMaJscode2SessionResult session = null;
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        try {
            session = wxService.getUserService().getSessionInfo(code);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
        }
        return session;
    }

    @Override
    public WxMaUserInfo getUserInfo(String appid, String sessionKey,
                                    String signature, String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return null;
        }
        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        return userInfo;

    }

    @Override
    public WxMaPhoneNumberInfo getPhoneNoInfo(String appid, String sessionKey, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        // 用户信息校验
//        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
//            return null;
//        }
        return wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
    }

    @Override
    public WxMaJscode2SessionResult jsCode2SessionInfo(String appid, String code) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        try {
            return wxService.jsCode2SessionInfo(code);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public WxMediaUploadResult uploadMedia(String appid, File file) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        WxMediaUploadResult uploadResult = null;
        try {
            uploadResult = wxService.getMediaService().uploadMedia(WxMaConstants.KefuMsgType.IMAGE, file);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
        }
        return uploadResult;
    }
}
