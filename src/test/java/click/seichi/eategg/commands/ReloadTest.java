package click.seichi.eategg.commands;

import static click.seichi.eategg.config.EnabledWorlds.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import click.seichi.eategg.EatEgg;
import java.util.List;
import org.bukkit.ChatColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReloadTest {
  private ServerMock server;
  private EatEgg plugin;
  private Reload reload;

  @BeforeEach
  void setUp() {
    server = MockBukkit.mock();
    plugin = MockBukkit.load(EatEgg.class);
    reload = new Reload(plugin);
  }

  @AfterEach
  void tearDown() {
    MockBukkit.unmock();
  }

  @Test
  void playerWithPermissionReloadsConfiguration() {
    PlayerMock player = server.addPlayer();
    player.addAttachment(plugin, "eategg.reload", true);
    plugin.getConfig().set("enabled-worlds", List.of("WORLD"));

    reload.execute(player);

    assertTrue(contains("world"));
    assertEquals(ChatColor.GREEN + "設定を再読み込みしました。", player.nextMessage());
  }

  @Test
  void playerWithoutPermissionCannotReloadConfiguration() {
    PlayerMock player = server.addPlayer();
    player.addAttachment(plugin, "eategg.reload", false);
    plugin.getConfig().set("enabled-worlds", List.of("world"));

    reload.execute(player);

    assertFalse(contains("world"));
    String expectedMessage = ChatColor.RED + "このコマンドを実行する権限がありません。: eategg.reload";
    assertEquals(expectedMessage, player.nextMessage());
  }

  @Test
  void consoleCanReloadConfiguration() {
    ConsoleCommandSenderMock console = (ConsoleCommandSenderMock) server.getConsoleSender();
    plugin.getConfig().set("enabled-worlds", List.of("WORLD_NETHER"));

    reload.execute(console);

    assertTrue(contains("world_nether"));
    assertEquals(ChatColor.GREEN + "設定を再読み込みしました。", console.nextMessage());
  }
}
