package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.constant.ResolveLagConstant;

/**
 * File ResolveLag.yml
 */
public class FileResolveLag extends File {
    public FileResolveLag() {
        super("ResolveLag.yml");
    }

    @Override
    public void onLoad() {
        ResolveLagConstant.ENABLE = this.getConfiguration().getBoolean("Enable");

        ResolveLagConstant.SETTING_DATEFORMAT = this.getConfiguration().getString("Setting.DateFormat");
        ResolveLagConstant.SETTING_PREFIX = this.getConfiguration().getString("Setting.Prefix");

        ResolveLagConstant.RESOLVE_LAG_SECOND_TIME = this.getConfiguration().getInt("ResolveLag.SecondTIme");

        ResolveLagConstant.RESOLVE_LAG_ENTITY_PET = this.getConfiguration().getBoolean("ResolveLag.Entity.Pet");
        ResolveLagConstant.RESOLVE_LAG_ENTITY_NAMED = this.getConfiguration().getBoolean("ResolveLag.Entity.Named");

        ResolveLagConstant.RESOLVE_LAG_ENTITY_SPECIAL_POSE = this.getConfiguration().getBoolean("ResolveLag.Entity.SpecialPose");
        ResolveLagConstant.RESOLVE_LAG_ENTITY_TOTAL_ENABLE = this.getConfiguration().getBoolean("ResolveLag.Entity.Total.Enable");

        ResolveLagConstant.RESOLVE_LAG_ENTITY_TOTAL_LIMIT = this.getConfiguration().getInt("ResolveLag.Entity.Total.Limit");
        ResolveLagConstant.RESOLVE_LAG_ENTITY_SPECIAL_ITEM = this.getConfiguration().getStringList("ResolveLag.Entity.SpecialItem");
        ResolveLagConstant.RESOLVE_LAG_ENTITY_SPECIAL_ENTITY = this.getConfiguration().getStringList("ResolveLag.Entity.SpecialEntity");

        ResolveLagConstant.RESOLVE_LAG_CHUNK_INTERVAL = this.getConfiguration().getInt("ResolveLag.Chunk.Interval");
        ResolveLagConstant.RESOLVE_LAG_CHUNK_TOTAL_ENABLE = this.getConfiguration().getBoolean("ResolveLag.Chunk.Total.Limit");
        ResolveLagConstant.RESOLVE_LAG_CHUNK_TOTAL_LIMIT = this.getConfiguration().getInt("ResolveLag.Chunk.Total.Limit");

        ResolveLagConstant.RESOLVE_LAG_WHITE_WORLD = this.getConfiguration().getStringList("ResolveLag.WhiteWorld");
        ResolveLagConstant.RESOLVE_LAG_COUNT_TIME = this.getConfiguration().getIntegerList("ResolveLag.CountTime");

        ResolveLagConstant.CLEAR_MESSAGE_COUNT_DOWN = this.getConfiguration().getString("ClearMessage.CountDown");

        ResolveLagConstant.MESSAGE_RELOAD = this.getConfiguration().getString("Message.Reload");
        ResolveLagConstant.MESSAGE_MISSING_PERMISSION = this.getConfiguration().getString("Message.MissingPermission");
        ResolveLagConstant.MESSAGE_HELP = this.getConfiguration().getString("Message.Help");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}