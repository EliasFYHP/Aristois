package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.command.resolvelag.ResolveLagCommand;
import me.xiaoying.serverbuild.constant.ResolveLagConstant;
import me.xiaoying.serverbuild.entity.ResolveLagEntity;
import me.xiaoying.serverbuild.file.FileResolveLag;
import me.xiaoying.serverbuild.scheduler.ResolveLagScheduler;
import me.xiaoying.serverbuild.utils.YamlUtil;

import java.util.*;

public class ResolveLagModule extends Module {
    private FileResolveLag file;
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
        this.file = new FileResolveLag();
        // register files
        this.registerFile(this.file);

        // register commands
        this.registerCommand(new ResolveLagCommand());

        // register scheduler
        this.registerScheduler(new ResolveLagScheduler());
    }

    @Override
    public void onEnable() {
        YamlUtil.getNodes(this.file.getFile().getAbsolutePath(), "ClearMessage.ClearDown").forEach(object -> {
            String string = object.toString();
            int integer;
            try {
                integer = Integer.parseInt(string);
            } catch (Exception e) {
                return;
            }
            this.resolveLagEntities.add(new ResolveLagEntity(integer,
                    this.file.getConfiguration().getString("ClearMessage.ClearDown." + string + ".Type"),
                    this.file.getConfiguration().getString("ClearMessage.ClearDown." + string + ".Message")));
        });
    }

    @Override
    public void onDisable() {
        this.resolveLagEntities.clear();
    }

    public List<ResolveLagEntity> getResolveLagEntities() {
        return this.resolveLagEntities;
    }
}