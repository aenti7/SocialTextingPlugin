package com.example.st;

import org.bukkit.Bukkit;
import org.bukkit.Player;
import org.bukkit.configuration.file.FileConfiguration; 
import java.util.List;

// Импортируй сюда свой главный класс вместо Main, если он называется иначе!
import com.example.st.Main; 

public class STCommand {

    public static boolean processSTCommand(Player commandSender, String[] args) {
        // Нужно минимум 4 части: /st [m/p] [rus/eng] [ник] [что-то ещё]
        if (args.length < 4) {
            commandSender.sendMessage("Неверный формат. Напиши /st, чтобы увидеть примеры.");
            return true;
        }

        String mode = args[0].toLowerCase();
        String language = args[1].toLowerCase();
        String targetPlayer = args[2];

        // --- ГЛАВНОЕ ИЗМЕНЕНИЕ: собираем ВСЁ, что после ника, в одну фразу ---
        StringBuilder argumentBuilder = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            if (i > 3) argumentBuilder.append(" "); // добавляем пробел между словами
            argumentBuilder.append(args[i]);
        }
        String argument = argumentBuilder.toString();
        // --------------------------------------------------------------------

        String senderName = commandSender.getName();
        String finalMessage;

        if ("p".equals(mode)) {
            // Режим P: оскорбление (тут аргумент — это просто текст)
            if ("rus".equals(language)) {
                finalMessage = "|||||||||| §4§lST§r: §l" + senderName + "§r обозвал §l" + targetPlayer + "§r как \"" + argument + "\". §0§lАЗКАБАН§r проверяет данное оскорбление на наличие экстремистского посыла… §l" + senderName + " - §4ЭКСТРЕМИСТ! ДЕПОРТИРОВАТЬ В АЗКАБАН!!!§r §a§lST§r §a§oработает в штатном режиме!§r ||||||||||";
            } else {
                finalMessage = "|||||||||| §4§lST§r: §l" + senderName + "§r called §l" + targetPlayer + "§r a \"" + argument + "\". §0§lAZKABAN§r is reviewing this insult for extremist connotations… §l" + senderName + " is an §4EXTREMIST! DEPORT TO AZKABAN!!!§r §a§lST§r §a§ooperates normally!§r ||||||||||";
            }
            Bukkit.broadcastMessage(finalMessage);
            return true;

        } else if ("m".equals(mode)) {
            String displayText;
            FileConfiguration config = Main.getInstance().getConfig(); // Получаем конфиг

            if (targetPlayer.equalsIgnoreCase(senderName)) {
                // Если ник совпадает с твоим: аргумент — это текст сообщения
                displayText = argument; 
            } else {
                // Если чужой ник: аргумент должен быть числом
                try {
                    int number = Integer.parseInt(argument);
                    if (number <= 0) throw new NumberFormatException();

                    List<String> messages = config.getStringList("messages");

                    // Проверка на случай, если конфиг пустой
                    if (messages == null || messages.isEmpty()) {
                        commandSender.sendMessage("Ошибка: в config.yml нет списка сообщений!");
                        return true;
                    }

                    if (number > messages.size()) {
                        commandSender.sendMessage("Ошибка: сообщения №" + number + " не существует. Всего есть " + messages.size() + " сообщений.");
                        return true;
                    }

                    String realMessage = messages.get(number - 1); // индекс на 1 меньше номера
                    displayText = realMessage; // выводим сам текст из конфига

                } catch (NumberFormatException e) {
                    commandSender.sendMessage("Для чужого ника последний аргумент должен быть натуральным числом (например, 3). Ты ввёл: \"" + argument + "\"");
                    return true;
                }
            }

            String reason = argument;

            if ("rus".equals(language)) {
                finalMessage = "|||||||||| §4§lST§r: Сообщение игрока §l" + targetPlayer + "§r было распознано как §l" + reason + "§r («" + displayText + "»). Не волнуйтесь, автор такого контента уже депортирован в §0§lАЗКАБАН§r. §a§lST§r §a§oработает в штатном режиме!§r ||||||||||";
            } else {
                finalMessage = "|||||||||| §4§lST§r: §l" + targetPlayer + "§r's message was recognised as §l" + reason + "§r (\"" + displayText + "\"). Don't worry, the author of this content has been deported to §0§lAZKABAN§r. §a§lST§r §a§ooperates normally!§r ||||||||||";
            }

            Bukkit.broadcastMessage(finalMessage);
            return true;

        } else {
            commandSender.sendMessage("Первый аргумент должен быть 'p' (обзывательство) или 'm' (контент).");
            return true;
        }
    }
}


