package me.xiaoying.serverbuild.constant;

import java.util.List;

/**
 * Constant of file for ResolveLag
 */
public class ResolveLagConstant {
    public static boolean ENABLE;

    public static String SETTING_DATEFORMAT, SETTING_PREFIX;

    public static int RESOLVE_LAG_SECOND_TIME;
    public static boolean RESOLVE_LAG_ENTITY_PET,
            RESOLVE_LAG_ENTITY_NAMED,
            RESOLVE_LAG_ENTITY_SPECIAL_POSE,
            RESOLVE_LAG_ENTITY_TOTAL_ENABLE;

    public static int RESOLVE_LAG_ENTITY_TOTAL_LIMIT;
    public static List<String> RESOLVE_LAG_ENTITY_SPECIAL_ITEM,
            RESOLVE_LAG_ENTITY_SPECIAL_ENTITY;

    public static int RESOLVE_LAG_CHUNK_INTERVAL,
            RESOLVE_LAG_CHUNK_TOTAL_LIMIT;
    public static boolean RESOLVE_LAG_CHUNK_TOTAL_ENABLE;

    public static List<String> RESOLVE_LAG_WHITE_WORLD;
    public static List<Integer> RESOLVE_LAG_COUNT_TIME;

    public static String CLEAR_MESSAGE_COUNT_DOWN;

    public static String MESSAGE_RELOAD,
            MESSAGE_MISSING_PERMISSION,
            MESSAGE_UNKNOWN_WORLD,
            MESSAGE_WORLD_STATE,
            MESSAGE_HELP;
}