package me.xiaoying.serverbuild.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Util String
 */
public class StringUtil {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String INTEGER_CHARS = "123456789";
    private static final Random RANDOM = new Random();

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String generateRandomString(int length) {
        return RANDOM.ints(length, 0, CHARACTERS.length())
                .mapToObj(i -> String.valueOf(CHARACTERS.charAt(i)))
                .collect(Collectors.joining());
    }

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String generateRandomIntString(int length) {
        return RANDOM.ints(length, 0, INTEGER_CHARS.length())
                .mapToObj(i -> String.valueOf(INTEGER_CHARS.charAt(i)))
                .collect(Collectors.joining());
    }


    /**
     * 是否为空
     * null或者空字符串都会判定为true
     *
     * @param str 字符串
     * @return 逻辑值
     */
    public static boolean isEmpty(String str) {
        return  str == null || str.length() == 0;
    }

    /**
     * 字符串通配符 ? *<br>
     *
     * @param s1 规则
     * @param s2 匹配数据
     * @param c1 位置1
     * @param c2 位置2
     * @return Boolean
     */
    public static boolean match(String s1, String s2, int c1, int c2) {
        if (c1 == s1.length() && c2 == s2.length()) {
            return true;
        }
        if (c1 == s1.length() || c2 == s2.length()) {
            return false;
        }
        if (s1.charAt(c1) == '?') {
            return match(s1, s2, c1 + 1, c2 + 1);
        } else if (s1.charAt(c1) == '*') {
            return match(s1, s2, c1 + 1, c2) || match(s1, s2, c1 + 1, c2 + 1) || match(s1, s2, c1, c2 + 1);
        } else if (s1.charAt(c1) == s2.charAt(c2)) {
            return match(s1, s2, c1 + 1, c2 + 1);
        } else {
            return false;
        }
    }

    /**
     * 移除前面的空格
     *
     * @param original 源字符串
     * @return 移除后的字符串
     */
    public static String removeStartingSpace(String original) {
        while (original.startsWith(" "))
            original = original.substring(1);

        return original;
    }

    /**
     * 移除所有空格
     *
     * @param original 源字符串
     * @return String
     */
    public static String removeAllSpace(String original) {
        return original.replace(" ", "");
    }

    /**
     * 将数组转为字符串
     *
     * @param args 数组
     * @param interval 间隔符号
     * @return 字符串
     */
    public static String listToString(String[] args, String interval) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i == args.length - 1) {
                stringBuilder.append(args[i]);
                break;
            }
            stringBuilder.append(args[i]).append(interval);
        }
        return stringBuilder.toString();
    }

    /**
     * 将数组转为字符串
     *
     * @param args 数组
     * @param interval 间隔符号
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 字符串
     */
    public static String listToString(String[] args, String interval, String prefix, String suffix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i == args.length - 1) {
                stringBuilder.append(prefix).append(args[i]).append(suffix);
                break;
            }
            stringBuilder.append(prefix).append(args[i]).append(suffix).append(interval);
        }
        return stringBuilder.toString();
    }

    /**
     * 移除前面的所有内容
     *
     * @param original 源字符串
     * @return 移除后的字符串
     */
    public static String removeStartingAllSpace(String original) {
        String[] str = original.split("");
        StringBuilder stringBuilder = new StringBuilder();
        boolean isStartingSpace = true;
        for (String s : str) {
            if (isStartingSpace && !s.equalsIgnoreCase(" "))
                isStartingSpace = false;
            if (isStartingSpace && s.equalsIgnoreCase(" "))
                continue;
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取开头的空格长度
     *
     * @param str 源字符串
     * @return 数值
     */
    public static int getStartingSpaceSize(String str) {
        int size = 0;
        String[] strs = str.split("");
        for (String s : strs) {
            if (s.equalsIgnoreCase(" ")) size++;
            else return size;
        }
        return 0;
    }

    /**
     * 移除关键字后的内容
     *
     * @param str 字符串
     * @param key 关键字
     * @return 内容
     */
    public static String removeBehindStr(String str, String key) {
        String[] strs = str.split("");
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder strBuilder = new StringBuilder();
        String[] keys = key.split("");
        int keyLenght = keys.length;
        // 当字符串中不包含关键字则直接返回原内容
        if (!str.contains(key))
            return str;

        boolean isStart = false;
        for (String s : strs) {
            if (key.startsWith(s) && !isStart) {
                isStart = true;
            }
            if (!isStart) {
                stringBuilder.append(s);
            } else {
                strBuilder.append(s);
            }
            if (strBuilder.length() == keyLenght && strBuilder.toString().equalsIgnoreCase(key)) {
                break;
            } else if (strBuilder.length() == keyLenght && !strBuilder.toString().equalsIgnoreCase(key)) {
                stringBuilder.append(strBuilder);
                strBuilder.delete(0, strBuilder.length());
                isStart = false;
            }
        }

        return stringBuilder.toString();
    }

    /**
     * 获取字符串的某个字符个数
     *
     * @param str     源内容
     * @param key     计数字符
     * @param capital 是否区分大小写
     * @return 个数
     */
    public static int getKeySize(String str, String key, boolean capital) {
        int size = 0;
        String[] strs = str.split("");
        for (String string : strs) {
            if (capital) {
                if (string.equals(key)) size++;
            } else {
                if (string.equalsIgnoreCase(key)) size++;
            }
        }
        return size;
    }

    /**
     * 移除字符串的某一位
     *
     * @param str 源内容
     * @return 内容
     */
    public static String removeSomeString(String str, int index) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] strs = str.split("");

        for (int i = 0; i < strs.length; i++) {
            if (i == index) {
                continue;
            }
            stringBuilder.append(strs[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * 大小写转换
     *
     * @param str       源字符串
     * @param index     字符串位数
     * @param uppercase 设置大小写
     * @return 转换后字符串
     */
    public static String caseString(String str, int index, boolean uppercase) {
        String[] strings = str.split("");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (i == index) {
                if (uppercase) {
                    strings[i] = strings[i].toUpperCase();
                } else {
                    strings[i] = strings[i].toLowerCase();
                }
            }
            stringBuilder.append(strings[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取前后缀中间的值
     *
     * @param string 源字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 所有匹配的值
     */
    public static List<String> rexStr(String string, String prefix, String suffix) {
        List<String> list = new ArrayList<>();

        boolean isPrefix = false;
        boolean isSuffix = false;
        int rexSize = 0;

        String[] strings = string.split("");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strings) {
            if (s.equalsIgnoreCase(prefix.split("")[rexSize]) && !isPrefix) {
                stringBuilder.append(s);
                rexSize++;

                try {
                    String s1 = prefix.split("")[rexSize];
                } catch (Exception e) {
                    isPrefix = true;
                    rexSize = 0;
                }
                continue;
            }

            if (isPrefix)
                stringBuilder.append(s);

            try {
                if (s.equalsIgnoreCase(suffix.split("")[rexSize]) && isPrefix) {
                    rexSize++;

                    try {
                        String s1 = suffix.split("")[rexSize];
                    } catch (Exception e) {
                        isSuffix = true;
                        rexSize = 0;
                    }
                }
            } catch (Exception e)  {
                // todo
            }

            if (isPrefix && isSuffix) {
                list.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
                isSuffix = false;
                isPrefix = false;
            }
        }
        return list;
    }

    public static String argsToString(String[] args) {
        // 拼接字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i]);

            if (i == args.length - 1)
                break;

            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}