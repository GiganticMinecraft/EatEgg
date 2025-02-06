package click.seichi.eategg.externals

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldguard.LocalPlayer
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.bukkit.WorldGuardPlugin
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import org.bukkit.Location
import org.bukkit.entity.Player

import scala.jdk.CollectionConverters.*

object WorldGuardInstance {
  private val instance = {
    val worldGuard = Option(WorldGuard.getInstance())
    require(worldGuard.isDefined, "WorldGuard is not found.")

    worldGuard.get
  }

  def getRegionsByLocation(location: Location): Set[ProtectedRegion] =
    instance
      .getPlatform
      .getRegionContainer
      .get(BukkitAdapter.adapt(location.getWorld))
      .getApplicableRegions(BlockVector3.at(location.getX, location.getY, location.getZ))
      .getRegions
      .asScala
      .toSet

  def wrapPlayer(player: Player): LocalPlayer = WorldGuardPlugin.inst().wrapPlayer(player)
}
