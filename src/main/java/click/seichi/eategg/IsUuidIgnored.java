package click.seichi.eategg;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public final class IsUuidIgnored {
  private static final @NotNull Map<UUID, Boolean> FLAGS = new HashMap<>();

  private IsUuidIgnored() {}

  public static boolean get(@NotNull UUID playerUuid) {
    return FLAGS.getOrDefault(playerUuid, false);
  }

  public static void toggle(@NotNull UUID playerUuid) {
    FLAGS.put(playerUuid, !get(playerUuid));
  }

  public static void reset(@NotNull UUID playerUuid) {
    FLAGS.put(playerUuid, false);
  }
}
