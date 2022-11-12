package click.seichi.eategg.listeners

import click.seichi.eategg.IsUuidIgnored
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.{EventHandler, Listener}

object ResetFlags extends Listener {
  @EventHandler
  def onPlayerQuit(event: PlayerQuitEvent): Unit = {
    IsUuidIgnored.reset(event.getPlayer.getUniqueId)
  }
}
