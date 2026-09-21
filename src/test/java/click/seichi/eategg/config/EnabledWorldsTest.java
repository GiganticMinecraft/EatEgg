package click.seichi.eategg.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import click.seichi.eategg.EatEgg;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnabledWorldsTest {
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
  void loadNormalizesWorldNames() {
    plugin.getConfig().set("enabled-worlds", List.of("WORLD", "world_nether", "World"));

    EnabledWorlds.load(plugin);

    assertEquals(Set.of("world", "world_nether"), EnabledWorlds.get());
    assertTrue(EnabledWorlds.contains("WoRlD"));
    assertTrue(EnabledWorlds.contains("WORLD_NETHER"));
  }

  @Test
  void loadReplacesPreviouslyLoadedWorlds() {
    plugin.getConfig().set("enabled-worlds", List.of("world"));
    EnabledWorlds.load(plugin);
    plugin.getConfig().set("enabled-worlds", List.of("world_the_end"));

    EnabledWorlds.load(plugin);

    assertEquals(Set.of("world_the_end"), EnabledWorlds.get());
    assertFalse(EnabledWorlds.contains("world"));
  }

  @Test
  void returnedWorldsCannotBeModified() {
    plugin.getConfig().set("enabled-worlds", List.of("world"));
    EnabledWorlds.load(plugin);

    assertThrows(UnsupportedOperationException.class, () -> EnabledWorlds.get().add("other"));
  }
}
