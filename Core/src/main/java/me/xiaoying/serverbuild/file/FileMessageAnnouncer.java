package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.constant.ConfigConstant;
import me.xiaoying.serverbuild.constant.MessageAnnouncerConstant;

/**
 * File MessageAnnouncer.yml
 */
public class FileMessageAnnouncer extends File {
    public FileMessageAnnouncer() {
        super("MessageAnnouncer.yml");
    }

    @Override
    public void onLoad() {
        MessageAnnouncerConstant.ENABLE = this.getConfiguration().getBoolean("Enable");

        MessageAnnouncerConstant.SETTING_PREFIX = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");
        MessageAnnouncerConstant.SETTING_DATEFORMAT = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigConstant.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");

        MessageAnnouncerConstant.MESSAGE_ANNOUNCER_DELAY = this.getConfiguration().getInt("MessageAnnouncer.Delay");

        MessageAnnouncerConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigConstant.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        MessageAnnouncerConstant.MESSAGE_MISSING_PERMISSION = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigConstant.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
        MessageAnnouncerConstant.MESSAGE_HELP = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigConstant.OVERALL_SITUATION_MESSAGE_HELP : this.getConfiguration().getString("Message.Help");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}