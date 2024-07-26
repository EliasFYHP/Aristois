package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.constant.WelcomeMessageConstant;
import me.xiaoying.serverbuild.entity.WelcomeMessageEntity;
import me.xiaoying.serverbuild.file.FileWelcomeMessage;
import me.xiaoying.serverbuild.listener.WelcomeMessageListener;
import me.xiaoying.serverbuild.utils.YamlUtil;

import java.util.ArrayList;
import java.util.List;

public class WelcomeMessageModule extends Module {
    private FileWelcomeMessage file;
    private final List<WelcomeMessageEntity> welcomeMessageEntities = new ArrayList<>();

    @Override
    public String getName() {
        return "欢迎消息";
    }

    @Override
    public String getAliasName() {
        return "WelcomeMessage";
    }

    @Override
    public boolean ready() {
        return WelcomeMessageConstant.ENABLE;
    }

    @Override
    public void init() {
        this.file = new FileWelcomeMessage();
        // register files
        this.registerFile(this.file);

        // register listeners
        this.registerListener(new WelcomeMessageListener());
    }

    @Override
    public void onEnable() {
        YamlUtil.getNodes(this.file.getFile().getAbsolutePath(), "WelcomeMessage").forEach(object -> {
            String string = object.toString();

            this.welcomeMessageEntities.add(new WelcomeMessageEntity(string,
                    this.file.getConfiguration().getInt("WelcomeMessage." + string + ".Priority"),
                    this.file.getConfiguration().getString("WelcomeMessage." + string + ".Permission"),
                    this.file.getConfiguration().getString("WelcomeMessage." + string + ".Join"),
                    this.file.getConfiguration().getString("WelcomeMessage." + string + ".Quit")));
        });
    }

    @Override
    public void onDisable() {
        this.welcomeMessageEntities.clear();
    }

    public List<WelcomeMessageEntity> getWelcomeMessageEntities() {
        return this.welcomeMessageEntities;
    }
}