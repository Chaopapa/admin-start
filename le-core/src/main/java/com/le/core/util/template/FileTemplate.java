package com.le.core.util.template;

import com.le.core.util.Constant;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import no.api.freemarker.java8.Java8ObjectWrapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

/**
 * @author 严秋旺
 * @since 2019-04-23 14:41
 **/
@Slf4j
class FileTemplate {
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    private Configuration configuration;
    ;

    private FileTemplate(String encoding) throws IOException {
        configuration = new Configuration(Configuration.getVersion());
        loadProperties();
        configuration.setDefaultEncoding(encoding);
        File dir = new File(Constant.CLASS_DIR + "/tpl/");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(dir);
        configuration.setTemplateLoader(fileTemplateLoader);
        configuration.setObjectWrapper(new Java8ObjectWrapper(configuration.getVersion()));
    }

    static FileTemplate instance(String encoding) throws IOException {
        return new FileTemplate(encoding);
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

    public void make(String tpl, Map<String, Object> dataModel, Writer out) throws TemplateException, IOException {
        Template template = configuration.getTemplate(tpl);
        template.process(dataModel, out);
        out.flush();
    }
}
