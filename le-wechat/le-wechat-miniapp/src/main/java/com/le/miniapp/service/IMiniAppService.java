package com.le.miniapp.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author:zqj
 * @createTime:2019/5/9
 */
public interface IMiniAppService {
  /**
   * 微信服务器的认证消息
   * @param signature
   * @param timestamp
   * @param nonce
   * @param echostr
   * @return
   */
  String  checkSignature(String appid,String signature, String timestamp, String nonce, String echostr);

  /**
   * 微信服务器消息接收
   * @param signature
   * @param timestamp
   * @param nonce
   * @param echostr
   * @return
   */
  String  receivingServiceMessages(String appid,String requestBody, String msgSignature,String signature, String timestamp, String nonce, String echostr, String encryptType);

  /**
   * 发送客服消息
   * @param message
   * @param appid
   */
  void sendMessage(WxMaMessage message,String appid);

  /**
   * 登录
   * @param appid
   * @param code
   * @return
   */
  WxMaJscode2SessionResult login(String appid, String code);

  /**
   * 获取用户信息
   * @param sessionKey
   * @param encryptedData
   * @param iv
   * @return
   */
  WxMaUserInfo getUserInfo(String appid, String sessionKey,
                           String signature, String rawData, String encryptedData, String iv);

  /**
   * 获取手机
   * @param appid
   * @param sessionKey
   * @param encryptedData
   * @param iv
   * @return
   */
  WxMaPhoneNumberInfo getPhoneNoInfo(String appid, String sessionKey,String encryptedData, String iv);

  /**
   * 获取sessionKey
   * @param appid
   * @param code
   * @return
   */
  WxMaJscode2SessionResult jsCode2SessionInfo(String appid,String code);

  /**
   * 上传素材
   * @param appid
   * @param file
   * @return
   */
  WxMediaUploadResult uploadMedia(String appid, File file);
}
