package com.le.component.print;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * @author 严秋旺
 * @since 2019-04-23 13:58
 **/
public interface IPrintComponent {

    /**
     * 打印内容
     *
     * @param model 打印时的数据模板，配置模板设置
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    boolean print(Map<String, Object> model) throws IOException, TemplateException;

    /**
     * 打印内容
     *
     * @param model 打印时的数据模板，配置模板设置
     * @param times 打印数量
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    boolean print(Map<String, Object> model, int times) throws IOException, TemplateException;

    /**
     * 清空待打印队列
     *
     * @return
     */
    boolean cancel() throws IOException;
}
