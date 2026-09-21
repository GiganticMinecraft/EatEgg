package click.seichi.eategg.commands;

import java.util.Set;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainCommand extends BranchExecutor {
  public MainCommand(JavaPlugin plugin) {
    super("eategg", Set.of(new Reload(plugin), new Toggle()));
  }
}
