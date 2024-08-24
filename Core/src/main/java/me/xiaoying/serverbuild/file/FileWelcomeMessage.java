package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.constant.WelcomeMessageConstant;

public class FileWelcomeMessage extends File {
    public FileWelcomeMessage() {
        super("WelcomeMessage.yml");
    }

    @Override
    public void onLoad() {
        WelcomeMessageConstant.ENABLE = this.getConfiguration().getBoolean("Enable");

        WelcomeMessageConstant.SETTING_DATEFORMAT = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");
        WelcomeMessageConstant.SETTING_PREFIX = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");

        WelcomeMessageConstant.MESSAGE_RELOAD = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        WelcomeMessageConstant.MESSAGE_MISSING_PERMISSION = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
        WelcomeMessageConstant.MESSAGE_HELP = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_HELP : this.getConfiguration().getString("Message.Help");

    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}