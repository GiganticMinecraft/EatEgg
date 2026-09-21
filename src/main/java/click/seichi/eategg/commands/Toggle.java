package click.seichi.eategg.commands;

import click.seichi.eategg.IsUuidIgnored;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class Toggle {
  public void execute(@NotNull CommandSender sender) {
    if (!(sender instanceof Player player)) {
      sender.sendMessage(ChatColor.RED + "このコマンドはゲーム内からのみ実行できます。");
      return;
    }

    if (!player.hasPermission("eategg.toggle")) {
      sender.sendMessage(ChatColor.RED + "このコマンドを実行する権限がありません。");
      return;
    }

    var uuid = player.getUniqueId();
    IsUuidIgnored.toggle(uuid);

    String message =
        IsUuidIgnored.get(uuid)
            ? ChatColor.GREEN + "EatEggの設定をバイパスします。"
            : ChatColor.RED + "EatEggの設定をバイパスしません。";
    player.sendMessage(message);
  }
}
