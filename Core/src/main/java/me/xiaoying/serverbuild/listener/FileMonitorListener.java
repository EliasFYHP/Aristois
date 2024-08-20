package me.xiaoying.serverbuild.listener;

import me.xiaoying.serverbuild.constant.FileMonitorConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.bukkit.Bukkit;

import java.io.File;

public class FileMonitorListener extends FileAlterationListenerAdaptor {
    @Override
    public void onFileChange(File file) {
        // 特殊文件
        if (file.getName().equalsIgnoreCase("Config.yml")) {
            SBPlugin.getFileManager().getFile("Config.yml").load();
            return;
        }

        SBPlugin.getModuleManager().getModules().forEach(module -> {
            for (me.xiaoying.serverbuild.file.File moduleFile : module.getFiles()) {
                if (!moduleFile.getName().equalsIgnoreCase(file.getName()))
                    return;

                module.reload();

                // script
                FileMonitorConstant.FILE_MONITOR_EVENT.forEach(script -> SBPlugin.getScriptManager().performScript(script, Bukkit.getServer().getConsoleSender()));
            }
        });
    }
}