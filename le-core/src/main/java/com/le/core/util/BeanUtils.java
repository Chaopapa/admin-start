package com.le.core.util;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 严秋旺
 * @since 2019-04-13 20:29
 **/
public class BeanUtils {

    /**
     * map集合转换为实体
     *
     * @param map  数据集
     * @param bean 实体类型
     * @return
     */
    public static <T> void mapToBean(Map<String, ? extends Object> map, T bean) {
        try {
            org.apache.commons.beanutils.BeanUtils.populate(bean, map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (!Modifier.isFinal(field.getModifiers())) {
                try {
                    String name = field.getName();
                    Object object = PropertyUtils.getProperty(bean, name);

                    if (object != null)
                        map.put(name, object);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return map;
    }

}
