package click.seichi.eategg.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import click.seichi.eategg.EatEgg;
import click.seichi.eategg.IsUuidIgnored;
import org.bukkit.ChatColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ToggleTest {
  private ServerMock server;
  private EatEgg plugin;
  private Toggle toggle;

  @BeforeEach
  void setUp() {
    server = MockBukkit.mock();
    plugin = MockBukkit.load(EatEgg.class);
    toggle = new Toggle();
  }

  @AfterEach
  void tearDown() {
    MockBukkit.unmock();
  }

  @Test
  void playerWithPermissionTogglesTheBypass() {
    PlayerMock player = server.addPlayer();
    player.addAttachment(plugin, "eategg.toggle", true);

    toggle.execute(player);

    assertTrue(IsUuidIgnored.get(player.getUniqueId()));
    assertEquals(ChatColor.GREEN + "EatEggの設定をバイパスします。", player.nextMessage());

    toggle.execute(player);

    assertFalse(IsUuidIgnored.get(player.getUniqueId()));
    assertEquals(ChatColor.RED + "EatEggの設定をバイパスしません。", player.nextMessage());
  }

  @Test
  void playerWithoutPermissionCannotToggleTheBypass() {
    PlayerMock player = server.addPlayer();
    player.addAttachment(plugin, "eategg.toggle", false);

    toggle.execute(player);

    assertFalse(IsUuidIgnored.get(player.getUniqueId()));
    assertEquals(ChatColor.RED + "このコマンドを実行する権限がありません。", player.nextMessage());
  }

  @Test
  void nonPlayerCannotToggleTheBypass() {
    ConsoleCommandSenderMock console = (ConsoleCommandSenderMock) server.getConsoleSender();

    toggle.execute(console);

    assertEquals(ChatColor.RED + "このコマンドはゲーム内からのみ実行できます。", console.nextMessage());
  }
}
