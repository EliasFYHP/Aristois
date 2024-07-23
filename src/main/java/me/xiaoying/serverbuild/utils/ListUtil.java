package me.xiaoying.serverbuild.utils;

import java.util.List;

/**
 * 工具类 List
 */
public class ListUtil {
    /**
     * String contains
     *
     * @param list 列表
     * @param value contains 值
     * @param matchCase 是否区分大小写
     * @return 逻辑值
     */
    public static boolean contains(List<String> list, String value, boolean matchCase) {
        for (String s : list) {
            if (!matchCase) {
                if (!s.equalsIgnoreCase(value))
                    continue;

                return true;
            }

            if (!s.equals(value))
                continue;

            return true;
        }
        return false;
    }

    /**
     * 获取列表最小值
     *
     * @param list 列表
     * @return 最小值
     */
    public static int getListMinNumber(List<Integer> list) {
        int num = list.get(0);
        for (int i : list) {
            if (i >= num) continue;
            num = i;
        }
        return num;
    }

    /**
     * 获取列表最大值
     *
     * @param list 列表
     * @return 最大值
     */
    public static int getListMaxNumber(List<Integer> list) {
        int num = list.get(0);
        for (int i : list) {
            if (i <= num) continue;
            num = i;
        }
        return num;
    }
}