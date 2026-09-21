package click.seichi.eategg.commands;

import click.seichi.eategg.IsUuidIgnored;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class Toggle extends Executor {
  public Toggle() {
    super("toggle");
  }

  @Override
  public void execute(CommandContext context) {
    if (!(context.sender() instanceof Player player)) {
      context.sender().sendMessage(ChatColor.RED + "このコマンドはゲーム内からのみ実行できます。");
      return;
    }

    if (!player.hasPermission("eategg." + getCommandName())) {
      context.sender().sendMessage(ChatColor.RED + "このコマンドを実行する権限がありません。");
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
