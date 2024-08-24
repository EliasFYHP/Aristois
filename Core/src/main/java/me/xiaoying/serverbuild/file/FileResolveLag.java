package me.xiaoying.serverbuild.file;

import java.util.ArrayList;
import java.util.List;

/**
 * File ResolveLag.yml
 */
public class FileResolveLag extends File {
    public static boolean ENABLE;

    public static String SETTING_DATEFORMAT, SETTING_PREFIX;

    public static int RESOLVE_LAG_SECOND_TIME;
    public static boolean RESOLVE_LAG_ENTITY_PET,
            RESOLVE_LAG_ENTITY_NAMED,
            RESOLVE_LAG_ENTITY_SPECIAL_POSE,
            RESOLVE_LAG_ENTITY_TOTAL_ENABLE;

    public static int RESOLVE_LAG_ENTITY_TOTAL_LIMIT;
    public static List<String> RESOLVE_LAG_ENTITY_SPECIAL_ITEM,
            RESOLVE_LAG_ENTITY_SPECIAL_ENTITY;

    public static int RESOLVE_LAG_CHUNK_INTERVAL,
            RESOLVE_LAG_CHUNK_TOTAL_LIMIT;
    public static boolean RESOLVE_LAG_CHUNK_ENABLE,
            RESOLVE_LAG_CHUNK_TOTAL_ENABLE;

    public static List<String> RESOLVE_LAG_WHITE_WORLD;
    public static List<Integer> RESOLVE_LAG_COUNT_TIME;

    public static String CLEAR_MESSAGE_COUNT_DOWN;

    public static String MESSAGE_RELOAD,
            MESSAGE_MISSING_PERMISSION,
            MESSAGE_UNKNOWN_WORLD,
            MESSAGE_WORLD_STATE,
            MESSAGE_HELP;

    public FileResolveLag() {
        super("ResolveLag.yml");
    }

    @Override
    public void onLoad() {
        FileResolveLag.ENABLE = this.getConfiguration().getBoolean("Enable");

        FileResolveLag.SETTING_DATEFORMAT = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_DATEFORAMT : this.getConfiguration().getString("Setting.DateFormat");
        FileResolveLag.SETTING_PREFIX = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_VARIABLE_ENABLE ? FileConfig.OVERALL_SITUATION_VARIABLE_PREFIX : this.getConfiguration().getString("Setting.Prefix");

        FileResolveLag.RESOLVE_LAG_SECOND_TIME = this.getConfiguration().getInt("ResolveLag.SecondTime");

        FileResolveLag.RESOLVE_LAG_ENTITY_PET = this.getConfiguration().getBoolean("ResolveLag.Entity.Pet");
        FileResolveLag.RESOLVE_LAG_ENTITY_NAMED = this.getConfiguration().getBoolean("ResolveLag.Entity.Named");

        FileResolveLag.RESOLVE_LAG_ENTITY_SPECIAL_POSE = this.getConfiguration().getBoolean("ResolveLag.Entity.SpecialPose");
        FileResolveLag.RESOLVE_LAG_ENTITY_TOTAL_ENABLE = this.getConfiguration().getBoolean("ResolveLag.Entity.Total.Enable");

        FileResolveLag.RESOLVE_LAG_ENTITY_TOTAL_LIMIT = this.getConfiguration().getInt("ResolveLag.Entity.Total.Limit");
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
            entityList.add(s);
        }
        FileResolveLag.RESOLVE_LAG_ENTITY_SPECIAL_ITEM = itemList;
        FileResolveLag.RESOLVE_LAG_ENTITY_SPECIAL_ENTITY = entityList;

        FileResolveLag.RESOLVE_LAG_CHUNK_INTERVAL = this.getConfiguration().getInt("ResolveLag.Chunk.Interval");
        FileResolveLag.RESOLVE_LAG_CHUNK_ENABLE = this.getConfiguration().getBoolean("ResolveLag.Chunk.Enable");
        FileResolveLag.RESOLVE_LAG_CHUNK_TOTAL_ENABLE = this.getConfiguration().getBoolean("ResolveLag.Chunk.Total.Limit");
        FileResolveLag.RESOLVE_LAG_CHUNK_TOTAL_LIMIT = this.getConfiguration().getInt("ResolveLag.Chunk.Total.Limit");

        FileResolveLag.RESOLVE_LAG_WHITE_WORLD = this.getConfiguration().getStringList("ResolveLag.WhiteWorld");
        FileResolveLag.RESOLVE_LAG_COUNT_TIME = this.getConfiguration().getIntegerList("ResolveLag.CountTime");

        FileResolveLag.CLEAR_MESSAGE_COUNT_DOWN = this.getConfiguration().getString("ClearMessage.CountDown");

        FileResolveLag.MESSAGE_RELOAD = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_RELOAD : this.getConfiguration().getString("Message.Reload");
        FileResolveLag.MESSAGE_MISSING_PERMISSION = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_MISSING_PERMISSION : this.getConfiguration().getString("Message.MissingPermission");
        FileResolveLag.MESSAGE_HELP = FileConfig.OVERALL_SITUATION_ENABLE && FileConfig.OVERALL_SITUATION_MESSAGE_ENABLE ? FileConfig.OVERALL_SITUATION_MESSAGE_HELP : this.getConfiguration().getString("Message.Help");

        FileResolveLag.MESSAGE_UNKNOWN_WORLD = this.getConfiguration().getString("Message.UnknownWorld");
        FileResolveLag.MESSAGE_WORLD_STATE = this.getConfiguration().getString("Message.WorldState");
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onDisable() {

    }
}