package click.seichi.eategg;

import click.seichi.eategg.commands.MainCommand;
import click.seichi.eategg.config.EnabledWorlds;
import click.seichi.eategg.listeners.CancelEggHatched;
import click.seichi.eategg.listeners.ResetFlags;
import java.io.File;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

public final class EatEgg extends JavaPlugin {
  public EatEgg() {
    super();
  }

  private EatEgg(
      JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
    super(loader, description, dataFolder, file);
  }

  @Override
  public void onEnable() {
    saveDefaultConfig();
    EnabledWorlds.load(this);

    new MainCommand(this).register(this);

    for (Listener listener : Set.of(new CancelEggHatched(), new ResetFlags())) {
      Bukkit.getPluginManager().registerEvents(listener, this);
    }

    getLogger().info("EatEgg is enabled.");
  }
}
