package click.seichi.eategg.commands

import org.bukkit.plugin.java.JavaPlugin

case class MainCommand()(implicit plugin: JavaPlugin)
    extends BranchExecutor("eategg", Set(Reload())) {}
