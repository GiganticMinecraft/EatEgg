package click.seichi.eategg

import click.seichi.eategg.commands.MainCommand
import click.seichi.eategg.listeners.CancelEggHatched
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

// TODO: CI
// TODO: debug

class EatEgg extends JavaPlugin {
  override def onEnable(): Unit = {
    implicit val instance: EatEgg = this

    saveDefaultConfig()

    MainCommand().register(this)
    Bukkit.getPluginManager.registerEvents(CancelEggHatched, this)

    this.getLogger.info("EatEgg is enabled.")
  }
}
