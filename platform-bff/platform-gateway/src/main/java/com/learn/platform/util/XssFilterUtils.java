package com.learn.platform.util;

import cn.hutool.core.map.MapUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.learn.platform.entity.util.XssCleanRuleUtils;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.learn.platform.entity.util.XssCleanRuleUtils.XSS_REPLACEMENT_LIST;

/**
 * @ClassName XssFilterUtils
 * @Description TODO
 * @Author xue
 * @Date 2024/1/5 14:44
 */
public class XssFilterUtils {
    /**
     * 临时特殊处理（后续如果明确出忽略规则考虑将规则进行配置，先hardcode）
     */
    private static final String[] XSS_FILTER_IGNORE_CONTENT_TYPES = {"application/vnd.ms-excel", "multipart/form-data"};

    private XssFilterUtils() {

    }

    /**
     * isIgnore
     *
     * @param httpHeaders
     * @return
     */
    public static boolean isIgnore(HttpHeaders httpHeaders) {
        if (MapUtil.isEmpty(httpHeaders)) {
            return false;
        }

        List<String> contentTypeValues = httpHeaders.get(HttpHeaders.CONTENT_TYPE);
        if (CollectionUtils.isEmpty(contentTypeValues)) {
            return false;
        }

        for (String contentTypeValue : contentTypeValues) {
            for (String ignoredContentTypeValue : XSS_FILTER_IGNORE_CONTENT_TYPES) {
                if (contentTypeValue.toLowerCase().indexOf(ignoredContentTypeValue) >= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * uriXssClean
     *
     * @param uri
     * @return
     */
    public static URI uriXssClean(URI uri) {
        if (uri == null) {
            return null;
        }

        URI result = uri;
        String rawQuery = getUrlDecoderValue(uri.getRawQuery());
        if (!XssCleanRuleUtils.isFind(rawQuery)) {
            return uri;
        }

        // Query XSS清理
        Map<String, String> queryMap = getQueryMap(uri);
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : queryMap.entrySet()) {
            String value = URLEncoder.encode(XssCleanRuleUtils.xssClean(entry.getValue()), StandardCharsets.UTF_8);
            sb.append(entry.getKey() + "=" + value);
            if ((i + 1) != queryMap.size()) {
                sb.append("&");
            }

            i++;
        }

        return UriComponentsBuilder.fromUri(result)
                .replaceQuery(sb.toString())
                .build(true)
                .toUri();
    }

    /**
     * headerXssClean
     *
     * @param httpHeaders
     * @return
     */
    public static HttpHeaders headerXssClean(HttpHeaders httpHeaders) {
        HttpHeaders newHeaders = new HttpHeaders();
        if (MapUtil.isEmpty(httpHeaders)) {
            return newHeaders;
        }

        newHeaders.putAll(httpHeaders);
        List<String> removedHeaderKeys = new ArrayList<>();
        httpHeaders.forEach((key, values) -> {
            for (String value : values) {
                if (XssCleanRuleUtils.isFind(getUrlDecoderValue(value))) {
                    removedHeaderKeys.add(key);
                    break;
                }
            }
        });

        for (String key : removedHeaderKeys) {
            newHeaders.remove(key);
            newHeaders.put(key, XSS_REPLACEMENT_LIST);
        }

        return newHeaders;
    }

    /**
     * bodyXssClean
     *
     * @param headers
     * @param data
     * @return
     */
    public static byte[] bodyXssClean(HttpHeaders headers, byte[] data) {
        if (ArrayUtils.isEmpty(data)) {
            return new byte[0];
        }

        String body = new String(data, StandardCharsets.UTF_8);
        if (isFormUrlEncodedContentType(headers)) {
            Map<String, String> rawFormDataMap = getFormDataMap(body);
            Map<String, String> newFormDataMap = new LinkedHashMap<>(rawFormDataMap.size());
            for (Map.Entry<String, String> entry : rawFormDataMap.entrySet()) {
                newFormDataMap.put(entry.getKey(), XssCleanRuleUtils.xssClean(entry.getValue()));
            }

            return getFormDataString(newFormDataMap).getBytes(StandardCharsets.UTF_8);
        }

        return XssCleanRuleUtils.xssClean(body).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * resetContentLength
     *
     * @param headers
     * @param length
     */
    public static void resetContentLength(HttpHeaders headers, int length) {
        if (!MapUtil.isEmpty(headers) && headers.containsKey(HttpHeaders.CONTENT_LENGTH)) {
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            headers.setContentLength(length);
        }
    }


    /**
     * 兼容进行URL编码和没有编码的情况（先尝试URL解码，如果失败返回原始值）
     *
     * @param value
     * @return
     */
    private static String getUrlDecoderValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            return value;
        }
    }

    /**
     * getQueryMap
     *
     * @param uri
     * @return
     */
    private static Map<String, String> getQueryMap(@NonNull final URI uri) {
        String query = uri.getQuery();
        final Map<String, String> queryArgsMap = new HashMap<>(16);
        if (StringUtils.isEmpty(query)) {
            return queryArgsMap;
        }

        List<NameValuePair> params = URLEncodedUtils.parse(uri, Charsets.UTF_8);
        for (NameValuePair param : params) {
            queryArgsMap.putIfAbsent(param.getName(), param.getValue());
        }

        return queryArgsMap;
    }

    /**
     * isFormUrlEncodedContentType
     *
     * @param headers
     * @return
     */
    private static boolean isFormUrlEncodedContentType(HttpHeaders headers) {
        if (MapUtil.isEmpty(headers)) {
            return false;
        }

        List<String> contentTypeValues = headers.get(HttpHeaders.CONTENT_TYPE);
        if (CollectionUtils.isEmpty(contentTypeValues)) {
            return false;
        }

        for (String contentTypeValue : contentTypeValues) {
            if (contentTypeValue.toLowerCase().indexOf("application/x-www-form-urlencoded") >= 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * getFormDataMap
     *
     * @param formData
     * @return
     */
    private static Map<String, String> getFormDataMap(String formData) {
        Map<String, String> formDataMap = new LinkedHashMap<>(8);
        if (StringUtils.isEmpty(formData)) {
            return formDataMap;
        }

        String[] pairs = formData.split("\\&");
        for (int i = 0; i < pairs.length; i++) {
            String[] fields = pairs[i].split("=");
            if (fields.length >= 2) {
                String name = getUrlDecoderValue(fields[0]);
                String value = getUrlDecoderValue(fields[1]);
                formDataMap.put(name, value);
            }
        }

        return formDataMap;
    }

    /**
     * getFormDataString
     *
     * @param formDataMap
     * @return
     */
    private static String getFormDataString(Map<String, String> formDataMap) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : formDataMap.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }

        return result.toString();
    }
}
