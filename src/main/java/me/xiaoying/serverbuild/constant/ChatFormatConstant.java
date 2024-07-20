package me.xiaoying.serverbuild.constant;

import java.util.List;

/**
 * Constant ChatFormat
 */
public class ChatFormatConstant {
    public static boolean ENABLE;

    public static String SETTING_DATEFORMAT, SETTING_PREFIX;

    public static String MUTE_DEFAULT_TIME, MUTE_MESSAGE;

    public static boolean CHARACTER_LIMIT_ENABLE;
    public static int CHARACTER_LIMIT_LIMIT;
    public static String CHARACTER_LIMIT_MESSAGE;

    public static boolean CALL_ENABLE;
    public static String CALL_KEY, CALL_SOUND;

    public static boolean BLACK_TERMS_ENABLE,
            BLACK_TERMS_FOR_EVERY_BODY,
            BLACK_TERMS_CANCEL;
    public static String BLACK_TERMS_SCRIPT,
            BLACK_TERMS_MESSAGE;
    public static List<String> BLACK_TERMS_TERMS;

    public static String MESSAGE_RELOAD,
            MESSAGE_MISSING_PERMISSION,
            MESSAGE_MUTE_WRONG,
            MESSAGE_MUTE_SUCCESS,
            MESSAGE_NOT_FOUND_PLAYER;
    public static String MESSAGE_HELP;
}