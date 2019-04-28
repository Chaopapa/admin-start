package com.le.core.util.template;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author 严秋旺
 * @since 2019-04-23 14:42
 **/
public class TemplateUtil {

    private static final String ENCODING = "UTF-8";

    private static StringTemplate stringTemplate = null;
    private static FileTemplate fileTemplate = null;

    static {
        try {
            stringTemplate = StringTemplate.instance(ENCODING);
            fileTemplate = FileTemplate.instance(ENCODING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tpl
     * @param model
     * @return
     * @throws TemplateException 字符串+数据模型转字符串
     * @throws IOException
     * @throws
     */
    public static String stringTplValue(String tpl, Map<String, Object> model) throws IOException, TemplateException {
        try (StringWriter out = new StringWriter();) {
            stringTemplate.make(tpl, model, out);
            return out.toString();
        }
    }

    public static String fileTplValue(String path, Map<String, Object> model) throws TemplateException, IOException {
        try (StringWriter out = new StringWriter();) {
            fileTemplate.make(path, model, out);
            return out.toString();
        }
    }
}
