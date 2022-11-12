package click.seichi.eategg

import click.seichi.eategg.commands.MainCommand
import click.seichi.eategg.listeners.{CancelEggHatched, ResetFlags}
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

// TODO: debug

class EatEgg extends JavaPlugin {
  override def onEnable(): Unit = {
    implicit val instance: EatEgg = this

    saveDefaultConfig()

    MainCommand().register(this)
    Set(CancelEggHatched, ResetFlags).foreach(listener =>
      Bukkit.getPluginManager.registerEvents(listener, this)
    )

    this.getLogger.info("EatEgg is enabled.")
  }
}
