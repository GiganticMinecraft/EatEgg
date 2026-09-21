package click.seichi.eategg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Test;

class PluginDescriptionTest {
  @Test
  void pluginYmlIsValid() throws Exception {
    try (InputStream input = getClass().getResourceAsStream("/plugin.yml")) {
      assertNotNull(input);

      PluginDescriptionFile description = new PluginDescriptionFile(input);

      assertEquals("EatEgg", description.getName());
      assertFalse(description.getVersion().isBlank());
      assertEquals(EatEgg.class.getName(), description.getMain());
      assertEquals("1.18", description.getAPIVersion());
      assertTrue(description.getDepend().contains("WorldGuard"));

      Class<?> mainClass = Class.forName(description.getMain(), false, getClass().getClassLoader());
      assertTrue(JavaPlugin.class.isAssignableFrom(mainClass));

      assertEquals(
          Set.of("eategg.*", "eategg.toggle", "eategg.reload"),
          description.getPermissions().stream()
              .map(Permission::getName)
              .collect(Collectors.toSet()));
      assertEquals(PermissionDefault.OP, permission(description, "eategg.*").getDefault());
      assertEquals(PermissionDefault.OP, permission(description, "eategg.toggle").getDefault());
      assertEquals(PermissionDefault.OP, permission(description, "eategg.reload").getDefault());
    }
  }

  private static Permission permission(PluginDescriptionFile description, String name) {
    return description.getPermissions().stream()
        .filter(permission -> permission.getName().equals(name))
        .findFirst()
        .orElseThrow();
  }
}
