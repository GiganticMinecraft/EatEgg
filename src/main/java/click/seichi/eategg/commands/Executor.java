package click.seichi.eategg.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Executor implements TabExecutor {
  private final String commandName;

  protected Executor(String commandName) {
    this.commandName = commandName;
  }

  public final String getCommandName() {
    return commandName;
  }

  public abstract void execute(CommandContext context);

  public List<String> completeTab(CommandContext context) {
    return context.args();
  }

  public final void register(JavaPlugin instance) {
    PluginCommand command =
        Objects.requireNonNull(
            instance.getCommand(commandName), () -> "Command is not defined: " + commandName);
    command.setExecutor(this);
  }

  @Override
  public final boolean onCommand(
      CommandSender commandSender, Command command, String alias, String[] args) {
    execute(new CommandContext(commandSender, command, Arrays.asList(args)));
    return true;
  }

  @Override
  public final List<String> onTabComplete(
      CommandSender commandSender, Command command, String alias, String[] args) {
    return completeTab(new CommandContext(commandSender, command, Arrays.asList(args)));
  }
}
