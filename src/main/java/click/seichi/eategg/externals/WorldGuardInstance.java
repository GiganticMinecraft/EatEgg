package click.seichi.eategg.externals;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Objects;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class WorldGuardInstance {
  private static final WorldGuard INSTANCE =
      Objects.requireNonNull(WorldGuard.getInstance(), "WorldGuard is not found.");

  private WorldGuardInstance() {}

  public static Set<ProtectedRegion> getRegionsByLocation(Location location) {
    RegionManager regionManager =
        INSTANCE.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(location.getWorld()));

    return Set.copyOf(
        regionManager
            .getApplicableRegions(
                BlockVector3.at(location.getX(), location.getY(), location.getZ()))
            .getRegions());
  }

  public static LocalPlayer wrapPlayer(Player player) {
    return WorldGuardPlugin.inst().wrapPlayer(player);
  }
}
