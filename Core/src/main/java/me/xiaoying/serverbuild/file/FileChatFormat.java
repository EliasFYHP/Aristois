package me.xiaoying.serverbuild.file;

import java.util.List;

/**
 * File ChatFormat.yml
 */
public class FileChatFormat extends File {
    public static String TABLE_MUTE = "cf_mute";

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
            MESSAGE_NOT_FOUND_PLAYER,
            MESSAGE_HELP;

    public FileChatFormat() {
        super("ChatFormat.yml");
    }

    @Override
    public void onLoad() {
        FileChatFormat.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileChatFormat.SETTING_DATEFORMAT = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");
        FileChatFormat.SETTING_PREFIX = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");

        FileChatFormat.MUTE_DEFAULT_TIME = this.getConfiguration().getString("Mute.DefaultTime");
        FileChatFormat.MUTE_MESSAGE = this.getConfiguration().getString("Mute.Message");

        FileChatFormat.CHARACTER_LIMIT_ENABLE = this.getConfiguration().getBoolean("CharacterLimit.Enable");
        FileChatFormat.CHARACTER_LIMIT_LIMIT = this.getConfiguration().getInt("CharacterLimit.Limit");
        FileChatFormat.CHARACTER_LIMIT_MESSAGE = this.getConfiguration().getString("CharacterLimit.Message");

        FileChatFormat.CALL_ENABLE = this.getConfiguration().getBoolean("Call.Enable");
        FileChatFormat.CALL_KEY = this.getConfiguration().getString("Call.Key");
        FileChatFormat.CALL_SOUND = this.getConfiguration().getString("Call.Sound");

        FileChatFormat.BLACK_TERMS_ENABLE = this.getConfiguration().getBoolean("BlackTerms.Enable");
        FileChatFormat.BLACK_TERMS_FOR_EVERY_BODY = this.getConfiguration().getBoolean("BlackTerms.ForEveryBody");
        FileChatFormat.BLACK_TERMS_CANCEL = this.getConfiguration().getBoolean("BlackTerms.Cancel");
        FileChatFormat.BLACK_TERMS_SCRIPT = this.getConfiguration().getString("BlackTerms.Script");
        FileChatFormat.BLACK_TERMS_MESSAGE = this.getConfiguration().getString("BlackTerms.Message");
        FileChatFormat.BLACK_TERMS_TERMS = this.getConfiguration().getStringList("BlackTerms.Terms");

        FileChatFormat.MESSAGE_RELOAD = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        FileChatFormat.MESSAGE_MISSING_PERMISSION = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
        FileChatFormat.MESSAGE_HELP = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_HELP : this.getConfiguration().getString("Message.Help");
        FileChatFormat.MESSAGE_MUTE_WRONG = this.getConfiguration().getString("Message.MuteWrong");
        FileChatFormat.MESSAGE_MUTE_SUCCESS = this.getConfiguration().getString("Message.MuteSuccess");
        FileChatFormat.MESSAGE_NOT_FOUND_PLAYER = this.getConfiguration().getString("Message.NotFoundPlayer");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}