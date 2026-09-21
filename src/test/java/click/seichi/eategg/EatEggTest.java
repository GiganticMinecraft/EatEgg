package click.seichi.eategg;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import be.seeseemelk.mockbukkit.MockBukkit;
import click.seichi.eategg.commands.MainCommand;
import org.bukkit.command.PluginCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EatEggTest {
  private EatEgg plugin;

  @BeforeEach
  void setUp() {
    MockBukkit.mock();
    plugin = MockBukkit.load(EatEgg.class);
  }

  @AfterEach
  void tearDown() {
    MockBukkit.unmock();
  }

  @Test
  void pluginRegistersTheCommandExecutor() {
    PluginCommand command = plugin.getCommand("eategg");
    assertNotNull(command);
    assertInstanceOf(MainCommand.class, command.getExecutor());
  }
}
