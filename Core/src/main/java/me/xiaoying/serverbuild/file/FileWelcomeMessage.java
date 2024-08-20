package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.constant.ConfigConstant;
import me.xiaoying.serverbuild.constant.WelcomeMessageConstant;

public class FileWelcomeMessage extends File {
    public FileWelcomeMessage() {
        super("WelcomeMessage.yml");
    }

    @Override
    public void onLoad() {
        WelcomeMessageConstant.ENABLE = this.getConfiguration().getBoolean("Enable");

        WelcomeMessageConstant.SETTING_DATEFORMAT = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigConstant.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");
        WelcomeMessageConstant.SETTING_PREFIX = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");

        WelcomeMessageConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigConstant.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        WelcomeMessageConstant.MESSAGE_MISSING_PERMISSION = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigConstant.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
        WelcomeMessageConstant.MESSAGE_HELP = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigConstant.OVERALL_SITUATION_MESSAGE_HELP : this.getConfiguration().getString("Message.Help");

    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}