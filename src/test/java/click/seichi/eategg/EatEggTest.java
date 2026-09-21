package click.seichi.eategg;

import static click.seichi.eategg.config.EnabledWorlds.contains;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EatEggTest {
  private ServerMock server;
  private EatEgg plugin;

  @BeforeEach
  void setUp() {
    server = MockBukkit.mock();
    plugin = MockBukkit.load(EatEgg.class);
  }

  @AfterEach
  void tearDown() {
    MockBukkit.unmock();
  }

  @Test
  void toggleCommandChangesThePlayerBypass() {
    PlayerMock player = server.addPlayer();
    player.addAttachment(plugin, "eategg.toggle", true);

    assertTrue(player.performCommand("eategg toggle"));
    assertTrue(IsUuidIgnored.get(player.getUniqueId()));

    assertTrue(player.performCommand("eategg toggle"));
    assertFalse(IsUuidIgnored.get(player.getUniqueId()));
  }

  @Test
  void toggleCommandRequiresPermission() {
    PlayerMock player = server.addPlayer();
    player.addAttachment(plugin, "eategg.toggle", false);

    assertTrue(player.performCommand("eategg toggle"));
    assertFalse(IsUuidIgnored.get(player.getUniqueId()));
  }

  @Test
  void reloadCommandLoadsEnabledWorlds() {
    PlayerMock player = server.addPlayer();
    player.addAttachment(plugin, "eategg.reload", true);
    plugin.getConfig().set("enabled-worlds", List.of("WORLD", "world_nether"));

    assertTrue(player.performCommand("eategg reload"));
    assertTrue(contains("world"));
    assertTrue(contains("WORLD_NETHER"));
  }
}
