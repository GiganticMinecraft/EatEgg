package click.seichi.eategg.listeners;

import click.seichi.eategg.IsUuidIgnored;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public final class ResetFlags implements Listener {
  @EventHandler
  public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
    IsUuidIgnored.reset(event.getPlayer().getUniqueId());
  }
}
