package me.xiaoying.serverbuild.command.resolvelag.commands;

import me.xiaoying.serverbuild.command.Command;
import me.xiaoying.serverbuild.command.SCommand;
import me.xiaoying.serverbuild.constant.ResolveLagConstant;
import me.xiaoying.serverbuild.core.SBPlugin;
import me.xiaoying.serverbuild.entity.ResolveLagEntity;
import me.xiaoying.serverbuild.factory.VariableFactory;
import me.xiaoying.serverbuild.module.ResolveLagModule;
import me.xiaoying.serverbuild.scheduler.ResolveLagScheduler;
import me.xiaoying.serverbuild.utils.ListUtil;
import me.xiaoying.serverbuild.utils.ServerUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@Command(values = "clear", length = 0)
public class RLClearCommand extends SCommand {
    @Override
    public List<String> getHelpMessage() {
        List<String> list = new ArrayList<>();
        list.add(new VariableFactory(ResolveLagConstant.MESSAGE_HELP)
                .prefix(ResolveLagConstant.SETTING_PREFIX)
                .date(ResolveLagConstant.SETTING_DATEFORMAT)
                .color()
                .toString());
        return list;
    }

    @Override
    public void performCommand(CommandSender sender, String[] strings) {
        if (!ServerUtil.hasPermission(sender, "sb.admin", "sb.rl.admin") && !sender.isOp()) {
            sender.sendMessage(new VariableFactory(ResolveLagConstant.MESSAGE_MISSING_PERMISSION)
                    .prefix(ResolveLagConstant.SETTING_PREFIX)
                    .date(ResolveLagConstant.SETTING_DATEFORMAT)
                    .color()
                    .toString());
            return;
        }

        ResolveLagScheduler resolveLagScheduler = new ResolveLagScheduler();
        int entitiesCount = resolveLagScheduler.clearEntity();
        int chunksCount = resolveLagScheduler.clearChunk();
        int count = entitiesCount + chunksCount;
        boolean hasBigOperator = false;
        List<ResolveLagEntity> trueNode = new ArrayList<>();
        ResolveLagModule resolveLagModule = (ResolveLagModule) SBPlugin.getModuleManager().getModule("ResolveLag");
        for (ResolveLagEntity clearDown : resolveLagModule.getResolveLagEntities()) {
            if (!resolveLagScheduler.compareInteger(count, clearDown.getId(), clearDown.getType()))
                continue;

            // 当满足多个条件判断用最大值还是最小值
            if (clearDown.getType().contains(">"))
                hasBigOperator = true;

            trueNode.add(clearDown);
        }

        List<Integer> list = new ArrayList<>();
        for (ResolveLagEntity clearEntityEntity : trueNode)
            list.add(clearEntityEntity.getId());

        int time;
        if (hasBigOperator) {
            time = ListUtil.getListMaxNumber(list);
        } else
            time = ListUtil.getListMinNumber(list);

        ResolveLagEntity entity = null;
        for (ResolveLagEntity clearEntityEntity : trueNode) {
            if (time != clearEntityEntity.getId())
                continue;

            entity = clearEntityEntity;
        }

        assert entity != null;
        String message = entity.getMessage();
        ServerUtil.getOnlinePlayers().forEach(player -> player.sendMessage(new VariableFactory(message)
                .prefix(ResolveLagConstant.SETTING_PREFIX)
                .date(ResolveLagConstant.SETTING_DATEFORMAT)
                .entities(entitiesCount)
                .chunks(chunksCount)
                .amount(count)
                .player(player)
                .placeholder(player)
                .color()
                .toString()));
    }
}