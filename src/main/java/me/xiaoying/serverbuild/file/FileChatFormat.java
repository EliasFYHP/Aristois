package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.constant.ChatFormatConstant;
import me.xiaoying.serverbuild.constant.ConfigConstant;

/**
 * File ChatFormat.yml
 */
public class FileChatFormat extends File {
    public FileChatFormat() {
        super("ChatFormat.yml");
    }

    @Override
    public void onLoad() {
        ChatFormatConstant.ENABLE = this.getConfiguration().getBoolean("Enable");

        ChatFormatConstant.SETTING_DATEFORMAT = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigConstant.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");
        ChatFormatConstant.SETTING_PREFIX = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");

        ChatFormatConstant.MUTE_DEFAULT_TIME = this.getConfiguration().getString("Mute.DefaultTime");
        ChatFormatConstant.MUTE_MESSAGE = this.getConfiguration().getString("Mute.Message");

        ChatFormatConstant.CHARACTER_LIMIT_ENABLE = this.getConfiguration().getBoolean("CharacterLimit.Enable");
        ChatFormatConstant.CHARACTER_LIMIT_LIMIT = this.getConfiguration().getInt("CharacterLimit.Limit");
        ChatFormatConstant.CHARACTER_LIMIT_MESSAGE = this.getConfiguration().getString("CharacterLimit.Message");

        ChatFormatConstant.CALL_ENABLE = this.getConfiguration().getBoolean("Call.Enable");
        ChatFormatConstant.CALL_KEY = this.getConfiguration().getString("Call.Key");
        ChatFormatConstant.CALL_SOUND = this.getConfiguration().getString("Call.Sound");

        ChatFormatConstant.BLACK_TERMS_ENABLE = this.getConfiguration().getBoolean("BlackTerms.Enable");
        ChatFormatConstant.BLACK_TERMS_FOR_EVERY_BODY = this.getConfiguration().getBoolean("BlackTerms.ForEveryBody");
        ChatFormatConstant.BLACK_TERMS_CANCEL = this.getConfiguration().getBoolean("BlackTerms.Cancel");
        ChatFormatConstant.BLACK_TERMS_SCRIPT = this.getConfiguration().getString("BlackTerms.Script");
        ChatFormatConstant.BLACK_TERMS_MESSAGE = this.getConfiguration().getString("BlackTerms.Message");
        ChatFormatConstant.BLACK_TERMS_TERMS = this.getConfiguration().getStringList("BlackTerms.Terms");

        ChatFormatConstant.MESSAGE_RELOAD = this.getConfiguration().getString("Message.Reload");
        ChatFormatConstant.MESSAGE_MISSING_PERMISSION = this.getConfiguration().getString("Message.MissingPermission");
        ChatFormatConstant.MESSAGE_MUTE_WRONG = this.getConfiguration().getString("Message.MuteWrong");
        ChatFormatConstant.MESSAGE_MUTE_SUCCESS = this.getConfiguration().getString("Message.MuteSuccess");
        ChatFormatConstant.MESSAGE_NOT_FOUND_PLAYER = this.getConfiguration().getString("Message.NotFoundPlayer");
        ChatFormatConstant.MESSAGE_HELP = this.getConfiguration().getString("Message.Help");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}