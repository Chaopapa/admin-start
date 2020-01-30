package com.le.cs.api.service.controller;

import com.le.miniapp.service.IMiniAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zqj
 */
@RestController
@RequestMapping("/api/webchat/message/")
@Slf4j
public class XcxMessageApi {

    @Autowired
    private IMiniAppService miniAppService;

    @GetMapping(produces = "text/plain;charset=utf-8", value = "{appid}")
    public String authGet(@PathVariable String appid,
                          @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {
        return miniAppService.checkSignature(appid, signature, timestamp, nonce, echostr);
    }

    @PostMapping(produces = "application/xml; charset=UTF-8", value = "{appid}")
    public String post(@PathVariable String appid,
                       @RequestBody String requestBody,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature,
                       @RequestParam(name = "encrypt_type", required = false) String encryptType,
                       @RequestParam(name = "signature", required = false) String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce) {
        log.info("\n接收微信请求：[msg_signature=[{}], encrypt_type=[{}], signature=[{}]," +
                        " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                msgSignature, encryptType, signature, timestamp, nonce, requestBody);
        return miniAppService.receivingServiceMessages(appid, requestBody, msgSignature, signature, timestamp, nonce, encryptType);
    }

}
