package click.seichi.eategg.externals

import com.sk89q.worldguard.LocalPlayer
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import org.bukkit.Location
import org.bukkit.entity.Player

import scala.jdk.CollectionConverters._

object WorldGuardInstance {
  private val instance = {
    val worldGuard = Option(WorldGuardPlugin.inst())
    require(worldGuard.isDefined, "WorldGuard is not found.")

    worldGuard.get
  }

  def getRegionsByLocation(location: Location): Set[ProtectedRegion] =
    instance
      .getRegionManager(location.getWorld)
      .getApplicableRegions(location)
      .getRegions
      .asScala
      .toSet

  def wrapPlayer(player: Player): LocalPlayer = instance.wrapPlayer(player)
}
