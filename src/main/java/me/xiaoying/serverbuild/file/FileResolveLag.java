package me.xiaoying.serverbuild.file;

import me.xiaoying.serverbuild.constant.ChatFormatConstant;
import me.xiaoying.serverbuild.constant.ConfigConstant;
import me.xiaoying.serverbuild.constant.ResolveLagConstant;

import java.util.ArrayList;
import java.util.List;

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

        ResolveLagConstant.SETTING_DATEFORMAT = ConfigConstant.OVERALL_SITUATION_ENABLE & ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigConstant.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");
        ResolveLagConstant.SETTING_PREFIX = ConfigConstant.OVERALL_SITUATION_ENABLE & ConfigConstant.OVERALL_SITUATION_VARIABLE_ENABLE ? ConfigConstant.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");

        ResolveLagConstant.RESOLVE_LAG_SECOND_TIME = this.getConfiguration().getInt("ResolveLag.SecondTime");

        ResolveLagConstant.RESOLVE_LAG_ENTITY_PET = this.getConfiguration().getBoolean("ResolveLag.Entity.Pet");
        ResolveLagConstant.RESOLVE_LAG_ENTITY_NAMED = this.getConfiguration().getBoolean("ResolveLag.Entity.Named");

        ResolveLagConstant.RESOLVE_LAG_ENTITY_SPECIAL_POSE = this.getConfiguration().getBoolean("ResolveLag.Entity.SpecialPose");
        ResolveLagConstant.RESOLVE_LAG_ENTITY_TOTAL_ENABLE = this.getConfiguration().getBoolean("ResolveLag.Entity.Total.Enable");

        ResolveLagConstant.RESOLVE_LAG_ENTITY_TOTAL_LIMIT = this.getConfiguration().getInt("ResolveLag.Entity.Total.Limit");
        List<String> itemList = new ArrayList<>();
        for (String s : this.getConfiguration().getStringList("ResolveLag.Entity.SpecialItem")) {
            if (!s.contains(":"))
                s = "minecraft:" + s;
            itemList.add(s);
        }
        List<String> entityList = new ArrayList<>();
        for (String s : this.getConfiguration().getStringList("ResolveLag.Entity.SpecialEntity")) {
            if (!s.contains(":"))
                s = "minecraft:" + s;
            itemList.add(s);
        }
        ResolveLagConstant.RESOLVE_LAG_ENTITY_SPECIAL_ITEM = itemList;
        ResolveLagConstant.RESOLVE_LAG_ENTITY_SPECIAL_ENTITY = entityList;

        ResolveLagConstant.RESOLVE_LAG_CHUNK_INTERVAL = this.getConfiguration().getInt("ResolveLag.Chunk.Interval");
        ResolveLagConstant.RESOLVE_LAG_CHUNK_TOTAL_ENABLE = this.getConfiguration().getBoolean("ResolveLag.Chunk.Total.Limit");
        ResolveLagConstant.RESOLVE_LAG_CHUNK_TOTAL_LIMIT = this.getConfiguration().getInt("ResolveLag.Chunk.Total.Limit");

        ResolveLagConstant.RESOLVE_LAG_WHITE_WORLD = this.getConfiguration().getStringList("ResolveLag.WhiteWorld");
        ResolveLagConstant.RESOLVE_LAG_COUNT_TIME = this.getConfiguration().getIntegerList("ResolveLag.CountTime");

        ResolveLagConstant.CLEAR_MESSAGE_COUNT_DOWN = this.getConfiguration().getString("ClearMessage.CountDown");

        ResolveLagConstant.MESSAGE_RELOAD = this.getConfiguration().getString("Message.Reload");
        ResolveLagConstant.MESSAGE_MISSING_PERMISSION = this.getConfiguration().getString("Message.MissingPermission");
        ResolveLagConstant.MESSAGE_UNKNOWN_WORLD = this.getConfiguration().getString("Message.UnknownWorld");
        ResolveLagConstant.MESSAGE_WORLD_STATE = this.getConfiguration().getString("Message.WorldState");
        ResolveLagConstant.MESSAGE_HELP = this.getConfiguration().getString("Message.Help");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}