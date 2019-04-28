package com.le.web.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取表单分页对象
     *
     * @param <T>
     * @return
     */
    public static <T> Page<T> getPage() {
        HttpServletRequest request = getHttpServletRequest();
        long current = 1, size = 10;
        String paramCurrent = request.getParameter("pageNumber");
        String paramSize = request.getParameter("pageSize");

        if (StringUtils.isNotEmpty(paramCurrent)) {
            try {
                current = Long.parseLong(paramCurrent);
            } catch (Exception e) {
                //ignore
            }
        }

        if (StringUtils.isNotEmpty(paramSize)) {
            try {
                size = Long.parseLong(paramSize);
            } catch (Exception e) {
                //ignore
            }
        }

        return new Page<>(current, size);
    }

    /**
     * 获取请求url，包括search部分
     *
     * @return 返回完整请求地址
     */
    public static String getRequestUrl() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String url = request.getRequestURL().toString();
        String query = request.getQueryString();

        if (StringUtils.isNotEmpty(query)) {
            url += "?" + query;
        }

        return url;
    }
}
