package com.le.core.util.template;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import no.api.freemarker.java8.Java8ObjectWrapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * @author 严秋旺
 * @since 2019-04-23 14:43
 **/
@Slf4j
class StringTemplate {
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    private Configuration configuration;
    //	private final Map<String, String> templates = new HashMap<>();
    private StringTemplateLoader stringLoader = new StringTemplateLoader();

    private StringTemplate(String encoding) {
        configuration = new Configuration(Configuration.getVersion());
        loadProperties();
        configuration.setDefaultEncoding(encoding);
        configuration.setTemplateLoader(stringLoader);
        configuration.setObjectWrapper(new Java8ObjectWrapper(configuration.getVersion()));
    }

    static StringTemplate instance(String encoding) throws IOException {
        return new StringTemplate(encoding);
    }

    /**
     * 加载并设置freemarker.properties
     */
    private void loadProperties() {
        Properties p = new Properties();
        Resource resource = resourceResolver.getResource("classpath:/freemarker.properties");

        try (InputStream inputStream = resource.getInputStream();) {
            p.load(inputStream);
            configuration.setSettings(p);
        } catch (Exception e) {
            log.error("配置文件读取异常，" + e.getMessage(), e);
        }
    }

//	/**
//	 * 获取字符串模板key
//	 *
//	 * @param template
//	 *            模板字符串
//	 * @throws IOException
//	 */
//	private String loadStringTemplate(String tpl) throws ParseException, IOException {
//		String key = templates.get(tpl);
//
//		if (key == null) {
//			key = UUID.randomUUID().toString();
//			templates.put(tpl, key);
//			stringLoader.putTemplate(key, tpl);
//		}
//
//		return key;
//	}

    public void make(String tpl, Map<String, Object> dataModel, Writer out) throws TemplateException, IOException {
//		String key = loadStringTemplate(tpl);
        synchronized (configuration) {//执行时可能从取上次执行过的缓存的模板
            String key = "tpl";
            stringLoader.putTemplate(key, tpl);
            Template template = configuration.getTemplate(key);
            configuration.removeTemplateFromCache(key);
            template.process(dataModel, out);
            out.flush();
        }
    }
}