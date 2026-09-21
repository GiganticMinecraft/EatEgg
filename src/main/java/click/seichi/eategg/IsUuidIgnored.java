package click.seichi.eategg;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class IsUuidIgnored {
  private static final Map<UUID, Boolean> FLAGS = new HashMap<>();

  private IsUuidIgnored() {}

  public static boolean get(UUID playerUuid) {
    return FLAGS.getOrDefault(playerUuid, false);
  }

  public static void toggle(UUID playerUuid) {
    FLAGS.put(playerUuid, !get(playerUuid));
  }

  public static void reset(UUID playerUuid) {
    FLAGS.put(playerUuid, false);
  }
}
