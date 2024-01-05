package com.learn.platform.entity.util;

import com.alibaba.cloud.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * @ClassName XssCleanRuleUtils
 * @Description TODO
 * @Author xue
 * @Date 2024/1/5 14:45
 */
public class XssCleanRuleUtils {
    private XssCleanRuleUtils() {

    }

    private static StringJoiner joiner = new StringJoiner("|");
    private static final String XSS_REPLACEMENT = "***XSS***";
    private static Pattern xssPattern;
    private static final String[] xssScriptRegArr = {
            "<script>(.*?)</script>",
            "src[\r\n]*=[\r\n]*\\'(.*?)\\'",
            "</script>",
            "<script(.*?)>",
            "eval\\((.*?)\\)",
            "expression\\((.*?)\\)",
            "javascript:",
            "vbscript:",
            "onload(.*?)=",
            "<(.*?)>"
    };

    public static final List<String> XSS_REPLACEMENT_LIST = Collections.singletonList(XSS_REPLACEMENT);

    static {
        for (String reg : xssScriptRegArr) {
            joiner.add(reg);
        }

        xssPattern = Pattern.compile(joiner.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    }

    /**
     * isFind
     * @param value
     * @return
     */
    public static boolean isFind(String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }

        return xssPattern.matcher(value).find();
    }

    /**
     * xssClean
     * @param value
     * @return
     */
    public static String xssClean(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }

        return xssPattern.matcher(value).replaceAll(XSS_REPLACEMENT);
    }

}
