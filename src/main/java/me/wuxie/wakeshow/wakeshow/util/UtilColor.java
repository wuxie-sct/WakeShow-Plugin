package me.wuxie.wakeshow.wakeshow.util;

import com.sun.istack.internal.Nullable;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class UtilColor {
    private static final Pattern a = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    public static boolean b(@Nullable String var0) {
        return StringUtils.isEmpty(var0);
    }
}