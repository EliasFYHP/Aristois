package me.xiaoying.serverbuild.module;

import me.xiaoying.serverbuild.file.FileFileMonitor;
import me.xiaoying.serverbuild.listener.FileMonitorListener;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Module FileMonitor
 */
public class FileMonitorModule extends Module {
    private final FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(ServerUtil.getDataFolder());
    private final FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(500, this.fileAlterationObserver);

    private final List<FileAlterationListener> listeners = new ArrayList<>();

    @Override
    public String getName() {
        return "文件监控";
    }

    @Override
    public String getAliasName() {
        return "FileMonitor";
    }

    @Override
    public boolean ready() {
        return FileFileMonitor.ENABLE;
    }

    @Override
    public void init() {
        this.listeners.add(new FileMonitorListener());

        this.listeners.forEach(this.fileAlterationObserver::addListener);
    }

    @Override
    public void onEnable() {
        try { this.fileAlterationMonitor.start(); } catch (Exception e) {}
    }

    @Override
    public void onDisable() {
        try { this.fileAlterationMonitor.stop(); } catch (Exception e) {}
        this.listeners.forEach(this.fileAlterationObserver::removeListener);
    }
}