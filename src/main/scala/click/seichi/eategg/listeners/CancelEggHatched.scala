package click.seichi.eategg.listeners

import click.seichi.eategg.IsUuidIgnored
import click.seichi.eategg.externals.WorldGuardInstance
import org.bukkit.event.player.PlayerEggThrowEvent
import org.bukkit.event.{EventHandler, Listener}

object CancelEggHatched extends Listener {
  @EventHandler
  def onEggThrown(event: PlayerEggThrowEvent): Unit = {
    val egg = event.getEgg
    val player = event.getPlayer

    if (IsUuidIgnored.get(player.getUniqueId)) return
    if (
      WorldGuardInstance
        .getRegionsByLocation(egg.getLocation)
        .exists(_.isOwner(WorldGuardInstance.wrapPlayer(player)))
    ) return

    event.setHatching(false)
  }
}
