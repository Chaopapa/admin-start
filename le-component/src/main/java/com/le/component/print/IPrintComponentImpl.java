package com.le.component.print;

import com.le.config.entity.config.PrintConfig;
import com.le.config.service.ISysConfigService;
import com.le.core.properties.LongeProperties;
import com.le.core.util.HttpUtils;
import com.le.core.util.JsonUtils;
import com.le.core.util.template.TemplateUtil;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 严秋旺
 * @since 2019-04-23 14:00
 **/
@Slf4j
@Component
public class IPrintComponentImpl implements IPrintComponent {
    private static final String URL = "http://api.feieyun.cn/Api/Open/";

    @Autowired
    private LongeProperties longeProperties;
    @Autowired
    private ISysConfigService configService;

    @Override
    public boolean print(Map<String, Object> model) throws IOException, TemplateException {
        return print(model, 1);
    }

    @Override
    public boolean print(Map<String, Object> model, int times) throws IOException, TemplateException {
        PrintConfig config = configService.findConfig(PrintConfig.class);
        String content = TemplateUtil.stringTplValue(config.getTpl(), model);

        Map<String, Object> params = new HashMap<>();
        params.put("content", content);
        params.put("times", times);//打印联数
        params.put("apiname", "Open_printMsg");//固定值,不需要修改

        PrintResult printResult = requestApi(config, params);
        return printResult.isSuccess();
    }

    @Override
    public boolean cancel() throws IOException {
        PrintConfig config = configService.findConfig(PrintConfig.class);

        Map<String, Object> params = new HashMap<>();
        params.put("apiname", "Open_delPrinterSqs");//固定值,不需要修改

        PrintResult printResult = requestApi(config, params);
        return printResult.isSuccess();
    }

    /**
     * 调用云服务接口
     *
     * @param config
     * @param params
     * @return
     * @throws IOException
     */
    private PrintResult requestApi(PrintConfig config, Map<String, Object> params) throws IOException {
        params.put("user", config.getUser());
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("stime", time);
        params.put("sig", signature(config, time));
        params.put("sn", config.getSn());

        if (longeProperties.getDebug()) {
            params.put("debug", "1");
        }

        log.debug("请求参数：{}", params);
        String result = HttpUtils.post(URL, params);
        log.debug("响应结果：{}", result);

        PrintResult printResult = JsonUtils.toObject(result, PrintResult.class);
        return printResult;
    }

    /**
     * 生成签名字符串
     *
     * @param config 配置
     * @param time   当前UNIX时间戳，10位，精确到秒
     * @return 签名字符串
     */
    private static String signature(PrintConfig config, String time) {
        String sign = DigestUtils.sha1Hex(config.getUser() + config.getKey() + time);
        return sign;
    }
}
