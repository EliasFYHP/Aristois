package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.constant.ConfigConstant;

/**
 * File Config.yml
 */
public class FileConfig extends File {
    public FileConfig() {
        super("Config.yml");
    }

    @Override
    public void onLoad() {
        ConfigConstant.SETTING_BSTATS = this.getConfiguration().getBoolean("Setting.Bstats");
        ConfigConstant.SETTING_DATA_TYPE = this.getConfiguration().getString("Setting.Data.Type");
        ConfigConstant.SETTING_DATA_SQLITE_DATAPATH = this.getConfiguration().getString("Setting.Data.SQLite.DataPath");
        ConfigConstant.SETTING_DATA_MYSQL_HOST = this.getConfiguration().getString("Setting.Data.Mysql.Host");
        ConfigConstant.SETTING_DATA_MYSQL_PORT = this.getConfiguration().getInt("Setting.Data.Mysql.Port");
        ConfigConstant.SETTING_DATA_MYSQL_DATABASE = this.getConfiguration().getString("Setting.Data.Mysql.Database");
        ConfigConstant.SETTING_DATA_MYSQL_USERNAME = this.getConfiguration().getString("Setting.Data.Mysql.Username");
        ConfigConstant.SETTING_DATA_MYSQL_PASSWORD = this.getConfiguration().getString("Setting.Data.Mysql.Password");

        ConfigConstant.OVERALL_SITUATION_ENABLE = this.getConfiguration().getBoolean("OverallSituation.Enable");
        ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE = this.getConfiguration().getBoolean("OverallSituation.Variable.Enable");
        ConfigConstant.OVERALL_SITUATION_VARIABLE_DATAFORAMT = this.getConfiguration().getString("OverallSituation.Variable.DateFormat");
        ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX = this.getConfiguration().getString("OverallSituation.Variable.Prefix");
        ConfigConstant.OVERALL_SITUATION_MESSAGE_ENABLE = this.getConfiguration().getBoolean("OverallSituation.Message.Enable");
        ConfigConstant.OVERALL_SITUATION_MESSAGE_RELOAD = this.getConfiguration().getString("OverallSituation.Message.Reload");
        ConfigConstant.OVERALL_SITUATION_MESSAGE_COMPLETE = this.getConfiguration().getString("OverallSituation.Message.Complete");
        ConfigConstant.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION = this.getConfiguration().getString("OverallSituation.Message.MissingPermission");
        ConfigConstant.OVERALL_SITUATION_MESSAGE_HELP = this.getConfiguration().getString("OverallSituation.Message.Help");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}