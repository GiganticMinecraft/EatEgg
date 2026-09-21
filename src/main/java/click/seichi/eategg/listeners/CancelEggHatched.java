package click.seichi.eategg.listeners;

import click.seichi.eategg.IsUuidIgnored;
import click.seichi.eategg.config.EnabledWorlds;
import click.seichi.eategg.externals.WorldGuardInstance;
import java.util.EnumSet;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public final class CancelEggHatched implements Listener {
  private static final Set<Material> SPAWN_EGGS =
      EnumSet.of(
          Material.ACACIA_BOAT,
          Material.AXOLOTL_SPAWN_EGG,
          Material.BAT_SPAWN_EGG,
          Material.BEE_SPAWN_EGG,
          Material.BLAZE_SPAWN_EGG,
          Material.CAT_SPAWN_EGG,
          Material.CAVE_SPIDER_SPAWN_EGG,
          Material.CHICKEN_SPAWN_EGG,
          Material.COD_SPAWN_EGG,
          Material.COW_SPAWN_EGG,
          Material.CREEPER_SPAWN_EGG,
          Material.DOLPHIN_SPAWN_EGG,
          Material.DONKEY_SPAWN_EGG,
          Material.DRAGON_EGG,
          Material.DROWNED_SPAWN_EGG,
          Material.ELDER_GUARDIAN_SPAWN_EGG,
          Material.ENDERMAN_SPAWN_EGG,
          Material.ENDERMITE_SPAWN_EGG,
          Material.EVOKER_SPAWN_EGG,
          Material.FOX_SPAWN_EGG,
          Material.GHAST_SPAWN_EGG,
          Material.GLOW_SQUID_SPAWN_EGG,
          Material.GOAT_SPAWN_EGG,
          Material.GUARDIAN_SPAWN_EGG,
          Material.HOGLIN_SPAWN_EGG,
          Material.HORSE_SPAWN_EGG,
          Material.HUSK_SPAWN_EGG,
          Material.LLAMA_SPAWN_EGG,
          Material.MAGMA_CUBE_SPAWN_EGG,
          Material.MOOSHROOM_SPAWN_EGG,
          Material.MULE_SPAWN_EGG,
          Material.OCELOT_SPAWN_EGG,
          Material.PANDA_SPAWN_EGG,
          Material.PARROT_SPAWN_EGG,
          Material.PHANTOM_SPAWN_EGG,
          Material.PIG_SPAWN_EGG,
          Material.PIGLIN_BRUTE_SPAWN_EGG,
          Material.PIGLIN_SPAWN_EGG,
          Material.PILLAGER_SPAWN_EGG,
          Material.POLAR_BEAR_SPAWN_EGG,
          Material.PUFFERFISH_SPAWN_EGG,
          Material.RABBIT_SPAWN_EGG,
          Material.RAVAGER_SPAWN_EGG,
          Material.SALMON_SPAWN_EGG,
          Material.SHEEP_SPAWN_EGG,
          Material.SHULKER_SPAWN_EGG,
          Material.SILVERFISH_SPAWN_EGG,
          Material.SKELETON_HORSE_SPAWN_EGG,
          Material.SKELETON_SPAWN_EGG,
          Material.SLIME_SPAWN_EGG,
          Material.SPIDER_SPAWN_EGG,
          Material.SQUID_SPAWN_EGG,
          Material.STRAY_SPAWN_EGG,
          Material.STRIDER_SPAWN_EGG,
          Material.TRADER_LLAMA_SPAWN_EGG,
          Material.TROPICAL_FISH_SPAWN_EGG,
          Material.TURTLE_EGG,
          Material.TURTLE_SPAWN_EGG,
          Material.VEX_SPAWN_EGG,
          Material.VILLAGER_SPAWN_EGG,
          Material.VINDICATOR_SPAWN_EGG,
          Material.WANDERING_TRADER_SPAWN_EGG,
          Material.WITCH_SPAWN_EGG,
          Material.WITHER_SKELETON_SPAWN_EGG,
          Material.WOLF_SPAWN_EGG,
          Material.ZOGLIN_SPAWN_EGG,
          Material.ZOMBIE_HORSE_SPAWN_EGG,
          Material.ZOMBIE_SPAWN_EGG,
          Material.ZOMBIE_VILLAGER_SPAWN_EGG,
          Material.ZOMBIFIED_PIGLIN_SPAWN_EGG);

  @EventHandler
  public void onEggThrown(PlayerEggThrowEvent event) {
    var egg = event.getEgg();
    var player = event.getPlayer();

    if (!EnabledWorlds.contains(player.getWorld().getName())) {
      return;
    }
    if (IsUuidIgnored.get(player.getUniqueId())) {
      return;
    }
    if (WorldGuardInstance.getRegionsByLocation(egg.getLocation()).stream()
        .anyMatch(region -> region.isOwner(WorldGuardInstance.wrapPlayer(player)))) {
      return;
    }

    event.setHatching(false);
  }

  @EventHandler
  public void onSpawnEggThrown(PlayerInteractEvent event) {
    var player = event.getPlayer();

    if (!EnabledWorlds.contains(player.getWorld().getName())) {
      return;
    }
    if (IsUuidIgnored.get(player.getUniqueId())) {
      return;
    }
    if (!SPAWN_EGGS.contains(player.getInventory().getItemInMainHand().getType())) {
      return;
    }
    if (WorldGuardInstance.getRegionsByLocation(event.getClickedBlock().getLocation()).stream()
        .anyMatch(region -> region.isOwner(WorldGuardInstance.wrapPlayer(player)))) {
      return;
    }

    event.setCancelled(true);
  }
}
