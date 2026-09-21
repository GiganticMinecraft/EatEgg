package click.seichi.eategg.commands;

import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public record CommandContext(CommandSender sender, Command command, List<String> args) {}
