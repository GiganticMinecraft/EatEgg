package click.seichi.eategg.commands

import click.seichi.eategg.IsUuidIgnored
import org.bukkit.ChatColor
import org.bukkit.entity.Player

case class Toggle() extends Executor {
  override val commandName: String = "toggle"

  override def execute(context: CommandContext): Unit = {
    val player = context.sender match {
      case p: Player => p
      case _         =>
        context.sender.sendMessage(s"${ChatColor.RED}このコマンドはゲーム内からのみ実行できます。")
        return
    }

    if (!player.hasPermission(s"eategg.$commandName")) {
      context.sender.sendMessage(s"${ChatColor.RED}このコマンドを実行する権限がありません。")
      return
    }

    val uuid = player.getUniqueId
    IsUuidIgnored.toggle(uuid)

    val message =
      if (IsUuidIgnored.get(uuid)) s"${ChatColor.GREEN}EatEggの設定をバイパスします。"
      else s"${ChatColor.RED}EatEggの設定をバイパスしません。"
    player.sendMessage(message)
  }
}
