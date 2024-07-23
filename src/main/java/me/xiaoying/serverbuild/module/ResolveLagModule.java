package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.constant.ResolveLagConstant;
import me.xiaoying.serverbuild.entity.ResolveLagEntity;
import me.xiaoying.serverbuild.file.FileResolveLag;
import me.xiaoying.serverbuild.listener.ResolveLagListener;
import me.xiaoying.serverbuild.scheduler.ResolveLagScheduler;
import me.xiaoying.serverbuild.utils.YamlUtil;
import org.bukkit.Chunk;

import java.util.*;

public class ResolveLagModule extends Module {
    private final Map<Chunk, Date> cacheChunk = new HashMap<>();
    private final List<ResolveLagEntity> resolveLagEntities = new ArrayList<>();

    @Override
    public String getName() {
        return "服务器清理";
    }

    @Override
    public String getAliasName() {
        return "ResolveLag";
    }

    @Override
    public boolean ready() {
        return ResolveLagConstant.ENABLE;
    }

    @Override
    public void init() {
        FileResolveLag file = new FileResolveLag();
        // register files
        this.registerFile(file);
        YamlUtil.getNodes(file.getFile().getAbsolutePath(), "ClearMessage.ClearDown").forEach(object -> {
            String string = object.toString();
            int integer;
            try {
                integer = Integer.parseInt(string);
            } catch (Exception e) {
                return;
            }
            this.resolveLagEntities.add(new ResolveLagEntity(integer,
                    file.getConfiguration().getString("ClearMessage.ClearDown.." + string + ".Type"),
                    file.getConfiguration().getString("ClearMessage.ClearDown.." + string + ".Message")));
        });

        // register listeners
        this.registerListener(new ResolveLagListener());

        // register scheduler
        this.registerScheduler(new ResolveLagScheduler());
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        this.removeChunks();
        this.resolveLagEntities.clear();
    }

    public List<ResolveLagEntity> getResolveLagEntities() {
        return this.resolveLagEntities;
    }

    public void addChunk(Chunk chunk) {
        this.cacheChunk.put(chunk, new Date());
    }

    public void removeChunk(Chunk chunk) {
        this.cacheChunk.remove(chunk);
    }

    public void removeChunks() {
        this.cacheChunk.clear();
    }

    public Map<Chunk, Date> getCacheChunk() {
        return this.cacheChunk;
    }
}