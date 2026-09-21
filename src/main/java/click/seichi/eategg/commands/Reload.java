package click.seichi.eategg.commands;

import click.seichi.eategg.config.EnabledWorlds;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Reload {
  private final @NotNull JavaPlugin plugin;

  public Reload(@NotNull JavaPlugin plugin) {
    this.plugin = plugin;
  }

  public void execute(@NotNull CommandSender sender) {
    String permission = "eategg.reload";
    if (sender instanceof Player player && !player.hasPermission(permission)) {
      player.sendMessage(ChatColor.RED + "このコマンドを実行する権限がありません。: " + permission);
      return;
    }

    EnabledWorlds.load(plugin);
    sender.sendMessage(ChatColor.GREEN + "設定を再読み込みしました。");
  }
}
