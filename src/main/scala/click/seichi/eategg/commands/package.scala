package click.seichi.eategg

import org.bukkit.command.{CommandSender, TabExecutor}
import org.bukkit.plugin.java.JavaPlugin

import java.util
import scala.jdk.CollectionConverters._

package object commands {
  case class CommandContext(
    sender: CommandSender,
    command: org.bukkit.command.Command,
    args: List[String]
  )

  abstract class PrintExecutor(override val commandName: String) extends Executor {
    override def execute(context: CommandContext): Unit =
      context.sender.sendMessage(context.command.getUsage)
  }

  abstract class BranchExecutor(
    override val commandName: String,
    branches: Set[Executor],
    whenNoBranch: Option[Executor] = None
  ) extends Executor {
    override def execute(context: CommandContext): Unit = {
      branches
        .find { executor => context.args.headOption.contains(executor.commandName.toLowerCase) }
        .getOrElse(whenNoBranch.getOrElse(new PrintExecutor(commandName) {}))
        .execute(context.copy(args = context.args.drop(1)))
    }

    override def completeTab(_context: CommandContext): List[String] =
      branches.map(_.commandName.toLowerCase).toList
  }

  abstract class Executor extends TabExecutor {
    val commandName: String

    def execute(context: CommandContext): Unit

    def completeTab(context: CommandContext): List[String] = context.args

    final def register(instance: JavaPlugin): Unit = {
      instance.getCommand(commandName).setExecutor(this)
    }

    override final def onCommand(
      commandSender: CommandSender,
      command: org.bukkit.command.Command,
      alias: String,
      args: Array[String]
    ): Boolean = {
      execute(CommandContext(commandSender, command, args.toList))

      true
    }

    override final def onTabComplete(
      commandSender: CommandSender,
      command: org.bukkit.command.Command,
      alias: String,
      args: Array[String]
    ): util.List[String] = completeTab(
      CommandContext(commandSender, command, args.toList)
    ).asJava
  }
}
