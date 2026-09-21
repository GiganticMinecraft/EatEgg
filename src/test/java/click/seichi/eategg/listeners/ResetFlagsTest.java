package click.seichi.eategg.listeners;

import static org.junit.jupiter.api.Assertions.assertFalse;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import click.seichi.eategg.IsUuidIgnored;
import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResetFlagsTest {
  private ServerMock server;

  @BeforeEach
  void setUp() {
    server = MockBukkit.mock();
  }

  @AfterEach
  void tearDown() {
    MockBukkit.unmock();
  }

  @Test
  void playerQuitResetsTheBypass() {
    PlayerMock player = server.addPlayer();
    IsUuidIgnored.toggle(player.getUniqueId());

    new ResetFlags()
        .onPlayerQuit(
            new PlayerQuitEvent(
                player, Component.empty(), PlayerQuitEvent.QuitReason.DISCONNECTED
            )
        );

    assertFalse(IsUuidIgnored.get(player.getUniqueId()));
  }
}
