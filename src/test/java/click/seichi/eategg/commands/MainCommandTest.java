package click.seichi.eategg.commands;

import static click.seichi.eategg.config.EnabledWorlds.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import click.seichi.eategg.EatEgg;
import click.seichi.eategg.IsUuidIgnored;
import java.util.List;
import org.bukkit.command.PluginCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainCommandTest {
  private ServerMock server;
  private EatEgg plugin;
  private PluginCommand command;
  private MainCommand mainCommand;

  @BeforeEach
  void setUp() {
    server = MockBukkit.mock();
    plugin = MockBukkit.load(EatEgg.class);
    command = plugin.getCommand("eategg");
    mainCommand = new MainCommand(plugin);
  }

  @AfterEach
  void tearDown() {
    MockBukkit.unmock();
  }

  @Test
  void missingSubcommandShowsUsage() {
    PlayerMock player = server.addPlayer();

    assertTrue(mainCommand.onCommand(player, command, "eategg", new String[0]));

    assertEquals(command.getUsage(), player.nextMessage());
  }

  @Test
  void unknownSubcommandShowsUsage() {
    PlayerMock player = server.addPlayer();

    assertTrue(mainCommand.onCommand(player, command, "eategg", new String[] {"unknown"}));

    assertEquals(command.getUsage(), player.nextMessage());
  }

  @Test
  void toggleSubcommandDelegatesToToggle() {
    PlayerMock player = server.addPlayer();
    player.addAttachment(plugin, "eategg.toggle", true);

    assertTrue(mainCommand.onCommand(player, command, "eategg", new String[] {"toggle"}));

    assertTrue(IsUuidIgnored.get(player.getUniqueId()));
  }

  @Test
  void reloadSubcommandDelegatesToReload() {
    ConsoleCommandSenderMock console = (ConsoleCommandSenderMock) server.getConsoleSender();
    plugin.getConfig().set("enabled-worlds", List.of("world"));

    assertTrue(mainCommand.onCommand(console, command, "eategg", new String[] {"reload"}));

    assertTrue(contains("WORLD"));
  }

  @Test
  void tabCompletionFiltersSubcommands() {
    PlayerMock player = server.addPlayer();

    assertEquals(
        List.of("toggle", "reload"),
        mainCommand.onTabComplete(player, command, "eategg", new String[0]));
    assertEquals(
        List.of("toggle"),
        mainCommand.onTabComplete(player, command, "eategg", new String[] {"TO"}));
    List<String> completions =
        mainCommand.onTabComplete(player, command, "eategg", new String[] {"toggle", "extra"});
    assertEquals(List.of(), completions);
  }

  @Test
  void tabCompletionDoesNotDependOnPermission() {
    PlayerMock player = server.addPlayer();
    player.addAttachment(plugin, "eategg.toggle", false);
    player.addAttachment(plugin, "eategg.reload", false);

    assertEquals(
        List.of("toggle", "reload"),
        mainCommand.onTabComplete(player, command, "eategg", new String[0]));
  }
}
