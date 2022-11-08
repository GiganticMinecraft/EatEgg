package click.seichi.eategg

import org.bukkit.plugin.java.JavaPlugin
import scala.jdk.CollectionConverters._
import scala.collection.mutable

object EnabledWorlds {
  private final val ConfigPath = "enabled-worlds"
  private val worlds: mutable.Set[String] = mutable.Set.empty

  def load()(implicit instance: JavaPlugin): Unit = {
    worlds.clear()

    instance.getConfig.getStringList(ConfigPath).asScala.foreach(worlds.add)
  }

  def get(): Set[String] = worlds.toSet
}
