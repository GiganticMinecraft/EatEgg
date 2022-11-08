package click.seichi.eategg

import java.util.UUID
import scala.collection.mutable

object IsUuidIgnored {
  private val flags: mutable.Map[UUID, Boolean] = mutable.Map.empty

  def get(playerUuid: UUID): Boolean = flags.getOrElse(playerUuid, false)

  def toggle(playerUuid: UUID): Unit = flags.put(playerUuid, !get(playerUuid))

  def reset(playerUuid: UUID): Unit = flags.put(playerUuid, false)
}
