package click.seichi.eategg.config

import org.bukkit.plugin.java.JavaPlugin

import java.util.Collections
import scala.collection.mutable
import scala.jdk.CollectionConverters._

object EnabledWorlds {
  private final val ConfigPath = "enabled-worlds"
  private val worlds: mutable.Set[String] = mutable.Set.empty

  def load()(implicit instance: JavaPlugin): Unit = {
    worlds.clear()

    instance.getConfig.getStringList(ConfigPath).asScala.map(_.toLowerCase).foreach(worlds.add)
  }

  def get(): Set[String] = worlds.toSet

  def contains(worldName: String): Boolean = get().contains(worldName.toLowerCase)
}
