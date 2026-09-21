package click.seichi.eategg.commands;

import click.seichi.eategg.config.EnabledWorlds;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Reload extends Executor {
  private final JavaPlugin plugin;

  public Reload(JavaPlugin plugin) {
    super("reload");
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandContext context) {
    String permission = "eategg." + getCommandName();
    if (context.sender() instanceof Player player && !player.hasPermission(permission)) {
      player.sendMessage(ChatColor.RED + "このコマンドを実行する権限がありません。: " + permission);
      return;
    }

    EnabledWorlds.load(plugin);
    context.sender().sendMessage(ChatColor.GREEN + "設定を再読み込みしました。");
  }
}
