package de.hglabor.bedwars.commands;

import de.hglabor.bedwars.gui.SettingsGui;
import de.hglabor.bedwars.utils.PacketUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SettingsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("hglabor.bedwars.settings")) {
            if(sender instanceof Player) {
                PacketUtils.sendNotification((Player) sender, "hglabor", "test");
                ((Player) sender).openInventory(SettingsGui.createGui((Player) sender));
            }
        }
        return false;
    }
}
