package com.le.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.util.Map;

/**
 * @author 严秋旺
 * @since 2019-04-11 16:03
 **/
public class XmlUtils {

    private static XmlMapper xmlMapper = new XmlMapper();

    public static <T> T toObject(String xml, Class<T> valueType) {
        T result = null;

        try {
            result = xmlMapper.readValue(xml, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String xml) {
        Map<String, Object> result = null;

        try {
            result = xmlMapper.readValue(xml, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static String toXml(Object object) {
        try {
            String xml = xmlMapper.writeValueAsString(object);
            return xml;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
