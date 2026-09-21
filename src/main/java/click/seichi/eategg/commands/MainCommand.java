package click.seichi.eategg.commands;

import java.util.List;
import java.util.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class MainCommand implements TabExecutor {
  private static final @NotNull List<String> SUBCOMMANDS = List.of("toggle", "reload");

  private final @NotNull Toggle toggle;
  private final @NotNull Reload reload;

  public MainCommand(@NotNull JavaPlugin plugin) {
    this.toggle = new Toggle();
    this.reload = new Reload(plugin);
  }

  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String alias,
      @NotNull String[] args) {
    if (args.length == 0) {
      sendUsage(sender, command);
      return true;
    }

    switch (args[0].toLowerCase(Locale.ROOT)) {
      case "toggle" -> toggle.execute(sender);
      case "reload" -> reload.execute(sender);
      default -> sendUsage(sender, command);
    }
    return true;
  }

  @Override
  public @NotNull List<String> onTabComplete(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String alias,
      @NotNull String[] args) {
    if (args.length > 1) {
      return List.of();
    }

    String prefix = args.length == 0 ? "" : args[0].toLowerCase(Locale.ROOT);
    return SUBCOMMANDS.stream().filter(subcommand -> subcommand.startsWith(prefix)).toList();
  }

  private static void sendUsage(@NotNull CommandSender sender, @NotNull Command command) {
    sender.sendMessage(command.getUsage());
  }
}
