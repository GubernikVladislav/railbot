package ru.gubernik.rails.railbot.bot.command;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

/**
 * Команда, отправляемая боту
 */
public interface BotCommand {

    List<SendMessage> runCommand(Message message);

    String supportedCommand();
}
