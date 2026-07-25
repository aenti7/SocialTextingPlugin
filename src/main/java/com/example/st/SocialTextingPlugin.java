package com.example.st;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SocialTextingPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        instance = this; // запоминаем себя
        saveDefaultConfig();
        getLogger().info("SocialTexting включён!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эта команда только для игроков.");
            return true;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§4§l[Social Texting] §rДоступные режимы:");
            player.sendMessage("");
            player.sendMessage("Режим P (оскорбление):");
            player.sendMessage("  /st p [rus/eng] [ник] [имя]");
            player.sendMessage("");
            player.sendMessage("Режим M (старый):");
            player.sendMessage("  /st m [rus/eng] [ник] [причина] [сообщение/номер]");
            return true;
        }

        return STCommand.processSTCommand(player, args);
    }
}
