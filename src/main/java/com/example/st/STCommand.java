package com.example.st;

import org.bukkit.Bukkit;
import org.bukkit.Player;

public class STCommand {

    public static boolean processSTCommand(Player commandSender, String[] args) {
        if (args.length < 4) {
            commandSender.sendMessage("Неверный формат. Напиши просто /st, чтобы увидеть примеры.");
            return true;
        }

        String mode = args[0].toLowerCase();
        String language = args[1].toLowerCase();
        String targetPlayer = args[2];
        String argument = args[3];

        String senderName = commandSender.getName();
        String finalMessage;

        if ("p".equals(mode)) {
            if ("rus".equals(language)) {
                finalMessage = "§4§lST§r: " +
                        "§l" + senderName + "§r обозвал " +
                        "§l" + targetPlayer + "§r как " +
                        "\"" + argument + "\". " +
                        "Азкабан проверяет… §4§lДЕПОРТИРОВАТЬ В АЗКАБАН!!!§r";
            } else {
                finalMessage = "§4§lST§r: " +
                        "§l" + senderName + "§r called " +
                        "§l" + targetPlayer + "§r a \"" + argument + "\". " +
                        "Azkaban is checking… §4§lDEPORT TO AZKABAN!!!§r";
            }
            Bukkit.broadcastMessage(finalMessage);
            return true;

        } else if ("m".equals(mode)) {
            String displayText;
            if (targetPlayer.equalsIgnoreCase(senderName)) {
                if ("rus".equals(language)) {
                    displayText = "сообщение «" + argument + "»";
                } else {
                    displayText = "message \"" + argument + "\"";
                }
            } else {
                try {
                    int number = Integer.parseInt(argument);
                    if (number <= 0) throw new NumberFormatException();
                    if ("rus".equals(language)) {
                        displayText = "сообщение под номером " + number;
                    } else {
                        displayText = "message number " + number;
                    }
                } catch (NumberFormatException e) {
                    commandSender.sendMessage("Для чужого ника аргумент должен быть натуральным числом!");
                    return true;
                }
            }

            if ("rus".equals(language)) {
                finalMessage = "§4§lST§r: " +
                        "Сообщение игрока " +
                        "§l" + targetPlayer + "§r было распознано как " +
                        "§l" + argument + "§r (" + displayText + "). " +
                        "Автор уже депортирован в Азкабан.";
            } else {
                finalMessage = "§4§lST§r: " +
                        "§l" + targetPlayer + "§r's message was recognised as " +
                        "§l" + argument + "§r (" + displayText + "). " +
                        "Author deported to Azkaban.";
            }
            Bukkit.broadcastMessage(finalMessage);
            return true;

        } else {
            commandSender.sendMessage("Первый аргумент должен быть 'p' или 'm'.");
            return true;
        }
    }
}
