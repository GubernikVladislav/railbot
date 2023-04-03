package ru.gubernik.rails.railbot.bot.command;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandRegistry {

    private final Map<String, BotCommand> commands;

    public CommandRegistry(List<BotCommand> rawCommands) {
        this.commands = new HashMap<>();
        rawCommands.forEach(command -> commands.put(command.supportedCommand(), command));
    }

    public BotCommand getCommand(String command) {
        return commands.get(command);
    }
}
