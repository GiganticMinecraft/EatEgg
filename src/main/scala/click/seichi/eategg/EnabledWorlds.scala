package click.seichi.eategg

import org.bukkit.plugin.java.JavaPlugin
import scala.jdk.CollectionConverters._
import scala.collection.mutable
import java.util.Collections

object EnabledWorlds {
  private final val ConfigPath = "enabled-worlds"
  private val worlds: mutable.Set[String] = mutable.Set.empty

  def load()(implicit instance: JavaPlugin): Unit = {
    worlds.clear()

    Option(instance.getConfig.getStringList(ConfigPath))
      .getOrElse(Collections.emptyList())
      .asScala
      .map(_.toLowerCase)
      .foreach(worlds.add)
  }

  def get(): Set[String] = worlds.toSet
}
