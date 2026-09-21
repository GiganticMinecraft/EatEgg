package click.seichi.eategg.config;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;

public final class EnabledWorlds {
  private static final String CONFIG_PATH = "enabled-worlds";
  private static final Set<String> WORLDS = new HashSet<>();

  private EnabledWorlds() {}

  public static void load(JavaPlugin instance) {
    WORLDS.clear();
    for (String world : instance.getConfig().getStringList(CONFIG_PATH)) {
      WORLDS.add(world.toLowerCase(Locale.ROOT));
    }
  }

  public static Set<String> get() {
    return Set.copyOf(WORLDS);
  }

  public static boolean contains(String worldName) {
    return WORLDS.contains(worldName.toLowerCase(Locale.ROOT));
  }
}
