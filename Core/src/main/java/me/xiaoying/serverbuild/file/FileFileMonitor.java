package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.constant.ConfigConstant;
import me.xiaoying.serverbuild.constant.FileMonitorConstant;

import java.util.ArrayList;
import java.util.Arrays;

public class FileFileMonitor extends File {
    public FileFileMonitor() {
        super("FileMonitor.yml");
    }

    @Override
    public void onLoad() {
        FileMonitorConstant.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileMonitorConstant.SETTING_PREFIX = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");
        FileMonitorConstant.SETTING_DATEFORMAT = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigConstant.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");

        FileMonitorConstant.FILE_MONITOR_EVENT = new ArrayList<>(Arrays.asList(this.getConfiguration().getString("FileMonitor").split("\n")));

        FileMonitorConstant.MESSAGE_RELOAD = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigConstant.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        FileMonitorConstant.MESSAGE_MISSING_PERMISSION = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigConstant.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
        FileMonitorConstant.MESSAGE_HELP = ConfigConstant.OVERALL_SITUATION_ENABLE && ConfigConstant.OVERALL_SITUATION_MESSAGE_ENABLE ? ConfigConstant.OVERALL_SITUATION_MESSAGE_HELP : this.getConfiguration().getString("Message.Help");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}