package click.seichi.eategg.commands

import click.seichi.eategg.EnabledWorlds
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

case class Reload()(implicit plugin: JavaPlugin) extends Executor {
  override val commandName: String = "reload"

  override def execute(context: CommandContext): Unit = {
    val perm = s"eategg.$commandName"
    context.sender match {
      case player: Player if !player.hasPermission(perm) =>
        player.sendMessage(s"${ChatColor.RED}このコマンドを実行する権限がありません。: $perm")
        return
      case _ =>
    }

    EnabledWorlds.load()
    context.sender.sendMessage(s"${ChatColor.GREEN}設定を再読み込みしました。")
  }
}
