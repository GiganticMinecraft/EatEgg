package click.seichi.eategg.listeners;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import click.seichi.eategg.EatEgg;
import click.seichi.eategg.IsUuidIgnored;
import click.seichi.eategg.config.EnabledWorlds;
import java.lang.reflect.Proxy;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CancelEggHatchedTest {
  private ServerMock server;
  private EatEgg plugin;

  @BeforeEach
  void setUp() {
    server = MockBukkit.mock();
    plugin = MockBukkit.load(EatEgg.class);
    plugin.getConfig().set("enabled-worlds", List.of("world"));
    EnabledWorlds.load(plugin);
  }

  @AfterEach
  void tearDown() {
    MockBukkit.unmock();
  }

  @Nested
  class EggThrowTests {
    @Test
    void eggHatchingIsCancelledInEnabledWorld() {
      PlayerMock player = server.addPlayer();
      PlayerEggThrowEvent event = eggThrowEvent(player);

      new CancelEggHatched((location, owner) -> false).onEggThrown(event);

      assertFalse(event.isHatching());
    }

    @Test
    void eggHatchingIsAllowedForRegionOwner() {
      PlayerMock player = server.addPlayer();
      PlayerEggThrowEvent event = eggThrowEvent(player);

      new CancelEggHatched((location, owner) -> true).onEggThrown(event);

      assertTrue(event.isHatching());
    }

    @Test
    void eggHatchingIsAllowedWhenBypassIsEnabled() {
      PlayerMock player = server.addPlayer();
      IsUuidIgnored.toggle(player.getUniqueId());
      PlayerEggThrowEvent event = eggThrowEvent(player);

      new CancelEggHatched((location, owner) -> false).onEggThrown(event);

      assertTrue(event.isHatching());
    }

    @Test
    void eggHatchingIsAllowedInDisabledWorld() {
      plugin.getConfig().set("enabled-worlds", List.of("other"));
      EnabledWorlds.load(plugin);
      PlayerMock player = server.addPlayer();
      PlayerEggThrowEvent event = eggThrowEvent(player);

      new CancelEggHatched((location, owner) -> false).onEggThrown(event);

      assertTrue(event.isHatching());
    }
  }

  // PlayerInteractEvent#isCancelled() は、ブロック操作と手持ちアイテム操作が別々の結果を持つため非推奨。
  // このリスナーはブロック操作をキャンセルするため、ここでは useInteractedBlock() を検証する。
  @Nested
  class SpawnEggInteractTests {
    @Test
    void spawnEggUseIsCancelledOnBlockInEnabledWorld() {
      PlayerMock player = server.addPlayer();
      player.getInventory().setItemInMainHand(new ItemStack(Material.ZOMBIE_SPAWN_EGG));
      PlayerInteractEvent event = blockInteractEvent(player);

      new CancelEggHatched((location, owner) -> false).onSpawnEggThrown(event);

      assertEquals(Event.Result.DENY, event.useInteractedBlock());
    }

    @Test
    void spawnEggUseIsAllowedForRegionOwner() {
      PlayerMock player = server.addPlayer();
      player.getInventory().setItemInMainHand(new ItemStack(Material.ZOMBIE_SPAWN_EGG));
      PlayerInteractEvent event = blockInteractEvent(player);

      new CancelEggHatched((location, owner) -> true).onSpawnEggThrown(event);

      assertNotEquals(Event.Result.DENY, event.useInteractedBlock());
    }

    @Test
    void nonSpawnEggUseIsIgnored() {
      PlayerMock player = server.addPlayer();
      player.getInventory().setItemInMainHand(new ItemStack(Material.EGG));
      PlayerInteractEvent event = blockInteractEvent(player);

      new CancelEggHatched((location, owner) -> false).onSpawnEggThrown(event);

      assertNotEquals(Event.Result.DENY, event.useInteractedBlock());
    }

    @Test
    void spawnEggUseInAirIsIgnored() {
      PlayerMock player = server.addPlayer();
      player.getInventory().setItemInMainHand(new ItemStack(Material.ZOMBIE_SPAWN_EGG));
      PlayerInteractEvent event =
          new PlayerInteractEvent(
              player,
              Action.RIGHT_CLICK_AIR,
              new ItemStack(Material.ZOMBIE_SPAWN_EGG),
              null,
              BlockFace.SELF);
      event.setCancelled(false);

      assertDoesNotThrow(
          () -> new CancelEggHatched((location, owner) -> false).onSpawnEggThrown(event));
      assertNotEquals(Event.Result.DENY, event.useInteractedBlock());
    }
  }

  private PlayerEggThrowEvent eggThrowEvent(PlayerMock player) {
    return new PlayerEggThrowEvent(
        player, eggAt(player.getLocation()), true, (byte) 1, EntityType.CHICKEN);
  }

  private PlayerInteractEvent blockInteractEvent(PlayerMock player) {
    return new PlayerInteractEvent(
        player,
        Action.RIGHT_CLICK_BLOCK,
        new ItemStack(Material.ZOMBIE_SPAWN_EGG),
        player.getWorld().getBlockAt(player.getLocation()),
        BlockFace.UP);
  }

  private static Egg eggAt(Location location) {
    return (Egg)
        Proxy.newProxyInstance(
            Egg.class.getClassLoader(),
            new Class<?>[] {Egg.class},
            (proxy, method, args) -> {
              if (method.getName().equals("getLocation")) {
                return location;
              }
              if (method.getReturnType() == boolean.class) {
                return false;
              }
              if (method.getReturnType() == byte.class) {
                return (byte) 0;
              }
              if (method.getReturnType() == short.class) {
                return (short) 0;
              }
              if (method.getReturnType() == int.class) {
                return 0;
              }
              if (method.getReturnType() == long.class) {
                return 0L;
              }
              if (method.getReturnType() == float.class) {
                return 0F;
              }
              if (method.getReturnType() == double.class) {
                return 0D;
              }
              if (method.getReturnType() == char.class) {
                return '\0';
              }
              return null;
            });
  }
}
