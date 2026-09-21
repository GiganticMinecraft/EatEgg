package click.seichi.eategg;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class IsUuidIgnoredTest {
  @Test
  void isFalseByDefault() {
    assertFalse(IsUuidIgnored.get(UUID.randomUUID()));
  }

  @Test
  void toggleSwitchesTheValue() {
    UUID uuid = UUID.randomUUID();

    IsUuidIgnored.toggle(uuid);
    assertTrue(IsUuidIgnored.get(uuid));

    IsUuidIgnored.toggle(uuid);
    assertFalse(IsUuidIgnored.get(uuid));
  }

  @Test
  void resetDisablesTheBypass() {
    UUID uuid = UUID.randomUUID();
    IsUuidIgnored.toggle(uuid);

    IsUuidIgnored.reset(uuid);

    assertFalse(IsUuidIgnored.get(uuid));
  }
}
